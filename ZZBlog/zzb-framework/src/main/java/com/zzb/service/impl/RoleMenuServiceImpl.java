package com.zzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzb.domain.entity.RoleMenu;
import com.zzb.mapper.RoleMenuMapper;
import com.zzb.service.RoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-27 02:55:03
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Resource
    private RoleMenuMapper roleMenuMapper;
    /**
     * 通过id获取角色菜单id
     *
     * @param roleId 角色id
     * @return {@link List}<{@link String}>
     */
    public List<Long> getRoleMenuIdsById(Long roleId){
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(queryWrapper);
        List<Long> menuIds = roleMenus
                .stream()
                .map(roleMenu -> roleMenu.getMenuId()).collect(Collectors.toList());
        return menuIds;
    }


}

