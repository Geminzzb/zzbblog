package com.zzb.domain.entity;

/**
 * @author: zahngzongbin
 * @date: 2023/3/16 - 17:20
 * @mail: 2218722664@qq.com
 * @info:
 */

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 评论表(Comment)表实体类
 *
 * @author makejava
 * @since 2023-03-16 17:18:10
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_comment")
public class Comment {
    @TableId
    private Long id;

    //评论类型（0代表文章评论，1代表友链评论）
    private String type;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;
    //在插入的时候进行自动填充
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
}
