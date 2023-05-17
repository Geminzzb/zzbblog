package com.zzb.controller;

import com.zzb.domain.ResponseResult;
import com.zzb.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author: zhangzongbin
 * @date: 2023/3/21 - 21:46
 * @mail: 2218722664@qq.com
 * @info:
 */
@RestController
public class UploadController {


    @Autowired
    private UploadService uploadService;

    /**
     * 上传img
     *
     * @param img img
     * @return {@link ResponseResult}
     */
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
