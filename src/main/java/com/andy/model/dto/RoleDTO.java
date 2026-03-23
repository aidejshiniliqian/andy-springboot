package com.andy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "角色DTO")
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色ID")
    private Long id;

    @NotBlank(message = "角色编码不能为空")
    @Schema(description = "角色编码", required = true)
    private String roleCode;

    @NotBlank(message = "角色名称不能为空")
    @Schema(description = "角色名称", required = true)
    private String roleName;

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "状态(0:禁用,1:启用)")
    private Integer status;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "权限ID列表")
    private List<Long> permissionIds;
}
