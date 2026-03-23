package com.andy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_subsystem")
public class SysSubsystem extends BaseEntity {

    private String subsystemName;

    private String subsystemCode;

    private String description;

    private Integer sort;

    private Integer status;
}
