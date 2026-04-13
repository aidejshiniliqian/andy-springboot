package com.andy.entity.system;

import com.andy.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
@Schema(description = "系统部门")
public class SysDept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "部门编码")
    private String deptCode;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "父级ID")
    private Long parentId;

    @Schema(description = "层级路径")
    private String path;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态(0:禁用,1:启用)")
    private Integer status;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "子部门列表")
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private java.util.List<SysDept> children;
}
