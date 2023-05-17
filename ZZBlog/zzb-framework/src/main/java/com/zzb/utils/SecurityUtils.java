package com.zzb.utils;

import com.zzb.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author: zahngzongbin
 * @date: 2023/3/16 - 22:20
 * @mail: 2218722664@qq.com
 * @info:
 */
public class SecurityUtils {

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && id.equals(1L);
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
