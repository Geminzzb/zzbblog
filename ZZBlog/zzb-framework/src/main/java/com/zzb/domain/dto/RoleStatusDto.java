package com.zzb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhangzongbin
 * @date: 2023/3/25 - 4:54
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleStatusDto {
    private Integer roleId;

    private Integer status;
}
