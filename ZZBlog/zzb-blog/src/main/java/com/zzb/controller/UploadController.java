package com.zzb.controller;

import com.zzb.domain.ResponseResult;
import com.zzb.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: zahngzongbin
 * @date: 2023/3/17 - 18:48
 * @mail: 2218722664@qq.com
 * @info:
 */
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @RequestMapping("upload")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);


    }
}
