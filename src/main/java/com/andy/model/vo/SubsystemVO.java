package com.andy.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "子系统VO")
public class SubsystemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "子系统ID")
    private Long id;

    @Schema(description = "子系统编码")
    private String code;

    @Schema(description = "子系统名称")
    private String name;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态(0:禁用,1:启用)")
    private Integer status;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
