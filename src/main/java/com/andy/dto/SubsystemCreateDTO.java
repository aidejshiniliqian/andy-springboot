package com.andy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "子系统创建请求")
public class SubsystemCreateDTO {

    @Schema(description = "子系统名称")
    @NotBlank(message = "子系统名称不能为空")
    private String subsystemName;

    @Schema(description = "子系统编码")
    @NotBlank(message = "子系统编码不能为空")
    private String subsystemCode;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "排序")
    private Integer sort = 0;

    @Schema(description = "状态：0-禁用，1-启用")
    private Integer status = 1;

    @Schema(description = "备注")
    private String remark;
}
