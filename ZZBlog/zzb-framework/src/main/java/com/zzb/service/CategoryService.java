package com.zzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.CategoryDto;
import com.zzb.domain.entity.Category;
import com.zzb.domain.vo.CategoryVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-03-09 16:19:34
 */
public interface CategoryService extends IService<Category> {


    ResponseResult getCategoryList();


    ResponseResult getNameArticleCategoryList();

    void getExcelData(String filename, HttpServletResponse response) throws IOException;

    ResponseResult getArticleCategoryList(Integer pageNum, Integer pageSize, CategoryDto categoryDto);
}
