package com.dome.szjykjcompany.web;
import com.dome.szjykjcompany.pojo.SysMenu;
import com.dome.szjykjcompany.pojo.Vo.LoginUserVo;
import com.dome.szjykjcompany.service.IsMenuService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@CrossOrigin
public class IndexController {


    @Autowired
    private IsMenuService isMenuService;

    @GetMapping({"/index","/index.html"})
    public String showIndex(Model session){
        List<SysMenu> sysMenus = isMenuService.QuerySysMenuList();
        session.addAttribute("menu",sysMenus);
        LoginUserVo userVo=(LoginUserVo)SecurityUtils.getSubject().getPrincipal();
        session.addAttribute("usera",userVo.getUser());
        return "index";
    }
}
