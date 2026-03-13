package com.andy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "菜单更新请求")
public class PermissionUpdateDTO {

    @Schema(description = "菜单ID")
    @NotNull(message = "菜单ID不能为空")
    private Long id;

    @Schema(description = "菜单名称")
    private String permissionName;

    @Schema(description = "菜单编码")
    private String permissionCode;

    @Schema(description = "菜单类型：1-目录，2-菜单，3-按钮")
    private Integer permissionType;

    @Schema(description = "父级ID")
    private Long parentId;

    @Schema(description = "路由路径")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态：0-禁用，1-启用")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}
