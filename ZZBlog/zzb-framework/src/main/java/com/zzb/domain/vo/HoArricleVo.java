package com.zzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zahngzongbin
 * @date: 2023/3/8 - 20:34
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoArricleVo {

    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
