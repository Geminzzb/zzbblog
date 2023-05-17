package com.zzb.controller;

import com.zzb.domain.ResponseResult;
import com.zzb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zahngzongbin
 * @date: 2023/3/9 - 16:40
 * @mail: 2218722664@qq.com
 * @info:
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}

