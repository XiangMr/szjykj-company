package com.dome.szjykjcompany.web;

import com.alibaba.fastjson.JSONObject;
import com.dome.szjykjcompany.aliyun.oss.ossTools;
import com.dome.szjykjcompany.pojo.SysUser;
import com.dome.szjykjcompany.pojo.SysUserExtend;
import com.dome.szjykjcompany.pojo.Vo.LoginUserVo;
import com.dome.szjykjcompany.service.IsUserExtendService;
import com.dome.szjykjcompany.service.IsUserService;
import com.dome.szjykjcompany.utils.MultipartFileToFile;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserController
 * @Deacription TODO
 * @Author MI
 * @Date 2020/12/29 10:48
 * @Version 1.0
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IsUserExtendService isUserExtendService;

    @Autowired
    private ossTools oss;

    @Autowired
    private IsUserService isUserService;

    /**
     * shiro 获得当前登录主体
     *
     * @param req //返回用户信息 ->sys_user -> sys_user_extend
     * @return
     */
    @GetMapping({"updateUserData.html", "updateUserData"})
    public String updateUserData(HttpServletRequest req) {
        req.setAttribute("users", isUserExtendService.getSysUserExtend());
        return "ChildHtml/user/updateUserData";
    }

    /**
     * 用户头像上传
     *
     * @param file 图片
     * @return
     */
    @PostMapping("/uploadFile")
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        String imageUrl = oss.uploadObject2OSS(oss.getOSSClient(), MultipartFileToFile.multipartFileToFile(file), oss.bucketName, oss.folder);
        System.out.println("imageUrl = " + imageUrl);
        if (!imageUrl.equals(null) || !imageUrl.equals("")) {
            LoginUserVo uservo = (LoginUserVo) SecurityUtils.getSubject().getPrincipal();
            isUserService.updateUserEx(new SysUser(uservo.getUser().getUid(), imageUrl + file.getOriginalFilename()));
        }
        Map<String, String> map = new HashMap<>();
        map.put("success", "1");
        map.put("message", "上传成功");
        return JSONObject.toJSONString(map);
    }

    /**
     * 用户信息修改
     *
     * @param user 用户表
     * @return
     */
    @PostMapping("/updateUser")
    @ResponseBody
    public String updateUser(@RequestBody Map<String, String> user) {
        SysUserExtend userExtend = (SysUserExtend) JSONObject.parseObject(JSONObject.toJSONBytes(user), SysUserExtend.class);
        LoginUserVo users=(LoginUserVo)SecurityUtils.getSubject().getPrincipal();
        int result = isUserService.updateUserEx(new SysUser(users.getUser().getUid(),user.get("username"),userExtend,null));
        if (result > 0) {
            return "1";
        } else {
            return "2";
        }
    }
}
