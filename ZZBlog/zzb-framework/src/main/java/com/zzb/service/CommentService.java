package com.zzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-03-16 17:18:10
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

