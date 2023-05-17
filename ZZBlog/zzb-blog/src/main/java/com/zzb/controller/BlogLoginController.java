package com.zzb.controller;

import com.zzb.domain.ResponseResult;
import com.zzb.domain.entity.User;
import com.zzb.enums.AppHttpCodeEnum;
import com.zzb.exception.SystemException;
import com.zzb.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zahngzongbin
 * @date: 2023/3/13 - 14:42
 * @mail: 2218722664@qq.com
 * @info:
 */
@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示必须传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
      }

      //退出登录
    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
