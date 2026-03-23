package com.andy.entity.system;

import com.andy.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_subsystem")
@Schema(description = "子系统")
public class SysSubsystem extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
}
