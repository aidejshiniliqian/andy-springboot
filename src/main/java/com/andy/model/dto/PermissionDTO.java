package com.andy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "权限DTO")
public class PermissionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "权限ID")
    private Long id;

    @NotBlank(message = "权限编码不能为空")
    @Schema(description = "权限编码", required = true)
    private String permissionCode;

    @NotBlank(message = "权限名称不能为空")
    @Schema(description = "权限名称", required = true)
    private String permissionName;

    @NotNull(message = "权限类型不能为空")
    @Schema(description = "权限类型(1:菜单,2:按钮,3:接口)", required = true)
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

    @Schema(description = "子系统编码(pc:PC端,app:APP端)")
    private String subsystemCode;
}
