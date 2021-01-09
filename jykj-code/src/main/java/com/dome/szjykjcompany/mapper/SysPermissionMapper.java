package com.dome.szjykjcompany.mapper;

import com.dome.szjykjcompany.pojo.SysPermission;

import java.util.List;
import java.util.Set;


public interface SysPermissionMapper {
    public Set<String> selectUserPermissionSet(Integer uid);

    public List<SysPermission> QueryPermissionList();
}
