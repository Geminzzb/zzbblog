package com.zzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzb.domain.entity.UserRole;
import com.zzb.mapper.UserRoleMapper;
import com.zzb.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2023-04-03 23:57:07
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;



    @Override
    public List<Long> getUserRoleById(Long id) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(UserRole::getRoleId).eq(UserRole::getUserId,id);
        List<UserRole> userRoles = userRoleMapper.selectList(queryWrapper);
        List<Long> roleIds = userRoles.stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toList());
        return roleIds;
    }
}

