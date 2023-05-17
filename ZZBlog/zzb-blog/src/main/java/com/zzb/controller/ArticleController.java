package com.zzb.controller;

import com.zzb.domain.ResponseResult;
import com.zzb.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zahngzongbin
 * @date: 2023/3/8 - 14:44
 * @mail: 2218722664@qq.com
 * @info:
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> test(){
//        return articleService.list();
//    }

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        //查询热门文章，封装成ResponseResult
        ResponseResult result =  articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    public ResponseResult articleList (Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail (@PathVariable ("id") Long id){
        return  articleService.getArticleDetail(id);

    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }



}
