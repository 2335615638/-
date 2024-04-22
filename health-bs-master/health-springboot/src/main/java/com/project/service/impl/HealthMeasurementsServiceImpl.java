package com.project.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.dto.HealthMeasurementsAddOrUpdateDto;
import com.project.domain.dto.HealthMeasurementsPageSearchDto;
import com.project.domain.vo.HealthMeasurementsPageVo;
import com.project.domain.vo.StatisticEntityVo;
import com.project.domain.vo.StatisticVo;
import com.project.entity.HealthMeasurements;
import com.project.entity.Role;
import com.project.enums.UserRoleEnum;
import com.project.mapper.HealthMeasurementsMapper;
import com.project.service.IHealthMeasurementsService;
import com.project.service.IRoleService;
import com.project.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
* @Author: zhoujinchuan
* @Description:  ()
*/
@Slf4j
@Service
public class HealthMeasurementsServiceImpl extends ServiceImpl<HealthMeasurementsMapper, HealthMeasurements> implements IHealthMeasurementsService {

    @Autowired
    private IRoleService roleService;

    @Override
    public IPage<HealthMeasurementsPageVo> userPageList(int pageNum, int pageSize, HealthMeasurementsPageSearchDto searchDto) {
        Integer userId = UserContext.getUserId();
        Role role = roleService.lambdaQuery().eq(Role::getUserId, userId).one();
        if (UserRoleEnum.DOCTOR.equals(role.getRoleStr())) {
            userId = searchDto.getUserId();
        }
        Page<HealthMeasurements> page = new Page<>(pageNum, pageSize);
        IPage<HealthMeasurements> iPage = lambdaQuery()
                .eq(HealthMeasurements::getCreateBy,userId)
                .orderByDesc(HealthMeasurements::getCreateTime)
                .page(page);
        IPage<HealthMeasurementsPageVo> voIPage = new Page<>();
        BeanUtils.copyProperties(iPage, voIPage,"records");
        List<HealthMeasurements> records = iPage.getRecords();
        List<HealthMeasurementsPageVo> resultList = records.stream().map(e -> {
            HealthMeasurementsPageVo vo = new HealthMeasurementsPageVo();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
        voIPage.setRecords(resultList);
        return voIPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userSaveOrUpdate(HealthMeasurementsAddOrUpdateDto dto) {
        HealthMeasurements entity;
        if(dto.getId() != null) {
            entity = getById(dto.getId());
            Assert.isTrue(entity != null, "信息不存在");
        }else{
            entity = new HealthMeasurements();
            Integer userId = UserContext.getUserId();
            LocalDateTime now = LocalDateTime.now();
            // 获取昨天23:59:59
            LocalDateTime yesterdayEnd = now.minusDays(1).withHour(23).withMinute(59).withSecond(59);
            // 获取明天的00:00:00
            LocalDateTime tomorrowStart = now.plusDays(1).withHour(0).withMinute(0).withSecond(0);
            Long count = lambdaQuery().eq(HealthMeasurements::getCreateBy, userId)
                    .gt(HealthMeasurements::getCreateTime, yesterdayEnd)
                    .lt(HealthMeasurements::getCreateTime, tomorrowStart).count();
            Assert.isTrue(count != null && count == 0,"一天只能维护一条记录");
        }
        BeanUtils.copyProperties(dto, entity);
        saveOrUpdate(entity);
    }

    @Override
    public void userRemove(Integer id) {

        HealthMeasurements entity = getById(id);
        Assert.isTrue(entity != null, "信息不存在");
        removeById(id);
    }

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

    @Override
    public StatisticVo statistics(Integer userId) {
        // 查询最新的7条
        List<HealthMeasurements> resultList = lambdaQuery()
                .eq(HealthMeasurements::getCreateBy, userId)
                .orderByDesc(HealthMeasurements::getCreateTime).list().stream().limit(7).collect(Collectors.toList());
        StatisticVo statisticVo = new StatisticVo();
        if (!CollectionUtils.isEmpty(resultList)){
            resultList.sort(Comparator.comparing(HealthMeasurements::getCreateTime));
            Map<String, List<StatisticEntityVo>> map = new HashMap<>();
            List<StatisticEntityVo> systolicDataList = new ArrayList<>();
            List<StatisticEntityVo> diastolicDataList = new ArrayList<>();
            List<StatisticEntityVo> glucoseDataList = new ArrayList<>();
            List<StatisticEntityVo> temperatureDataList = new ArrayList<>();
            List<StatisticEntityVo> pulseDataDataList = new ArrayList<>();
            for (HealthMeasurements healthMeasurements : resultList) {
                String monthDay = healthMeasurements.getCreateTime().format(formatter);
                systolicDataList.add(new StatisticEntityVo(monthDay,healthMeasurements.getSystolicPressure()));
                diastolicDataList.add(new StatisticEntityVo(monthDay,healthMeasurements.getDiastolicPressure()));
                glucoseDataList.add(new StatisticEntityVo(monthDay,healthMeasurements.getBloodSugarLevel()));
                temperatureDataList.add(new StatisticEntityVo(monthDay,healthMeasurements.getTemperature()));
                pulseDataDataList.add(new StatisticEntityVo(monthDay,healthMeasurements.getPulseRate()));
            }
            map.put("systolicData",systolicDataList);
            map.put("diastolicData",diastolicDataList);
            statisticVo.setBloodPressureData(map);
            statisticVo.setGlucoseData(glucoseDataList);
            statisticVo.setTemperatureData(temperatureDataList);
            statisticVo.setPulseData(pulseDataDataList);
        }
        return statisticVo;
    }

}
