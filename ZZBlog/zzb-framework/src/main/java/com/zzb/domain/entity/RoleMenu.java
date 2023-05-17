package com.zzb.domain.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 角色和菜单关联表
 * @TableName sys_role_menu
 */
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="sys_role_menu")
@Data
public class RoleMenu implements Serializable {
    /**
     * 角色ID
     */
    @TableId
    private Long roleId;

    /**
     * 菜单ID
     */

    private Long menuId;

}
