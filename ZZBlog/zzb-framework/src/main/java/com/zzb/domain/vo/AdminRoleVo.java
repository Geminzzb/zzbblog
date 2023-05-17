package com.zzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: zhangzongbin
 * @date: 2023/3/25 - 3:39
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRoleVo {

    private List<RoleVo> rows;

    private Long total;
}
