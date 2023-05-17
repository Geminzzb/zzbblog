package com.zzb.service;

import com.zzb.domain.ResponseResult;
import com.zzb.domain.entity.User;

/**
 * @author: zahngzongbin
 * @date: 2023/3/13 - 14:47
 * @mail: 2218722664@qq.com
 * @info:
 */
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}