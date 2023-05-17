package com.zzb.controller;

import com.zzb.domain.ResponseResult;
import com.zzb.domain.entity.LoginUser;
import com.zzb.domain.entity.Menu;
import com.zzb.domain.entity.User;
import com.zzb.domain.vo.AdminUserInfoVo;
import com.zzb.domain.vo.RoutersVo;
import com.zzb.domain.vo.UserInfoVo;
import com.zzb.enums.AppHttpCodeEnum;
import com.zzb.exception.SystemException;
import com.zzb.service.LoginService;
import com.zzb.service.MenuService;
import com.zzb.service.RoleService;
import com.zzb.utils.BeanCopyUtils;
import com.zzb.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: zhangzongbin
 * @date: 2023/3/19 - 19:31
 * @mail: 2218722664@qq.com
 * @info:
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }



    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

}