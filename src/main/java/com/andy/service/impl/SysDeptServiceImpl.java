package com.andy.service.impl;

import com.andy.entity.system.SysDept;
import com.andy.mapper.SysDeptMapper;
import com.andy.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDept> getDeptsByUserId(Long userId) {
        return sysDeptMapper.selectDeptsByUserId(userId);
    }

    @Override
    public List<SysDept> getDeptTree() {
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDept::getStatus, 1);
        wrapper.eq(SysDept::getDeleted, 0);
        wrapper.orderByAsc(SysDept::getSort);
        List<SysDept> allDepts = this.list(wrapper);
        return buildTree(allDepts, 0L);
    }

    private List<SysDept> buildTree(List<SysDept> depts, Long parentId) {
        Map<Long, List<SysDept>> parentIdMap = depts.stream()
                .collect(Collectors.groupingBy(SysDept::getParentId));
        return buildTreeRecursive(parentIdMap, parentId);
    }

    private List<SysDept> buildTreeRecursive(Map<Long, List<SysDept>> parentIdMap, Long parentId) {
        List<SysDept> children = parentIdMap.getOrDefault(parentId, new ArrayList<>());
        for (SysDept child : children) {
            child.setChildren(buildTreeRecursive(parentIdMap, child.getId()));
        }
        return children;
    }
}
