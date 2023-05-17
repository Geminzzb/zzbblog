package com.zzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhangzongbin
 * @date: 2023/4/3 - 23:54
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoVo {
    /**
     * 主键
     */
    private Long id;

    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    private String phonenumber;

    private String sex;

    private String email;

    private String status;
}