package com.project.controller;

import com.project.config.Auth;
import com.project.config.Result;
import com.project.domain.vo.StatisticVo;
import com.project.enums.UserRoleEnum;
import com.project.service.IHealthMeasurementsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2024/3/7 15:29
 */
@RestController
@RequestMapping("/statistics")
@Api(tags = "统计相关")
public class StatisticController {

    @Autowired
    private IHealthMeasurementsService healthMeasurementsService;

    @ApiOperation("用户统计")
    @GetMapping
    @Auth({UserRoleEnum.DOCTOR,UserRoleEnum.USER})
    public Result<StatisticVo> statistics(@RequestParam("userId")Integer userId){
        StatisticVo statisticVo = healthMeasurementsService.statistics(userId);
        return Result.success(statisticVo);
    }



}
