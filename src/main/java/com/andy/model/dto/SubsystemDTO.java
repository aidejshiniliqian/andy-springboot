package com.andy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "子系统DTO")
public class SubsystemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "子系统ID")
    private Long id;

    @NotBlank(message = "子系统编码不能为空")
    @Schema(description = "子系统编码", required = true)
    private String code;

    @NotBlank(message = "子系统名称不能为空")
    @Schema(description = "子系统名称", required = true)
    private String name;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态(0:禁用,1:启用)")
    private Integer status;

    @Schema(description = "描述")
    private String description;
}
