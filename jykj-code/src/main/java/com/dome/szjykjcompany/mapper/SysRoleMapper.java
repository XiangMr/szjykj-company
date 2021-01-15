package com.dome.szjykjcompany.mapper;

import com.dome.szjykjcompany.pojo.SysRole;

import java.util.List;
import java.util.Set;

public interface SysRoleMapper {
    public Set<String> selectUserRoleSet(Integer uid);

    public SysRole QueryByuidRole(Integer uid);

    /**
     * 查询所有角色
     */
    public List<SysRole> QueryRoleList();
}
