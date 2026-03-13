package com.example.demo.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "菜单DTO")
public class SysMenuDTO {

    @Schema(description = "菜单ID")
    private Long id;

    @NotBlank(message = "菜单名称不能为空")
    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "父菜单ID")
    private Long parentId;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "菜单标识（权限标识）")
    private String perms;

    @Schema(description = "菜单类型：M目录，C菜单，F按钮")
    private String menuType;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态：0禁用，1正常")
    private Integer status;
}
