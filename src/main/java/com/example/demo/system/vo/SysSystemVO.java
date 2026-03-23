package com.example.demo.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "子系统VO")
public class SysSystemVO {

    @Schema(description = "子系统ID")
    private Long id;

    @Schema(description = "子系统编码")
    private String systemCode;

    @Schema(description = "子系统名称")
    private String systemName;

    @Schema(description = "子系统描述")
    private String description;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态：0禁用，1正常")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
