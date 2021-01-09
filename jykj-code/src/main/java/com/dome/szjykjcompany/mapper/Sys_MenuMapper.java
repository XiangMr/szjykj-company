package com.dome.szjykjcompany.mapper;

import com.dome.szjykjcompany.pojo.SysMenu;

import java.util.List;

/**
 * 数据库 sys_menu表 接口层
 * time：2020年12月24日10:25:06
 * 编写人：xsg
 */
public interface Sys_MenuMapper {
    /**
     * 查询全部
     * @return menu
     */
    public List<SysMenu> QuerySysMenuListAll();
}
