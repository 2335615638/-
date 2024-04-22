package com.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.dto.CalculatorPageSearchDto;
import com.project.domain.dto.CalculatorSearchDto;
import com.project.domain.vo.CalculatorPageVo;
import com.project.entity.Calculator;

import java.math.BigDecimal;

/**
* @Description:  (服务层)
*/
public interface ICalculatorService extends IService<Calculator> {

    /**
     * 体脂计算器
     * @param dto
     * @return
     */
    BigDecimal search(CalculatorSearchDto dto);

    /**
     * 分页列表
     * @param pageNum
     * @param pageSize
     * @param searchDto
     * @return
     */
    IPage<CalculatorPageVo> pageList(int pageNum, int pageSize, CalculatorPageSearchDto searchDto);

}