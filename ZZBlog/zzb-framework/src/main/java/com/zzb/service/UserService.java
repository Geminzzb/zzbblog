package com.zzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-13 00:29:19
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}

