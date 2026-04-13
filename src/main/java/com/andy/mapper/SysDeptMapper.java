package com.andy.mapper;

import com.andy.entity.system.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    @Select("SELECT d.* FROM sys_dept d " +
            "INNER JOIN sys_user_dept ud ON d.id = ud.dept_id " +
            "WHERE ud.user_id = #{userId} AND d.status = 1 AND d.deleted = 0")
    List<SysDept> selectDeptsByUserId(@Param("userId") Long userId);
}
