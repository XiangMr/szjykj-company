package com.dome.szjykjcompany.service;



import com.dome.szjykjcompany.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class IsRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    public Set<String> selectUserRoleSet(Integer uid){
        return sysRoleMapper.selectUserRoleSet(uid);
    }

}
