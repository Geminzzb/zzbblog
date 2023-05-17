package com.zzb.service;

import com.zzb.domain.ResponseResult;
import com.zzb.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}