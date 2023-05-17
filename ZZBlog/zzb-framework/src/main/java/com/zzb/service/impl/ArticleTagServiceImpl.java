package com.zzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzb.domain.entity.ArticleTag;
import com.zzb.mapper.ArticleTagMapper;
import com.zzb.service.ArticleTagService;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-21 22:41:44
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
    /**
     * 得到文章标签列表
     *
     * @param id id
     * @return {@link List}
     */
    @Test
    public List<Long> getTagList(Long id) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, id);
        List<ArticleTag> articleTagList = list(queryWrapper);

        List<Long> tags = articleTagList
                .stream()
                .map(articleTag -> articleTag.getTagId())
                .collect(Collectors.toList());
        return tags;
    }
}

