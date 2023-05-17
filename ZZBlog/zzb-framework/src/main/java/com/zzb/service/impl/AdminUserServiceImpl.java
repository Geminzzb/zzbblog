package com.zzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.AdminUserDto;
import com.zzb.domain.dto.UserInfoDto;
import com.zzb.domain.entity.Role;
import com.zzb.domain.entity.User;
import com.zzb.domain.entity.UserRole;
import com.zzb.domain.vo.*;
import com.zzb.enums.AppHttpCodeEnum;
import com.zzb.exception.SystemException;
import com.zzb.mapper.MenuMapper;
import com.zzb.mapper.RoleMapper;
import com.zzb.mapper.UserMapper;
import com.zzb.service.AdminUserService;
import com.zzb.service.RoleService;
import com.zzb.service.UserRoleService;
import com.zzb.service.UserService;
import com.zzb.utils.BeanCopyPropertiesUtils;
import com.zzb.utils.RedisCache;
import com.zzb.utils.SecurityUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: zhangzongbin
 * @date: 2023/4/3 - 23:33
 * @mail: 2218722664@qq.com
 * @info:
 */

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;

    /**
     * 得到用户信息列表
     *
     * @param pageNum     页面num
     * @param pageSize    页面大小
     * @param userInfoDto 用户信息dto
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getUserInfoList(Integer pageNum, Integer pageSize, UserInfoDto userInfoDto) {
//        1.根据用户名(模糊查询)和状态进行查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(userInfoDto.getStatus()), User::getStatus, userInfoDto.getStatus());
        queryWrapper.eq(StringUtils.hasText(userInfoDto.getPhonenumber()), User::getPhonenumber, userInfoDto.getPhonenumber());
        queryWrapper.like(StringUtils.hasText(userInfoDto.getUserName()), User::getUserName, userInfoDto.getUserName());

//        2.分页查询
        Page<User> page = new Page(pageNum, pageSize);
        userMapper.selectPage(page, queryWrapper);
        List<User> users = page.getRecords();

//        3.将当前页中的User对象转换为UserInfoVo对象
        List<UserInfoVo> userInfoVos = BeanCopyPropertiesUtils.copyBeanList(users, UserInfoVo.class);
////        4.将LinkVo对象转换为LinkAdminVo对象
        AdminUserVo adminUserVo = new AdminUserVo(userInfoVos, page.getTotal());
        return ResponseResult.okResult(adminUserVo);
    }

    /**
     * 更新用户状态
     *
     * @param userId 用户id
     * @param status 状态
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult updateUserStatus(Integer userId, Integer status) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, userId)
                .set(User::getStatus, status);
        userService.update(null, updateWrapper);
        return ResponseResult.okResult();
    }


    /**
     * 通过id获取用户信息
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult getUserInfoById(Long id) {
//        1.通过id查询用户信息
        User userById = userService.getById(id);
//          1.1将User对象转换为UpdateUserInfoVo对象
        UpdateUserInfoVo user = BeanCopyPropertiesUtils.copyBean(userById, UpdateUserInfoVo.class);
//        2.查询用户所具有的角色id
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        List<Long> userRoleId = userRoleService.getUserRoleById(id);
//        3.查询所有角色的列表
        List<Role> allRoleList = roleService.getAllRoleList();
//            3.1将List<Role>对象转换为List<RoleVo>对象
        List<RoleVo> roleVos = BeanCopyPropertiesUtils.copyBeanList(allRoleList, RoleVo.class);

        UpdateUserVo updateUserVo = new UpdateUserVo(userRoleId, roleVos, user);
        return ResponseResult.okResult(updateUserVo);
    }
//
    /**
     * 更新用户信息
     *
     * @param updateUserInfoVo 更新用户信息签证官
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult updateUserInfo(UpdateUserInfoRoleIdVo updateUserInfoVo) {
//        1.获取到修改后的用户的roleIds列表
        List<Long> roleIds = updateUserInfoVo.getRoleIds();
//        2.将UpdateUserInfoRoleIdVo对象转换为User对象
        User user = BeanCopyPropertiesUtils.copyBean(updateUserInfoVo, User.class);
//          2.1查询当前用户roleIds列表
        List<Long> userRoleById = userRoleService.getUserRoleById(user.getId());
//          2.2遍历修改后的用户的roleIds列表，如果有新增的就添加到sys_user_role表中
        for (Long roleId : roleIds) {
            if (!userRoleById.contains(roleId)) {
                userRoleService.save(new UserRole(user.getId(), roleId));
            }
        }

//        2.根据用户id修改用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, user.getId());
        userService.update(user, queryWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 删除用户
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult deleteUser(Long id) {
//        1.从SecurityContextHolder当中获取到当前登录用户的id
        Long userId = SecurityUtils.getUserId();
        if (userId == id) {
//            1.1如果是当前登录的用户则不允许删除
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_USER_REFUSE);
        }
//        2.删除用户所对应sys_user_role表中的角色信息
        userRoleService.removeById(id);
        userService.removeById(id);
        return ResponseResult.okResult();
    }


    /**
     * 添加用户
     *
     * @param adminUserDto 管理用户dto
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult addUser(AdminUserDto adminUserDto) {
//        1.获取到AdminUserDto对象当中的roleIds属性
        List<Long> roleIds = adminUserDto.getRoleIds();
//        2.将AdminUserDto对象转化为User对象
        User user = BeanCopyPropertiesUtils.copyBean(adminUserDto, User.class);
//        3.判断信息是否为空
        if (!StringUtils.hasText(user.getUserName()) ||
                !StringUtils.hasText(user.getNickName()) ||
                !StringUtils.hasText(user.getPassword()) ||
                !StringUtils.hasText(user.getEmail()) ||
                !StringUtils.hasText(user.getPhonenumber()) ||
                !StringUtils.hasText(user.getStatus()) ||
                !StringUtils.hasText(user.getSex())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_IS_BLANK);
        }
//        4.判断信息是否存在
        if (!judgeUsername(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (!judgePhoneNumber(user.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (!judgeEmail(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
//        5.保存用户
        userService.save(user);
//        6.获取到用户id
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.getUserName());
        User getUser = userService.getOne(queryWrapper);

//        7.向sys_user_role表中添加数据
        roleIds.stream()
                .map(roleId -> userRoleService.save
                        (new UserRole(getUser.getId(),roleId)));
        return ResponseResult.okResult();
    }



    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean judgeUsername(String username){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getUserName, username);
        User user = userService.getOne(queryWrapper);
        if (Objects.isNull(user)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断邮箱是否存在
     * @param email
     * @return
     */
    public boolean judgeEmail(String email){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getEmail, email);
        User user = userService.getOne(queryWrapper);
        if (Objects.isNull(user)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断手机号是否存在
     * @param phonenumber
     * @return
     */
    public boolean judgePhoneNumber(String phonenumber){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getPhonenumber, phonenumber);
        User user = userService.getOne(queryWrapper);
        if (Objects.isNull(user)){
            return true;
        }else{
            return false;
        }
    }


}