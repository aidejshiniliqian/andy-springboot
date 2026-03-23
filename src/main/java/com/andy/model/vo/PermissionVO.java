package com.andy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "权限VO")
public class PermissionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "权限ID")
    private Long id;

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

    @Schema(description = "子系统编码(pc:PC端,app:APP端)")
    private String subsystemCode;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "子权限列表")
    private List<PermissionVO> children;
}
