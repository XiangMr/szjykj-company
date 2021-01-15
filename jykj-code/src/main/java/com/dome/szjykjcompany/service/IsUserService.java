package com.dome.szjykjcompany.service;


import com.alibaba.druid.util.Base64;
import com.dome.szjykjcompany.jwt.JwtProperties;
import com.dome.szjykjcompany.jwt.JwtUtils;
import com.dome.szjykjcompany.jwt.pojo.UserInfo;
import com.dome.szjykjcompany.mapper.SysUserExtendMapper;
import com.dome.szjykjcompany.mapper.SysUserMapper;
import com.dome.szjykjcompany.pojo.SysUser;
import com.dome.szjykjcompany.pojo.SysUserExtend;
import com.dome.szjykjcompany.pojo.Vo.LoginUserVo;
import com.dome.szjykjcompany.pojo.Vo.ResultVO;
import com.dome.szjykjcompany.utils.CodecUtils;
import com.dome.szjykjcompany.utils.applicationUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@EnableConfigurationProperties({JwtProperties.class})
public class IsUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserExtendMapper sysUserExtendMapper;

    @Autowired
    private JwtProperties prop;

    @Autowired
    private applicationUtils application;

    static String KEY_PREFIX = "user:login:number:";

    @Autowired
    private StringRedisTemplate redis;

    /**
     * 用户登录
     */
    public ResultVO UserLogin(String loginName, String pwd) {
        //判断传参是否为null
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(pwd)) {
            return new ResultVO("error", new Date(), null);
        }
        SysUser sysUser = sysUserMapper.SelectUserLogin(loginName);
        //判断用户名输入是否正确
        if (StringUtils.isEmpty(sysUser)) {
            return new ResultVO("error", new Date(), null);
        }
        System.out.println("====================进入shiro登录校验层======================");
        //得到当前登录主体
        Subject subject = SecurityUtils.getSubject();
        //创建口令
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, pwd);
        try {
            //登录
            subject.login(token);
            //创建登录对象
            LoginUserVo loginUserVo = (LoginUserVo) subject.getPrincipal();
        } catch (AuthenticationException e) {
            System.out.println("------------------->" + new Date().toString() + "_-_" + loginName + "[密码错误]-->记录缓存<-------------------");
            //进入缓存
            if (redis.opsForValue().get(KEY_PREFIX + loginName) == null) {
                redis.opsForValue().set(KEY_PREFIX + loginName, "1", 1 * 10, TimeUnit.MINUTES);
            }
            if (redis.opsForValue().get(KEY_PREFIX + loginName).equals("5")) {
                //5次已到，封禁账号十分钟
                sysUserMapper.UpdateUserTypeError(loginName);
                return new ResultVO("frozen", new Date(), null);
            }
            redis.boundValueOps(KEY_PREFIX + loginName).increment(1);
            System.out.println("redis数据+1   ---》" + redis.opsForValue().get(KEY_PREFIX + loginName));
            return new ResultVO("error", new Date(), null);
        }
        if (sysUser.getType() == 0) {
            System.out.println("------------------->" + new Date().toString() + "_-_" + loginName + "[账户状态正常，允许登录]<-------------------");
            //登录成功 修改最后登录时间
            sysUserMapper.UpdateUserLoginDateTime(new Date(), loginName);
            //登录成功  token写入redis
            if (application.getredisSwitch == true) {
                Map<String, String> userMap = new HashMap<>();
                userMap.put("loginname", sysUser.getLoginname());
                userMap.put("userpwd", CodecUtils.generateSalt() +"key"+pwd);
                redis.opsForHash().putAll("user:login:token:" + loginName, userMap);
                redis.expire("user:login:token:" + loginName, 72, TimeUnit.HOURS);
            }
            //登录成功 删除登录次数
            redis.delete(KEY_PREFIX + loginName);
            return new ResultVO("success", new Date(), sysUser);
        }
        if (sysUser.getType() == 1) {
            if (StringUtils.isEmpty(redis.opsForValue().get(KEY_PREFIX + loginName))) {
                sysUserMapper.UpdateUserTypeSeccess(loginName);
                redis.delete(KEY_PREFIX + loginName);
                System.out.println("------------------->" + new Date().toString() + "_-_" + loginName + "[十分钟过去恢复正常，允许登录]<-------------------");
                return new ResultVO("success", new Date(), sysUser);
            }
            System.out.println("------------------->" + new Date().toString() + "_-_" + loginName + "[账户被临时冻结，不允许登录]<-------------------");
            return new ResultVO("frozen", new Date(), null);
        }
        System.out.println("------------------->" + new Date().toString() + "_-_" + loginName + "[账户被冻结，不允许登录]<-------------------");
        return new ResultVO("Ban", new Date(), null);
    }

    /**
     * ---->用户登录成功  颁发token令牌
     * @param loginname
     * @param passWrod
     * @return
     */
    public String token(String loginname, String passWrod) {
        ResultVO<SysUser> login = UserLogin(loginname, passWrod);
        if (login.getStutes().equals("error")) {
            System.out.println("账号密码错误，不颁发token令牌");
            return "error";
        }
        if (login.getStutes().equals("Ban")) {
            System.out.println("账户被冻结，不颁发token令牌");
            return "Ban";
        }
        if (login.getStutes().equals("frozen")) {
            System.out.println("账户被临时冻结，不颁发token令牌");
            return "frozen";
        }

        System.out.println("账号密码正确，颁发token令牌");
        // 生成token
        return JwtUtils.generateToken(new
                UserInfo(login.getResult().getUid().longValue(), login.getResult().getLoginname()), prop.getPrivateKey(), prop.getExpire());
    }

    /**
     * 修改用户信息
     */
   @Transactional
    public int updateUserEx(SysUser user){
        return sysUserMapper.updateUserEx(user);
    }

    /**
     * 新增用户
     * @param user ->对象
     * @return ->自增的id
     */
    @Transactional
    public int addUserByExtend(SysUser user, SysUserExtend sysUserExtend){
        String salt = CodecUtils.generateSalt();
        String  bCryptPassword = new SimpleHash("md5",user.getUserpwd(),salt,65).toString();
        user.setType(0);
        user.setLastlogintime(new Date());
        user.setUserImages("http://szjykj.oss-cn-hangzhou.aliyuncs.com/jykj-image/317094未标题-1.jpg");
        user.setUsersalt(salt);
        user.setUserpwd(bCryptPassword);
        int result = sysUserMapper.addUserByExtend(user);
        if (result>0){
            sysUserExtend.setUid(user.getUid());
            sysUserExtend.setUserChangetime(new Date());
            int count = sysUserExtendMapper.addUserExtend(sysUserExtend);
            if (count>0){
                return 1;
            }
        }
        return 0;
    }
}
