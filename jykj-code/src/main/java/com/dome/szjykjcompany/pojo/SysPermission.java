package com.dome.szjykjcompany.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysPermission {
    private Integer permissionid;//权限编号
    private String permissionname;//权限名称
    private String permissioncode;//权限编码
}
