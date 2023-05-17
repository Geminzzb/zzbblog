package com.zzb.service.impl;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzb.constants.CommonConstants;
import com.zzb.constants.SystemConstants;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.CategoryDto;
import com.zzb.domain.entity.Article;
import com.zzb.domain.entity.Category;
import com.zzb.domain.vo.AdminCategoryVo;
import com.zzb.domain.vo.CategoryTwoVo;
import com.zzb.domain.vo.CategoryVo;

import com.zzb.domain.vo.DownloadDataVo;
import com.zzb.enums.AppHttpCodeEnum;
import com.zzb.mapper.CategoryMapper;
import com.zzb.service.ArticleService;
import com.zzb.service.CategoryService;
import com.zzb.utils.BeanCopyPropertiesUtils;
import com.zzb.utils.BeanCopyUtils;
import com.zzb.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-03-09 16:19:35
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult getNameArticleCategoryList() {
        //1.查询所有文章类别列表
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, CommonConstants.CATEGORY_STATUS_NORMAL);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
//        2.将Category对象转换为CategoryVo对象
        List<CategoryVo> categoryVos = BeanCopyPropertiesUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);

    }

    @Override
    public void getExcelData(String filename, HttpServletResponse response){
//        使用工具类操作Excel
//        1.设置下载文件的请求头
        try {
            WebUtils.setDownLoadHeader(filename, response);
//        2.获取需要导出的数据
            LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Category::getStatus, CommonConstants.CATEGORY_STATUS_NORMAL);
            List<Category> categories = categoryMapper.selectList(queryWrapper);
//        3.将Category对象转化为DownloadDataVo对象
            List<DownloadDataVo> downloadDataVos = BeanCopyPropertiesUtils.copyBeanList(categories, DownloadDataVo.class);
//        3.将数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), DownloadDataVo.class).autoCloseStream(Boolean.FALSE).sheet("文章分类")
                    .doWrite(downloadDataVos);

        } catch (Exception e) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response,JSON.toJSONString(result));
        }
    }

    /**
     * 查询文章类别列表
     *
     * @param pageNum     页面num
     * @param pageSize    页面大小
     * @param categoryDto 类别dto
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getArticleCategoryList(Integer pageNum, Integer pageSize, CategoryDto categoryDto) {
        //        1.根据文章分类名(模糊查询)和状态进行查询
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(categoryDto.getStatus()), Category::getStatus, categoryDto.getStatus());
        queryWrapper.like(StringUtils.hasText(categoryDto.getName()), Category::getName, categoryDto.getName());

//        2.分页查询
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

//        3.将当前页中的Category对象转换为CategoryTwoVo对象
        List<Category> categories = page.getRecords();
        List<CategoryTwoVo> categoryTwoVos = BeanCopyPropertiesUtils.copyBeanList(categories, CategoryTwoVo.class);
//        4.将CategoryTwoVo对象转换为AdminCategoryVo对象
        AdminCategoryVo adminCategoryVo = new AdminCategoryVo(categoryTwoVos, page.getTotal());
        return ResponseResult.okResult(adminCategoryVo);
    }


}


