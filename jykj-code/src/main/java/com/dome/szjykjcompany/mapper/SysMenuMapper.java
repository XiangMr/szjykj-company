package com.dome.szjykjcompany.mapper;


import com.dome.szjykjcompany.pojo.SysMenu;

import java.util.List;

/**
 * 数据库 sys_menu表 接口层
 * time：2020年12月23日14:16:30
 * 编写人：xsg
 */
public interface SysMenuMapper {
    /**
     * 级联查询
     * @return menu
     */
    public List<SysMenu> QuerySysMenuList();

    /**
     * 级联查询
     * @return menuChilder
     */
    public List<SysMenu> getMenuChildren();

    /**
     * 新增部门树
     * @return menuChilder
     */
    public int addMenu(SysMenu menu);


    /**
     * 新增部门树
     * @return menuChilder
     */
    public SysMenu querySysMenuId(Integer mid);

    /**
     * 修改部门树
     * @return menuChilder
     */
    public int updateSysMenu(SysMenu sysMenu);

    public int deleteMenuById(List<Integer> ids);

    public List<SysMenu> deleteMenuList(Integer parentId);
}
