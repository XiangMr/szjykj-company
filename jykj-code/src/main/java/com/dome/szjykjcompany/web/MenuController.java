package com.dome.szjykjcompany.web;

import com.alibaba.fastjson.JSON;
import com.dome.szjykjcompany.pojo.SysMenu;
import com.dome.szjykjcompany.service.IsMenuService;
import com.dome.szjykjcompany.service.IsPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IsMenuService isMenuService;

    @Autowired
    private IsPermissionService isPermissionService;

    /**
     * 进入menutree界面
     *
     * @return html
     */
    @GetMapping("/menu.html")
    public String showMenuHtml() {
        return "ChildHtml/system/treeTitle/treeTitle";
    }

    /**
     * 进入menutree 数据
     *
     * @return json
     */
    @GetMapping("/getMenusystem")
    @ResponseBody
    public ResponseEntity getMenuSystem(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "http://www.domain1.com");
        System.out.println("请求进入！");
        List<SysMenu> allRecursion = isMenuService.QuerySysMenuListAll();//查找所有菜单
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "true");
        map.put("code", "0");
        map.put("data", allRecursion);

        return ResponseEntity.ok(JSON.toJSONString(map));
    }

    /**
     * 携带menuid 进入新增页面
     *
     * @param id 部门id
     * @return
     */
    @GetMapping("/create.html/{menuid}")
    public String ruturnCreateHtml(@PathVariable("menuid") Integer id, HttpServletRequest req) {
        req.setAttribute("id", id);
        req.setAttribute("perss", isPermissionService.QueryPermissionList());
        return "ChildHtml/system/treeTitle/create";
    }

    @GetMapping("/rootmenu.html")
    public String returnrootmuenHmtl(HttpServletRequest req){
        req.setAttribute("perss", isPermissionService.QueryPermissionList());
        return "ChildHtml/system/treeTitle/rootmenu";
    }

    /**
     * 携带menuid 新增menu树目录
     *
     * @return 1：为新增成功 2：为失败
     */
    @PostMapping("/AddMenu")
    @ResponseBody
    public String AddMenu(@RequestBody SysMenu sysMenu) {
        System.out.println("sysMenu = " + sysMenu);
        if (isMenuService.addMenu(sysMenu) > 0) {
            return "1";
        } else {
            return "2";
        }
    }

    /**
     * 携带menuid 进入新增页面
     *
     * @param id 部门id
     */
    @GetMapping("/update.html/{menuid}")
    public String ruturnUpdateHtml(@PathVariable("menuid") Integer id, HttpServletRequest req) {
        req.setAttribute("id", id);
        req.setAttribute("sysmenu", isMenuService.querySysMenuId(id));
        req.setAttribute("perss", isPermissionService.QueryPermissionList());
        return "ChildHtml/system/treeTitle/update";
    }

    /**
     * 修改menuid信息
     * 携带menuid 新增menu树目录
     *
     * @return 1：为新增成功 2：为失败
     */
    @PostMapping("/UpdateMenu")
    @ResponseBody
    public String ruturnUpdateHtml(@RequestBody SysMenu sysMenu) {
        if (isMenuService.updateSysMenu(sysMenu) > 0) {
            return "1";
        } else {
            return "2";
        }
    }

    /**
     * 删除系统树 连带子集一起删除。
     *
     * @param ids menuid
     * @return 1 true 2 false
     */
    @GetMapping("/delMenu")
    @ResponseBody
    public String delMenuBymenuid(@RequestParam("menuid") List<Integer> ids) {
        System.out.println("ids.get(0) = " + ids.get(0));
        if (isMenuService.deleByOneId(ids.get(0))) {
            return "1";
        } else {
            return "2";
        }
    }
}
