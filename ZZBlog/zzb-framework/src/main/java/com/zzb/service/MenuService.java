package com.zzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.MenuDto;
import com.zzb.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-03-20 01:36:09
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getMenuList(String status, String menuName);

    ResponseResult addMenu(MenuDto menuDto);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenu(MenuDto menuDto);

    ResponseResult deleteMenu(Long id);

    ResponseResult getMenuTree();

    List<Menu> selectMenuList(Menu menu);

    ResponseResult roleMenuTreeselect(Long id);
}

