package com.dome.szjykjcompany.service;

import com.dome.szjykjcompany.mapper.SysUserExtendMapper;
import com.dome.szjykjcompany.pojo.Vo.LoginUserVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName IsSysUserExtendService
 * @Deacription TODO
 * @Author MI
 * @Date 2020/12/29 14:31
 * @Version 1.0
 **/
@Service
public class IsUserExtendService {
    @Autowired
    private SysUserExtendMapper sysUserExtendMapper;

    /**
     * 通过uid来获取更多用户信息 Extend表
     */
    public LoginUserVo getSysUserExtend(){
        LoginUserVo user =(LoginUserVo) SecurityUtils.getSubject().getPrincipal();
        user.getUser().setUserExtend(sysUserExtendMapper.queryExtendByuid(user.getUser().getUid()));
        return user;
    }
}
