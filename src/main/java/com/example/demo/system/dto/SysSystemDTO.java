package com.example.demo.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "子系统DTO")
public class SysSystemDTO {

    @Schema(description = "子系统ID")
    private Long id;

    @NotBlank(message = "子系统编码不能为空")
    @Schema(description = "子系统编码")
    private String systemCode;

    @NotBlank(message = "子系统名称不能为空")
    @Schema(description = "子系统名称")
    private String systemName;

    @Schema(description = "子系统描述")
    private String description;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态：0禁用，1正常")
    private Integer status;
}
