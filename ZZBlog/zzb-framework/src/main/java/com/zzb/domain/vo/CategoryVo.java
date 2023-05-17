package com.zzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zahngzongbin
 * @date: 2023/3/9 - 17:36
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {
    private int id;
    private String name;
    //描述
    private String description;
}
