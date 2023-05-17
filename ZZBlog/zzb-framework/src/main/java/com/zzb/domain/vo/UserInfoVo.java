package com.zzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: zahngzongbin
 * @date: 2023/3/13 - 15:03
 * @mail: 2218722664@qq.com
 * @info:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;

    private String phonenumber;

    private String status;

    private String createTime;
}

