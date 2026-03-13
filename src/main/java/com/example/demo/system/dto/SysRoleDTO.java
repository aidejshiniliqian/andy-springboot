package com.example.demo.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "角色DTO")
public class SysRoleDTO {

    @Schema(description = "角色ID")
    private Long id;

    @NotBlank(message = "角色名称不能为空")
    @Schema(description = "角色名称")
    private String roleName;

    @NotBlank(message = "角色标识不能为空")
    @Schema(description = "角色标识")
    private String roleKey;

    @Schema(description = "角色排序")
    private Integer sort;

    @Schema(description = "状态：0禁用，1正常")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}
