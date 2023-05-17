package com.zzb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzb.domain.entity.Menu;

import java.util.List;
//import .entity.Menu;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-20 01:36:09
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<Long> selectMenuListByRoleId(Long roleId);
}

