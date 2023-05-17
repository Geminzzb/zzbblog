package com.zzb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: zhangzongbin
 * @date: 2023/3/23 - 1:57
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminArticleDto {
    private Long categoryId;

    private String content;

    private Long id;

    private String isComment;

    private String isTop;

    private String status;

    private String summary;

    private List<Long> tags;

    private String thumbnail;

    private String title;

    private Long viewCount;
}
