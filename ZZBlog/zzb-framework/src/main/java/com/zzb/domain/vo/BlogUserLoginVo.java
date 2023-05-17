package com.zzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zahngzongbin
 * @date: 2023/3/13 - 15:03
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserLoginVo {

    private String token;
    private UserInfoVo userInfo;
}
