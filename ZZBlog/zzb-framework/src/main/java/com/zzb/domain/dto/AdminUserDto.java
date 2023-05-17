package com.zzb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: zhangzongbin
 * @date: 2023/4/3 - 23:45
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserDto {
    private String userName;

    private String nickName;

    private String password;

    private String phonenumber;

    private String email;

    private String sex;

    private String status;

    private List<Long> roleIds;
}
