package com.zzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: zahngzongbin
 * @date: 2023/3/11 - 3:25
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类名
    private String categoryName;
    //缩略图
    private String thumbnail;


    //访问量
    private Long viewCount;

    private Date createTime;

}
