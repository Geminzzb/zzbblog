package com.zzb.runner;

import com.zzb.domain.entity.Article;
import com.zzb.mapper.ArticleMapper;
import com.zzb.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: zhangzongbin
 * @date: 2023/3/18 - 22:21
 * @mail: 2218722664@qq.com
 * @info:
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息   id   viewcount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        //存储到redis
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
