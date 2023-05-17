package com.zzb.controller;

import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.AdminUserDto;
import com.zzb.domain.dto.UserInfoDto;
import com.zzb.domain.entity.User;
import com.zzb.domain.vo.UpdateUserInfoRoleIdVo;
import com.zzb.service.AdminUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: zhangzongbin
 * @date: 2023/4/3 - 23:29
 * @mail: 2218722664@qq.com
 * @info:
 */

@RestController
@RequestMapping("/system/user")
public class AdminUserController {

    @Resource
    private AdminUserService adminUserService;


    /**
     * 得到用户信息列表
     *
     * @param pageNum     页面num
     * @param pageSize    页面大小
     * @param userInfoDto 用户信息dto
     * @return {@link ResponseResult}
     */
    @GetMapping("/list")
    public ResponseResult getUserInfoList(Integer pageNum, Integer pageSize, UserInfoDto userInfoDto){
        return adminUserService.getUserInfoList(pageNum,pageSize,userInfoDto);
    }


    /**
     * 通过id获取用户信息
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @GetMapping("/{id}")
    public ResponseResult getUserInfoById(@PathVariable Long id){
        return adminUserService.getUserInfoById(id);
    }



    /**
     * 更新用户状态
     *
     * @param userId 用户id
     * @param status 状态
     * @return {@link ResponseResult}
     */
    @PutMapping("/changeStatus")
    public ResponseResult updateUserStatus(Integer userId,Integer status){
        return adminUserService.updateUserStatus(userId,status);
    }

    /**
     * 更新用户信息
     *
     * @param updateUserInfoRoleIdVo 更新用户信息签证官
     * @return {@link ResponseResult}
     */
    @PutMapping
    public ResponseResult updateUserInfo(@RequestBody UpdateUserInfoRoleIdVo updateUserInfoRoleIdVo){
        return adminUserService.updateUserInfo(updateUserInfoRoleIdVo);
    }

    /**
     * 删除用户
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id){
        return adminUserService.deleteUser(id);
    }

    /**
     * 添加用户
     *
     * @param adminUserDto 管理用户dto
     * @return {@link ResponseResult}
     */
    @PostMapping
    public ResponseResult addUser(@RequestBody AdminUserDto adminUserDto){
        return adminUserService.addUser(adminUserDto);
    }



}