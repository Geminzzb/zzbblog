package com.zzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: zhangzongbin
 * @date: 2023/4/3 - 23:46
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserVo {
    private List<UserInfoVo> rows;
    private Long total;
}
