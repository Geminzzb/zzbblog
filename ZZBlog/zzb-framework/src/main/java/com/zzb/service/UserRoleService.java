package com.zzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzb.domain.entity.UserRole;

import java.util.List;


/**
 * 用户和角色关联表(UserRole)表服务接口
 *
 * @author makejava
 * @since 2023-04-03 23:57:07
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 得到用户角色id
     *
     * @param id id
     * @return {@link List}<{@link Long}>
     */
    public List<Long> getUserRoleById(Long id);
}

