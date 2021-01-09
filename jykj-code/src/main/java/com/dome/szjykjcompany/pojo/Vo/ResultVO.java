package com.dome.szjykjcompany.pojo.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {
    private String stutes;
    private Date date;
    private T result;
}
