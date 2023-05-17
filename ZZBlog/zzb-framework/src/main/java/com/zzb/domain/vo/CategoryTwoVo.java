package com.zzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhangzongbin
 * @date: 2023/4/10 - 0:23
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTwoVo {
    private Long id;
    private String name;
    private String status;
    private String description;
}
