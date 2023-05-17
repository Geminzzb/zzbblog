package com.zzb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhangzongbin
 * @date: 2023/3/22 - 4:26
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    //标题
    private String title;

    //文章摘要
    private String summary;
}
