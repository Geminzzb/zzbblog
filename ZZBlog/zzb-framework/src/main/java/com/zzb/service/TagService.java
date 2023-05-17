package com.zzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.TagByIdDto;
import com.zzb.domain.dto.TagDto;
import com.zzb.domain.entity.Tag;
import com.zzb.domain.vo.PageVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-03-19 17:51:33
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagDto tagListDto);

    ResponseResult addTag(TagDto tagDto);

    ResponseResult deleteTag(Long id);

    ResponseResult getTagById(Long id);

    ResponseResult updateTag(TagByIdDto tagByIdDto);

    ResponseResult getNameTagList();
}

