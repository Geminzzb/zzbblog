package com.zzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.RoleDto;
import com.zzb.domain.dto.RoleStatusDto;
import com.zzb.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-20 01:39:25
 */
public interface RoleService extends IService<Role> {


    List<Role> getAllRoleList();

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult getAllRoleByPage(Integer pageNum, Integer pageSize, RoleDto roleDto);


    ResponseResult changeRoleStatus(RoleStatusDto roleStatusDto);

    ResponseResult addRole(RoleDto roleDto);

    ResponseResult getRoleInfoById(Long id);

    ResponseResult updateRoleInfo(RoleDto roleDto);

    ResponseResult deleteRole(Long id);

    ResponseResult getListAllRole();

}

