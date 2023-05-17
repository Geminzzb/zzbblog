package com.zzb.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: zhangzongbin
 * @date: 2023/4/10 - 0:38
 * @mail: 2218722664@qq.com
 * @info:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLinkVo {
    private List<LinkVo> rows;
    private Long total;
}
