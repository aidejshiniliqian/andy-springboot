package com.example.demo.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_system")
@Schema(description = "子系统实体")
public class SysSystem extends BaseEntity {

    @TableId(type = IdType.AUTO)
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
}
