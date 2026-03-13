package com.andy.mapper;

import com.andy.entity.system.SysUserDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Delete;

@Mapper
public interface SysUserDeptMapper extends BaseMapper<SysUserDept> {

    @Delete("DELETE FROM sys_user_dept WHERE user_id = #{userId}")
    int deleteByUserId(@Param("userId") Long userId);

    @Delete("DELETE FROM sys_user_dept WHERE dept_id = #{deptId}")
    int deleteByDeptId(@Param("deptId") Long deptId);
}
