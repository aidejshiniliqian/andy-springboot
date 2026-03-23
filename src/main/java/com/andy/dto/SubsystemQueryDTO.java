package com.andy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "子系统查询请求")
public class SubsystemQueryDTO {

    @Schema(description = "子系统名称")
    private String subsystemName;

    @Schema(description = "子系统编码")
    private String subsystemCode;

    @Schema(description = "状态：0-禁用，1-启用")
    private Integer status;
}
