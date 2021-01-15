package com.dome.szjykjcompany.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.Date;


/**
 * sys_User实体类
 */
@Data
@NoArgsConstructor
public class SysUser {

    private Integer uid;

    private String username;

    private String loginname;

    private String userpwd;

    private String usersalt;

    private Integer role;

    private Date lastlogintime;

    private String userImages;

    private int type;

    private SysUserExtend userExtend;

    public SysUser(String username, String loginname, String userpwd, String usersalt, Integer role, Date lastlogintime, String userImages, int type) {
        this.username = username;
        this.loginname = loginname;
        this.userpwd = userpwd;
        this.usersalt = usersalt;
        this.role = role;
        this.lastlogintime = lastlogintime;
        this.userImages = userImages;
        this.type = type;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", loginname='" + loginname + '\'' +
                ", userpwd='" + userpwd + '\'' +
                ", usersalt='" + usersalt + '\'' +
                ", role=" + role +
                ", lastlogintime=" + lastlogintime +
                ", type=" + type +
                '}';
    }

    public SysUser(Integer uid, String userImages) {
        this.uid = uid;
        this.userImages = userImages;
    }
    public SysUser(Integer uid, String userImages,SysUserExtend sysUserExtend) {
        this.uid = uid;
        this.userExtend=sysUserExtend;
        this.userImages = userImages;
    }

    public SysUser(Integer uid,String username, SysUserExtend userExtend,String loginname) {
        this.username = username;
        this.loginname=loginname;
        this.uid=uid;
        this.userExtend = userExtend;
    }

    public SysUser(Integer uid, String username, String loginname, String userpwd, String usersalt, Integer role, Date lastlogintime, String userImages, int type, SysUserExtend userExtend) {
        this.uid = uid;
        this.username = username;
        this.loginname = loginname;
        this.userpwd = userpwd;
        this.usersalt = usersalt;
        this.role = role;
        this.lastlogintime = lastlogintime;
        this.userImages = userImages;
        this.type = type;
        this.userExtend = userExtend;
    }
}