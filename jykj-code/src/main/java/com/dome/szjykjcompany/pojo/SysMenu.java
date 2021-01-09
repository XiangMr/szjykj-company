package com.dome.szjykjcompany.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.List;


/**
 * 数据库 sys_menu表 实体类
 * time：2020年12月23日14:16:30
 * 编写人：xsg
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu {

    private Integer menuId;

    private String menuName;

    private Integer parentId;

    private Integer orderNum;

    private String url;

    private String visible;

    private String perms;

    private String icon;

    @Transient
    private List<SysMenu> chilne;

    @Override
    public String toString() {
        return "SysMenu{" +
                "menuId=" + menuId +
                ", menuName='" + menuName + '\'' +
                ", parentId=" + parentId +
                ", orderNum=" + orderNum +
                ", url='" + url + '\'' +
                ", visible='" + visible + '\'' +
                ", perms='" + perms + '\'' +
                ", icon='" + icon + '\'' +
                ", chilne=" + chilne +
                '}';
    }
}