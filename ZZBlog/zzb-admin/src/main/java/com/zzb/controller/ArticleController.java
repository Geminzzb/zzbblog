package com.zzb.controller;

import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.AddArticleDto;
import com.zzb.domain.dto.ArticleDto;
import com.zzb.domain.dto.AdminArticleDto;
import com.zzb.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhangzongbin
 * @date: 2023/3/21 - 22:17
 * @mail: 2218722664@qq.com
 * @info:
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询博客列表
     *
     * @param pageNum    页面num
     * @param pageSize   页面大小
     * @param articleDto 文章dto
     * @return {@link ResponseResult}
     */
    @GetMapping("/list")
    public ResponseResult getAllArticleList(Integer pageNum, Integer pageSize, ArticleDto articleDto) {
        return articleService.getAllArticleList(pageNum, pageSize, articleDto);
    }

    /**
     * 添加博客
     *
     * @param articleDto 文章dto
     * @return {@link ResponseResult}
     */
    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto articleDto){
        return articleService.addArticle(articleDto);
    }

    /**
     * 通过id获取博客
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @GetMapping("/{id}")
    public ResponseResult getArticleById(@PathVariable Long id){
        return articleService.getArticleById(id);
    }
    /**
     * 修改博客
     *
     * @param adminArticleDto 文章细节签证官
     * @return {@link ResponseResult}
     */
    @PutMapping
    public ResponseResult updateArticle(@RequestBody AdminArticleDto adminArticleDto){
        return articleService.updateArticle(adminArticleDto);
    }

    /**
     * 删除博客
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable Long id){
        return articleService.deleteArticle(id);
    }

}
