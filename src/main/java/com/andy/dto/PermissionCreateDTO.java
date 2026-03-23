package com.andy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "菜单创建请求")
public class PermissionCreateDTO {

    @Schema(description = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String permissionName;

    @Schema(description = "菜单编码")
    @NotBlank(message = "菜单编码不能为空")
    private String permissionCode;

    @Schema(description = "菜单类型：1-目录，2-菜单，3-按钮")
    @NotNull(message = "菜单类型不能为空")
    private Integer permissionType;

    @Schema(description = "父级ID，顶级为0")
    private Long parentId = 0L;

    @Schema(description = "路由路径")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "排序")
    private Integer sort = 0;

    @Schema(description = "状态：0-禁用，1-启用")
    private Integer status = 1;

    @Schema(description = "子系统编码")
    private String subsystemCode;

    @Schema(description = "备注")
    private String remark;
}
