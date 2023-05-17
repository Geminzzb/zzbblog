package com.zzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.AddArticleDto;
import com.zzb.domain.dto.ArticleDto;
import com.zzb.domain.entity.Article;
import com.zzb.domain.dto.AdminArticleDto;

/**
 * @author: zahngzongbin
 * @date: 2023/3/8 - 14:35
 * @mail: 2218722664@qq.com
 * @info:
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto articleDto);

    ResponseResult getAllArticleList(Integer pageNum, Integer pageSize, ArticleDto articleDto);

    ResponseResult getArticleById(Long id);

    ResponseResult updateArticle(AdminArticleDto adminArticleDto);

    ResponseResult deleteArticle(Long id);
}
