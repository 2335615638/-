package com.project.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.dto.CalculatorPageSearchDto;
import com.project.domain.dto.CalculatorSearchDto;
import com.project.domain.vo.CalculatorPageVo;
import com.project.entity.Calculator;
import com.project.mapper.CalculatorMapper;
import com.project.service.ICalculatorService;
import com.project.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


/**
* @Author: zhoujinchuan
* @Description:  ()
*/
@Slf4j
@Service
public class CalculatorServiceImpl extends ServiceImpl<CalculatorMapper, Calculator> implements ICalculatorService {


    @Override
    public BigDecimal search(CalculatorSearchDto dto) {
        Integer userId = UserContext.getUserId();
        // 计算BMI
        BigDecimal heightInMeters = BigDecimal.valueOf(dto.getHeight()).divide(BigDecimal.valueOf(100));
        BigDecimal bmi = BigDecimal.valueOf(dto.getWeight()).divide(heightInMeters.pow(2), 2, BigDecimal.ROUND_HALF_UP);

        // 计算体脂率
        BigDecimal bodyFatPercentage = BigDecimal.valueOf(1.2)
                .multiply(bmi)
                .add(BigDecimal.valueOf(0.23).multiply(BigDecimal.valueOf(dto.getAge())))
                .subtract(BigDecimal.valueOf(5.4))
                .subtract(BigDecimal.valueOf(10.8).multiply("男".equalsIgnoreCase(dto.getSex()) ? BigDecimal.ONE : BigDecimal.ZERO));

        // 结果保留两位小数
        bodyFatPercentage = bodyFatPercentage.setScale(2, BigDecimal.ROUND_HALF_UP);
        Calculator calculator = new Calculator();
        calculator.setAge(dto.getAge());
        calculator.setBodyFatPercent(bodyFatPercentage);
        calculator.setHeight(dto.getHeight());
        calculator.setWeight(dto.getWeight());
        calculator.setSex(dto.getSex());
        save(calculator);
        return bodyFatPercentage;
    }

    @Override
    public IPage<CalculatorPageVo> pageList(int pageNum, int pageSize, CalculatorPageSearchDto searchDto) {
        Page<Calculator> page = new Page<>(pageNum, pageSize);
        List<CalculatorPageVo> resultList = baseMapper.pageList(page,searchDto);
        IPage<CalculatorPageVo> voIPage = new Page<>();
        BeanUtils.copyProperties(page, voIPage,"records");
        voIPage.setRecords(resultList);
        return voIPage;
    }

}