package com.dome.szjykjcompany.service;


import com.dome.szjykjcompany.mapper.SysPermissionMapper;
import com.dome.szjykjcompany.pojo.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class IsPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    public Set<String> selectUserPermissionSet(Integer uid){
        return sysPermissionMapper.selectUserPermissionSet(uid);
    }

    /**
     * 查询所有权限
     * @return 权限集合
     */
    public List<SysPermission> QueryPermissionList(){
        return sysPermissionMapper.QueryPermissionList();
    }
}
