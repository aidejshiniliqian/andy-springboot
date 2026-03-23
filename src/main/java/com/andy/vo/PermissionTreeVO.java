package com.andy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "菜单树VO")
public class PermissionTreeVO {

    @Schema(description = "菜单ID")
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

    @Schema(description = "子系统编码")
    private String subsystemCode;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "子菜单")
    private List<PermissionTreeVO> children;
}
