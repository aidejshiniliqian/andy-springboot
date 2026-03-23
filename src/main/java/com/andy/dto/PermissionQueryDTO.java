package com.andy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "菜单查询请求")
public class PermissionQueryDTO {

    @Schema(description = "菜单名称")
    private String permissionName;

    @Schema(description = "菜单编码")
    private String permissionCode;

    @Schema(description = "菜单类型：1-目录，2-菜单，3-按钮")
    private Integer permissionType;

    @Schema(description = "状态：0-禁用，1-启用")
    private Integer status;

    @Schema(description = "子系统编码")
    private String subsystemCode;
}
