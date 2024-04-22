package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.domain.dto.CalculatorPageSearchDto;
import com.project.domain.vo.CalculatorPageVo;
import com.project.entity.Calculator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Author: zhoujinchuan
* @Description:  (Mapper)
*/
@Mapper
public interface CalculatorMapper extends BaseMapper<Calculator> {

    /**
     * 体脂记录分页列表
     * @param page
     * @param dto
     * @return
     */
    List<CalculatorPageVo> pageList(@Param("page") Page<Calculator> page,
                                    @Param("dto") CalculatorPageSearchDto dto);


}