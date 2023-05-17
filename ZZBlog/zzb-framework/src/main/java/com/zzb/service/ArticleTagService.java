package com.zzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzb.domain.entity.ArticleTag;

import java.util.List;


/**
 * 文章标签关联表(ArticleTag)表服务接口
 *
 * @author makejava
 * @since 2023-03-21 22:41:44
 */
public interface ArticleTagService extends IService<ArticleTag> {

    List<Long> getTagList(Long id);
}

