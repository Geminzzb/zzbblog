package com.zzb.service;

import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.AdminUserDto;
import com.zzb.domain.dto.UserInfoDto;
import com.zzb.domain.entity.User;
import com.zzb.domain.vo.UpdateUserInfoRoleIdVo;

/**
 * @author: zhangzongbin
 * @date: 2023/4/3 - 23:31
 * @mail: 2218722664@qq.com
 * @info:
 */
public interface AdminUserService {




    ResponseResult getUserInfoList(Integer pageNum, Integer pageSize, UserInfoDto userInfoDto);

    ResponseResult updateUserStatus(Integer userId, Integer status);

    ResponseResult getUserInfoById(Long id);

    ResponseResult updateUserInfo(UpdateUserInfoRoleIdVo updateUserInfoVo);

    ResponseResult deleteUser(Long id);

    ResponseResult addUser(AdminUserDto adminUserDto);
}
