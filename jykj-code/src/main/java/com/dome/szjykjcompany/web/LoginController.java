package com.dome.szjykjcompany.web;


import com.dome.szjykjcompany.jwt.JwtProperties;
import com.dome.szjykjcompany.jwt.JwtUtils;
import com.dome.szjykjcompany.jwt.pojo.UserInfo;
import com.dome.szjykjcompany.pojo.Vo.LoginUserVo;
import com.dome.szjykjcompany.service.IsUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Controller
public class LoginController {

    @Autowired
    private IsUserService isUserService;

    @Autowired
    private JwtProperties prop;

    @Autowired
    private StringRedisTemplate redis;

    @Value("${ly.jwt.cookieName}")
    private String cookieName;

    protected static final Logger logs = LoggerFactory.getLogger(LoginController.class);

    private final String TokenKEY = "user:login:token:";


    /**
     * 登录页面
     * @param ChildToken 客户端token 有token可以免登录
     * @return
     */
    @GetMapping({"/","/login", "/login.html"})
    public String loginHtml(HttpServletResponse  res, @CookieValue(value = "JY_TOKEN", required = false) String ChildToken) {
        if (!StringUtils.isEmpty(ChildToken)) {
            //logs.info("[访问请求]_—_"+req.getRequestURI());
            UserInfo userInfo = JwtUtils.getUserInfo(prop.getPublicKey(), ChildToken);
            Map<Object, Object> tokenUserMap = redis.opsForHash().entries(TokenKEY + userInfo.getName());
            if (redis.opsForValue().get(TokenKEY + "lonum") == null) {
                redis.opsForValue().set(TokenKEY + "lonum", "0", 24, TimeUnit.HOURS);
            }
            if (tokenUserMap.size() > 0) {
                if (Integer.parseInt(redis.opsForValue().get(TokenKEY + "lonum")) < 5) {
                    redis.boundValueOps(TokenKEY + "lonum").increment(+1);
                }
               /* if (Integer.parseInt(redis.opsForValue().get(TokenKEY + "lonum")) >= 5) {
                    redis.delete(TokenKEY + userInfo.getName());
                    System.out.println("------------------------>使用token太过频繁，清除缓存 清除cookie<------------------------");
                    redis.delete(TokenKEY + "lonum");
                    Cookie cookie = new Cookie(cookieName, "");
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    res.addCookie(cookie);
                    return "login";
                }*/
                String userpwd = (String) tokenUserMap.get("userpwd");
                isUserService.token((String) tokenUserMap.get("loginname"), userpwd.substring(userpwd.indexOf("key") + 3));
                return "forward:/index";
            }
        }
        //System.out.println("req.getRequestURL() = " + req.getRequestURL());
        System.out.println("进入login页面！");
        return "login";
    }

    /**
     * 用户登录请求
     */
    @PostMapping("/Userlogin")
    @ResponseBody
    public String userLogin(@RequestBody Map<String, String> map, HttpServletResponse response) {
        logs.info(map.get("loginName") + "登录请求进入controller层");
        String token = isUserService.token(map.get("loginName"), map.get("nuse"));
        if (!token.equals("error")) {
            if (token.equals("Ban")) {
                logs.info(map.get("loginName") + "[登录请求]-》账户被冻结！");
                return "Ban";
            } else if (token.equals("frozen")) {
                logs.info(map.get("frzen") + "[登录请求]-》账户临时冻结！");
                return "frozen";
            } else {
                Cookie cookie = new Cookie(cookieName, token);
                cookie.setMaxAge(25 * 60);
                response.addCookie(cookie);
                System.out.println("cookieName = " + cookieName);
                System.out.println("token = " + token);
                logs.info(map.get("loginName") + "[登录请求]-》登录成功！");
                return "success";
            }
        }
        logs.info(map.get("loginName") + "[登录请求]-》账户或者密码错误！");
        return "error";
    }

    @PostMapping("/tologin")
    @ResponseBody
    //@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR,reason="server error")
    public ResponseEntity<Void> toLogin(@RequestBody Map<String, String> map, HttpServletResponse response) {
        System.out.println("====================进入controller层======================");
        //得到当前登录主体
        Subject subject = SecurityUtils.getSubject();
        //创建口令
        UsernamePasswordToken token = new UsernamePasswordToken(map.get("loginName"), map.get("nuse"));
        try {
            //登录
            subject.login(token);
            //创建登录对象
            LoginUserVo loginUserVo = (LoginUserVo) subject.getPrincipal();
            //保存会话
            //request.getSession().setAttribute("loginUser",loginUserVo.getUser());
            //跳转到后台主页
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AuthenticationException e) {

        }
        //登录失败
        //request.setAttribute("error", "账号或者密码错误！");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
