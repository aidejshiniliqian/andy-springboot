package com.andy.mapper;

import com.andy.entity.system.SysSubsystem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysSubsystemMapper extends BaseMapper<SysSubsystem> {

    @Select("SELECT code FROM sys_subsystem WHERE status = 1 AND deleted = 0")
    List<String> selectAllCodes();

    @Select("SELECT COUNT(*) FROM sys_subsystem WHERE code = #{code} AND status = 1 AND deleted = 0")
    int countByCode(@Param("code") String code);
}
