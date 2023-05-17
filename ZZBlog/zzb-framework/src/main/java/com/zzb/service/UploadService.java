package com.zzb.service;

import com.zzb.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: zahngzongbin
 * @date: 2023/3/17 - 18:51
 * @mail: 2218722664@qq.com
 * @info:
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
