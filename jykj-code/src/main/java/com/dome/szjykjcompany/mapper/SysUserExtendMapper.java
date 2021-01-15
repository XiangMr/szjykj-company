package com.dome.szjykjcompany.mapper;

import com.dome.szjykjcompany.pojo.SysUserExtend;

/**
 * @ClassName SysUserExtendMapper
 * @Deacription TODO
 * @Author 向绍高
 * @Date 2020/12/29 11:59
 * @Version 1.0
 **/
public interface SysUserExtendMapper {

    /**
     * 通过uid 查询用户更多信息
     */
    public SysUserExtend queryExtendByuid(Integer uid);

    public int addUserExtend(SysUserExtend userExtend);
}
