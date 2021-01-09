package com.dome.szjykjcompany.shiro.realm;

import com.alibaba.druid.util.Base64;
import com.dome.szjykjcompany.mapper.SysUserMapper;
import com.dome.szjykjcompany.pojo.SysUser;
import com.dome.szjykjcompany.pojo.Vo.LoginUserVo;
import com.dome.szjykjcompany.service.IsPermissionService;
import com.dome.szjykjcompany.service.IsRoleService;
import com.dome.szjykjcompany.service.IsUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private IsRoleService roleService;

    @Autowired
    private IsPermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LoginUserVo loginUserVo =(LoginUserVo) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (loginUserVo.getRoles()!=null && loginUserVo.getRoles().size()>0){
            info.setRoles(loginUserVo.getRoles());
        }
        if (loginUserVo.getPermissions()!=null &&loginUserVo.getPermissions().size()>0){
            info.setStringPermissions(loginUserVo.getPermissions());
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取当前登录的主体对象的用户名 userName
        String username = authenticationToken.getPrincipal().toString();
        try {
            //调用service层查询userName的方法
            SysUser user = sysUserMapper.SelectUserLogin(username);
            //判断是否存在userName的用户
            if (user != null) {
                // 查询该用户的下的角色
                Set<String> roles = roleService.selectUserRoleSet(user.getUid());
                //查询该用户下角色的权限
                Set<String> pers = permissionService.selectUserPermissionSet(user.getUid());
                //创建登录用户对象，传入用户信息，角色表列，权限列表
                LoginUserVo loginUserVo = new LoginUserVo(user, roles, pers);
                //将用户名作为加密的盐值
                ByteSource bytes = ByteSource.Util.bytes(Base64.base64ToByteArray(user.getUsersalt()));
                //System.out.println("user.getUsersalt() = " + user.getUsersalt());
                //创建身份验证对象
                //参数1：用户名称、参数2：用户密码、参数3：加密的盐值、参数4：域名（可随意填写）
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(loginUserVo,
                                                                             user.getUserpwd(), ByteSource.Util.bytes(user.getUsersalt()), "xxx");
                System.out.println("=============="+username+"尝试登录！==============");
                //返回对象
                return info;
            }
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("==============用户名称或者密码不正确被拦截！==============");
        }
        return null;
    }
}
