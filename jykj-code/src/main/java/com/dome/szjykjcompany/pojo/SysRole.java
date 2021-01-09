package com.dome.szjykjcompany.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole {
    private Integer userId;

    private String roleName;

    private Integer roleId;

    private Integer orderNum;

    @Override
    public String toString() {
        return "SysUserRole{" +
                "userId=" + userId +
                ", roleName='" + roleName + '\'' +
                ", roleId=" + roleId +
                ", orderNum=" + orderNum +
                '}';
    }
}
