package com.zzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: zahngzongbin
 * @date: 2023/3/12 - 22:41
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {
    private Long id;


    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;



}
