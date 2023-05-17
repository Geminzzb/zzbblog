package com.zzb.job;

import com.zzb.domain.entity.Article;
import com.zzb.service.ArticleService;
import com.zzb.utils.RedisCache;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: zhangzongbin
 * @date: 2023/3/19 - 0:11
 * @mail: 2218722664@qq.com
 * @info:
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void UpdateViewCount(){
        //获取redis中的游览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        //处理数据
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库
        articleService.updateBatchById(articles);
    }
}
