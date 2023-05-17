package com.zzb.controller;

import com.zzb.domain.ResponseResult;
import com.zzb.domain.dto.RoleDto;
import com.zzb.domain.dto.RoleStatusDto;
import com.zzb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhangzongbin
 * @date: 2023/3/25 - 3:25
 * @mail: 2218722664@qq.com
 * @info:
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询出所有角色
     * @return {@link ResponseResult}
     */
    @GetMapping("/listAllRole")
    public ResponseResult getListAllRole(){
        return roleService.getListAllRole();
    }


    /**
     * 得到所有角色页面
     *
     * @param pageNum  页面num
     * @param pageSize 页面大小
     * @param roleDto  角色dto
     * @return {@link ResponseResult}
     */
    @GetMapping("/list")
    public ResponseResult getAllRoleByPage(Integer pageNum, Integer pageSize, RoleDto roleDto){
        System.out.println(pageNum+"-----------");
        System.out.println(pageSize+"-----------");
        System.out.println(roleDto+"-----------");
        return roleService.getAllRoleByPage(pageNum,pageSize,roleDto);
    }

    /**
     * 改变角色状态
     *
     *  @param roleStatusDto 角色状态dto
     * @return {@link ResponseResult}
     */
    @PutMapping("/changeStatus")
    public ResponseResult changeRoleStatus(@RequestBody RoleStatusDto roleStatusDto){


        return roleService.changeRoleStatus(roleStatusDto);
    }

    /**
     * 添加角色
     *
     * @param roleDto 角色dto
     * @return {@link ResponseResult}
     */
    @PostMapping
    public ResponseResult addRole(@RequestBody RoleDto roleDto){
        return roleService.addRole(roleDto);
    }

    /**
     * 通过id获取角色信息
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @GetMapping("/{id}")
    public ResponseResult getRoleInfoById(@PathVariable Long id){
        return roleService.getRoleInfoById(id);
    }

    /**
     * 修改角色信息
     *
     * @return {@link ResponseResult}
     */
    @PutMapping
    public ResponseResult updateRoleInfo(@RequestBody RoleDto roleDto){
        return roleService.updateRoleInfo(roleDto);
    }

    /**
     * 删除角色
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable Long id){
        return roleService.deleteRole(id);
    }
}
