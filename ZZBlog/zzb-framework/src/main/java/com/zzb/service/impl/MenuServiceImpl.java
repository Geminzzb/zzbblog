package com.zzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzb.constants.CommonConstants;
import com.zzb.constants.SystemConstants;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.MenuDto;
import com.zzb.domain.entity.Menu;
import com.zzb.domain.vo.MenuTreeVo;
import com.zzb.domain.vo.MenuVo;
import com.zzb.domain.vo.RoleMenuTreeSelectVo;
import com.zzb.enums.AppHttpCodeEnum;
import com.zzb.mapper.MenuMapper;
import com.zzb.service.MenuService;
import com.zzb.utils.BeanCopyPropertiesUtils;
import com.zzb.utils.SecurityUtils;
import com.zzb.utils.SystemConverter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-20 01:36:09
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Lazy
    @Resource
    private MenuService menuService;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if(id == 1L){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    /**
     * 查询菜单列表
     *
     * @param status   状态
     * @param menuName 菜单名称
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getMenuList(String status, String menuName) {
//        1.根据menu状态和menuName查询
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status), Menu::getStatus, status)
                .like(StringUtils.hasText(menuName), Menu::getMenuName, menuName)
                .orderByAsc(Menu::getParentId, Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
//        2.将List<Menu>对象转换为List<MenuVo>对象
        List<MenuVo> menuVos = BeanCopyPropertiesUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    /**
     * 添加菜单
     *
     * @param menuDto 菜单dto
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult addMenu(MenuDto menuDto) {
//        1.将MenuDto对象转换为Menu对象
        Menu menu = BeanCopyPropertiesUtils.copyBean(menuDto, Menu.class);
//        2.根据MenuName判断当前是否存在menu
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getMenuName, menu.getMenuName());
        Menu oneMenu = getOne(queryWrapper);
        if (!Objects.isNull(oneMenu)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ADD_MENU_FAIL);
        }
        save(menu);
        return ResponseResult.okResult();
    }

    /**
     * 通过id获取菜单
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getMenuById(Long id) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getId, id);
        Menu menu = getOne(queryWrapper);
//        将Menu对象转换为MenuVo对象
        MenuVo menuVo = BeanCopyPropertiesUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    /**
     * 更新菜单
     *
     * @param menuDto
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult updateMenu(MenuDto menuDto) {
        //修改的时候不能把父菜单设置为当前菜单
        if (menuDto.getParentId().equals(menuDto.getId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_MENU_FAIL);
        }
        //        1.判断LinkDto对象值是否为空
        if (!StringUtils.hasText(menuDto.getMenuName()) ||
                !StringUtils.hasText(menuDto.getMenuType()) ||
                !StringUtils.hasText(String.valueOf(menuDto.getStatus())) ||
                !StringUtils.hasText(menuDto.getPath()) ||
                !StringUtils.hasText(String.valueOf(menuDto.getOrderNum())) ||
                !StringUtils.hasText(menuDto.getIcon())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.CONTENT_IS_BLANK);
        }
//        1.将MenuDto对象转化为Menu对象
        Menu menu = BeanCopyPropertiesUtils.copyBean(menuDto, Menu.class);
        updateById(menu);
        return ResponseResult.okResult();
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult deleteMenu(Long id) {
//        1.查询当前菜单是否有子菜单，如果有就不允许删除
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId, id);
        List<Menu> menus = list(queryWrapper);
        //List<Menu> menus = menuMapper.selectList(queryWrapper);
        System.out.println(menus+"menus");
        if (!Objects.isNull(menus) && menus.size() != 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_MENU_REFUSE);
        }

        removeById(id);
        return ResponseResult.okResult();
    }
    @Override
    public List<Menu> selectMenuList(Menu menu) {

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //menuName模糊查询
        queryWrapper.like(StringUtils.hasText(menu.getMenuName()),Menu::getMenuName,menu.getMenuName());
        queryWrapper.eq(StringUtils.hasText(menu.getStatus()),Menu::getStatus,menu.getStatus());
        //排序 parent_id和order_num
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);;
        return menus;
    }

    /**
     * 角色菜单treeselect
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult roleMenuTreeselect(Long id) {
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<Long> checkedKeys = this.selectMenuListByRoleId(id);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }
    /**
     * 选择菜单通过角色id列表
     * @param roleId 角色id
     * @return {@link List}<{@link Long}>
     */
    public List<Long> selectMenuListByRoleId(Long roleId) {
        return getBaseMapper().selectMenuListByRoleId(roleId);
    }

    /**
     * 查询菜单树
     *
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getMenuTree() {
// 复用之前的selectMenuList方法。方法需要参数，参数可以用来进行条件查询，而这个方法不需要条件，所以直接new Menu()传入
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<MenuTreeVo> menuTreeVos =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(menuTreeVos);
    }

    /**
     * 构建menu树(menu父子关系)
     *
     * @param menus
     * @return {@link List}<{@link Menu}>
     */
    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}

