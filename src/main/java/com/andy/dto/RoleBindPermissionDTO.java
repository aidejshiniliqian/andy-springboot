package com.andy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "角色绑定菜单请求")
public class RoleBindPermissionDTO {

    @Schema(description = "角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    @Schema(description = "菜单ID列表")
    @NotNull(message = "菜单ID列表不能为空")
    private List<Long> permissionIds;
}
