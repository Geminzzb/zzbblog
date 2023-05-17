package com.zzb.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author: zahngzongbin
 * @date: 2023/3/16 - 17:59
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {

    private Long id;

    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //所回复的目标评论的名字
    private String toCommentUserName;
    //回复目标评论id
    private Long toCommentId;

    private Long createBy;

    private Date updateTime;

    private String username;

    private List<CommentVo> children;
}
