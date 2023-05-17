package com.zzb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzb.domain.entity.Role;

import java.util.List;
//import .entity.Role;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-20 01:39:25
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}

