package com.zzb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzb.constants.CommonConstants;
import com.zzb.domain.entity.LoginUser;
import com.zzb.domain.entity.User;
import com.zzb.mapper.MenuMapper;
import com.zzb.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: zahngzongbin
 * @date: 2023/3/13 - 15:00
 * @mail: 2218722664@qq.com
 * @info:
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        //判断是否查到用户  如果没查到抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //返回用户信息
        //    2.查询当前用户的权限信息，如果是管理员就封装权限信息返回
        if(user.getType().equals(CommonConstants.ADMAIN)){
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,list);
        }
        // TODO 查询权限信息封装
        return new LoginUser(user,null);
    }
}