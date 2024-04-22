package com.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.project.config.Auth;
import com.project.config.Result;
import com.project.domain.dto.CalculatorPageSearchDto;
import com.project.domain.dto.CalculatorSearchDto;
import com.project.domain.vo.CalculatorPageVo;
import com.project.enums.UserRoleEnum;
import com.project.service.ICalculatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
* @CreateTime: 2023/12/6 11:17
*/
@RestController
@RequestMapping("/calculator")
@Api(tags = "体脂率（bfr）计算器")
public class CalculatorController {

    @Autowired
    private ICalculatorService calculatorService;

    @PostMapping("search")
    @ApiOperation("体脂计算器")
    @Auth({UserRoleEnum.USER})
    public Result<BigDecimal> search(@RequestBody @Valid CalculatorSearchDto dto){
        BigDecimal vo = calculatorService.search(dto);
        return Result.success(vo);
    }

    @GetMapping("pageList")
    @ApiOperation("体脂记录分页列表")
    @Auth({UserRoleEnum.ADMIN})
    public Result<IPage<CalculatorPageVo>> pageList(CalculatorPageSearchDto searchDto,
                                                    @RequestParam(required = false, defaultValue = "0") int pageNum,
                                                    @RequestParam(required = false, defaultValue = "10") int pageSize){
        IPage<CalculatorPageVo> page = calculatorService.pageList(pageNum,pageSize,searchDto);
        return Result.success(page);
    }



}
