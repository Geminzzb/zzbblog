package com.zzb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzb.domain.entity.Category;
import org.mapstruct.Mapper;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-09 16:23:04
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}

