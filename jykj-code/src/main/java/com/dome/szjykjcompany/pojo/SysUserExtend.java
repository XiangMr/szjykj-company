package com.dome.szjykjcompany.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class SysUserExtend {
    private Integer eid;

    private Integer uid;

    private String userAddress;

    private String userSex;

    private String userBirthday;

    private Date userChangetime;

    private Integer userAge;

    private String userPhone;

    private String userEmils;

    @Override
    public String toString() {
        return "SysUserExtend{" +
                "eid=" + eid +
                ", uid=" + uid +
                ", userAddress='" + userAddress + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userBirthday='" + userBirthday + '\'' +
                ", userChangetime=" + userChangetime +
                ", userAge=" + userAge +
                ", userPhone='" + userPhone + '\'' +
                ", userEmils='" + userEmils + '\'' +
                '}';
    }

    public SysUserExtend(Integer eid, Integer uid, String userAddress, String userSex, String userBirthday, Date userChangetime, Integer userAge, String userPhone, String userEmils) {
        this.eid = eid;
        this.uid = uid;
        this.userAddress = userAddress;
        this.userSex = userSex;
        this.userBirthday = userBirthday;
        this.userChangetime = userChangetime;
        this.userAge = userAge;
        this.userPhone = userPhone;
        this.userEmils = userEmils;
    }

    public SysUserExtend(String userAddress) {
        this.userAddress = userAddress;
    }
}