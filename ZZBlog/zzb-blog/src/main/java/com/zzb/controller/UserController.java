package com.zzb.controller;

import com.zzb.annocation.SystemLog;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.entity.User;
import com.zzb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zahngzongbin
 * @date: 2023/3/17 - 13:50
 * @mail: 2218722664@qq.com
 * @info:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    public ResponseResult regitter(@RequestBody User user){
        return userService.register(user);

    }

}
