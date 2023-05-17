package com.zzb.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: zhangzongbin
 * @date: 2023/3/19 - 16:49
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加评论dto")
public class AddCommentDto {
    private Long id;


    @ApiModelProperty(notes = "评论类型（0代表文章评论，1代表友链评论）")
    private String type;

    @ApiModelProperty(notes = "文章id")
    private Long articleId;

    @ApiModelProperty(notes = "根评论id")
    private Long rootId;

    @ApiModelProperty(notes = "评论内容")
    private String content;

    @ApiModelProperty(notes = "所回复的目标评论的userid")
    private Long toCommentUserId;

    @ApiModelProperty(notes = "回复目标评论id")
    private Long toCommentId;

    @ApiModelProperty(notes = "在插入的时候进行自动填充")
    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
}
