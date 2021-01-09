package com.dome.szjykjcompany.pojo.Vo;


import com.dome.szjykjcompany.pojo.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * shiro-登录用户类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVo {
    private SysUser user;//用户信息
    private Set<String> roles;//角色列表
    private Set<String> permissions;//权限列表

}
