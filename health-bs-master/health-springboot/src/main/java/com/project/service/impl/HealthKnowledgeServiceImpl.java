package com.project.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.dto.HealthKnowledgeAddOrUpdateDto;
import com.project.domain.vo.HealthKnowledgePageVo;
import com.project.entity.HealthKnowledge;
import com.project.enums.HealthKnowledgeTypeEnum;
import com.project.mapper.HealthKnowledgeMapper;
import com.project.service.IHealthKnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
* @Author: zhoujinchuan
* @Description:  ()
*/
@Slf4j
@Service
public class HealthKnowledgeServiceImpl extends ServiceImpl<HealthKnowledgeMapper, HealthKnowledge> implements IHealthKnowledgeService {


    @Override
    public IPage<HealthKnowledgePageVo> pageList(int pageNum, int pageSize, String type) {
        Page<HealthKnowledge> page = new Page<>(pageNum, pageSize);
        IPage<HealthKnowledge> iPage = lambdaQuery()
                .eq(StringUtils.isNotBlank(type),HealthKnowledge::getType,type)
                .orderByDesc(HealthKnowledge::getCreateTime)
                .page(page);
        IPage<HealthKnowledgePageVo> voIPage = new Page<>();
        BeanUtils.copyProperties(iPage, voIPage,"records");
        List<HealthKnowledge> records = iPage.getRecords();
        List<HealthKnowledgePageVo> resultList = records.stream().map(e -> {
            HealthKnowledgePageVo vo = new HealthKnowledgePageVo();
            BeanUtils.copyProperties(e, vo);
            vo.setTypeStr(HealthKnowledgeTypeEnum.getLabelByCode(vo.getType()));
            return vo;
        }).collect(Collectors.toList());
        voIPage.setRecords(resultList);
        return voIPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(HealthKnowledgeAddOrUpdateDto dto) {
        HealthKnowledge entity;
        if(dto.getId() != null) {
            entity = getById(dto.getId());
            Assert.isTrue(entity != null, "信息不存在");
        }else{
            entity = new HealthKnowledge();
        }
        BeanUtils.copyProperties(dto, entity);
        saveOrUpdate(entity);
    }

    @Override
    public void remove(Integer id) {
        HealthKnowledge entity = getById(id);
        Assert.isTrue(entity != null, "信息不存在");
        removeById(id);
    }

    @Override
    public List<HealthKnowledgePageVo> getListByType(String type) {
        List<HealthKnowledge> list = lambdaQuery().eq(HealthKnowledge::getType, type).orderByDesc(HealthKnowledge::getCreateTime).list();
        if (CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        return list.stream().map(e -> {
            HealthKnowledgePageVo target = new HealthKnowledgePageVo();
            BeanUtils.copyProperties(e, target);
            return target;
        }).collect(Collectors.toList());
    }

}