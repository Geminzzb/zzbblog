package com.zzb.controller;

import com.zzb.constants.CommonConstants;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.CategoryDto;
import com.zzb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: zhangzongbin
 * @date: 2023/3/21 - 19:57
 * @mail: 2218722664@qq.com
 * @info:
 */
@RestController
@RequestMapping("/content/category")
public class ArticleCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询文章类别列表
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/listAllCategory")
    public ResponseResult ArticleCategoryList(){
        return categoryService.getNameArticleCategoryList();
    }

    /**
     * 导出excel
     * @param response 响应
     * 使用自定义的权限校验方法
     */
    @PreAuthorize("@permission.hasAuthority('content:category:export')")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response){
        try {
            categoryService.getExcelData(CommonConstants.ARTICLE_CATEGORY,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询文章类别列表
     * @param pageNum     页面num
     * @param pageSize    页面大小
     * @param categoryDto 类别dto
     * @return {@link ResponseResult}
     */
    @GetMapping("/list")
    public ResponseResult getArticleCategoryList(Integer pageNum, Integer pageSize, CategoryDto categoryDto) {
        return categoryService.getArticleCategoryList(pageNum, pageSize, categoryDto);
    }


}
