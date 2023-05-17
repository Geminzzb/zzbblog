package com.zzb.controller;


import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.LinkDto;
import com.zzb.service.LinkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Resource
    private LinkService linkService;


    /**
     * 分页查询所有友链-Admin
     *
     * @return {@link ResponseResult}
     */
    @GetMapping("/list")
    public ResponseResult getLinkList(Integer pageNum, Integer pageSize, LinkDto linkDto) {
        return linkService.getAllLinkByAdmin(pageNum, pageSize, linkDto);
    }




}


