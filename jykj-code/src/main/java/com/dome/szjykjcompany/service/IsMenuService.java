package com.dome.szjykjcompany.service;


import com.dome.szjykjcompany.mapper.SysMenuMapper;
import com.dome.szjykjcompany.mapper.Sys_MenuMapper;
import com.dome.szjykjcompany.pojo.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class IsMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private Sys_MenuMapper sys_menuMapper;

    public List<SysMenu> QuerySysMenuList(){
        return sysMenuMapper.QuerySysMenuList();
    }

    public List<SysMenu> QuerySysMenuListAll(){
        return sys_menuMapper.QuerySysMenuListAll();
    }

    /**
     * 新增menu
     * @return 受影响行数
     */
    public int addMenu(SysMenu menu){
        return sysMenuMapper.addMenu(menu);
    }

    /**
     * 根据menuid查询对应的树
     */
    public SysMenu querySysMenuId(Integer mid){
        return sysMenuMapper.querySysMenuId(mid);
    }

    /**
     * 修改menu
     * @return 受影响行数
     */
    public int updateSysMenu(SysMenu sysMenu){
        return sysMenuMapper.updateSysMenu(sysMenu);
    }

    /**
     * 递归删除部门
     * 功能：删除部门以及下面的子部门
     */
    public boolean deleByOneId(Integer oneId) {
        SysMenu sysMenu = this.querySysMenuId(oneId);
        if (StringUtils.isEmpty(sysMenu)){
            System.out.println("参数不存在 二次提交");
            return true;
        }

        ArrayList<Integer> ids = new ArrayList<>();
        //先把要删除的一级分类id放入到集合中
        ids.add(oneId);
        //递归的将一级分类下的id也加入到集合中
        this.getIds(ids, oneId);
        //批量删除集合中的id
        System.out.println("---------------------->批量删除");
        ids.forEach(System.out::println);
        int i = sysMenuMapper.deleteMenuById(ids);
        return i > 0;
    }

    //递归方法
    private void getIds(ArrayList<Integer> ids, Integer oneId) {
        //查询二级分类的对象
        List<SysMenu> sysMenus = sysMenuMapper.deleteMenuList(oneId);
        //遍历二级分类的对象，把二级分类的id加入到要删除的集合中
        for (SysMenu sysDepartment : sysMenus) {
            int id = sysDepartment.getMenuId();
            ids.add(id);
            //把二级分类的每一个ID，查询它下面的子节点
            this.getIds(ids, id);
        }
    }
}
