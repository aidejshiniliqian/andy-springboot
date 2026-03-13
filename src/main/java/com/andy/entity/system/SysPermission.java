package com.andy.entity.system;

import com.andy.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
@Schema(description = "系统权限")
public class SysPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "权限编码")
    private String permissionCode;

    @Schema(description = "权限名称")
    private String permissionName;

    @Schema(description = "权限类型(1:菜单,2:按钮,3:接口)")
    private Integer type;

    @Schema(description = "父级ID")
    private Long parentId;

    @Schema(description = "权限路径")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态(0:禁用,1:启用)")
    private Integer status;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "子权限列表")
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private java.util.List<SysPermission> children;
}
