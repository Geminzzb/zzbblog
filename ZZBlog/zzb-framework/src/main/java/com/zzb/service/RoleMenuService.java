package com.zzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzb.domain.entity.RoleMenu;

import java.util.List;


/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author makejava
 * @since 2023-03-27 02:55:03
 */
public interface RoleMenuService extends IService<RoleMenu> {
    public List<Long> getRoleMenuIdsById(Long roleId);

}

