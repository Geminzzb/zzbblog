package com.zzb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.LinkDto;
import com.zzb.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-12 21:32:45
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult getAllLinkByAdmin(Integer pageNum, Integer pageSize, LinkDto linkDto);
}

