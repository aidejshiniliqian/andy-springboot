package com.andy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "子系统更新请求")
public class SubsystemUpdateDTO {

    @Schema(description = "子系统ID")
    @NotNull(message = "子系统ID不能为空")
    private Long id;

    @Schema(description = "子系统名称")
    private String subsystemName;

    @Schema(description = "子系统编码")
    private String subsystemCode;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态：0-禁用，1-启用")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}
