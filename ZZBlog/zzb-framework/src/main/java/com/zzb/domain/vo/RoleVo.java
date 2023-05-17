package com.zzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: zhangzongbin
 * @date: 2023/3/25 - 3:34
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//链式编程
@Accessors(chain = true)
public class RoleVo {
    private Long id;

    private String remark;

    private String roleKey;

    private String roleName;

    private Integer roleSort;

    private String createTime;

    private String status;

    private String delFlag;
}