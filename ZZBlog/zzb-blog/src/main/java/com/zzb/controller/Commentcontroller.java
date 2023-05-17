package com.zzb.controller;

import com.zzb.constants.SystemConstants;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.AddCommentDto;
import com.zzb.domain.entity.Comment;
import com.zzb.service.CommentService;
import com.zzb.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zahngzongbin
 * @date: 2023/3/16 - 17:40
 * @mail: 2218722664@qq.com
 * @info:
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论")
public class Commentcontroller {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @ApiOperation(value = "友链评论列表",notes = "获取一页友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    }
    )
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);

    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }

}
