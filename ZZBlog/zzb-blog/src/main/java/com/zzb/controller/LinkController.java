package com.zzb.controller;

import com.zzb.domain.ResponseResult;
import com.zzb.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zahngzongbin
 * @date: 2023/3/12 - 21:43
 * @mail: 2218722664@qq.com
 * @info:
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
