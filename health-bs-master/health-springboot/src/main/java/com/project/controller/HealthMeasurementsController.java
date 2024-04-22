package com.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.project.config.Auth;
import com.project.config.Result;
import com.project.domain.dto.HealthMeasurementsAddOrUpdateDto;
import com.project.domain.dto.HealthMeasurementsPageSearchDto;
import com.project.domain.dto.IdDto;
import com.project.domain.vo.HealthMeasurementsPageVo;
import com.project.enums.UserRoleEnum;
import com.project.service.IHealthMeasurementsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
* @CreateTime: 2023/12/6 11:17
*/
@RestController
@RequestMapping("/healthMeasurements")
@Api(tags = "健康测量记录")
public class HealthMeasurementsController {

    @Autowired
    private IHealthMeasurementsService healthMeasurementsService;

    @GetMapping("userPageList")
    @ApiOperation("用户健康记录分页列表")
    @Auth({UserRoleEnum.USER,UserRoleEnum.DOCTOR})
    public Result<IPage<HealthMeasurementsPageVo>> userPageList(HealthMeasurementsPageSearchDto searchDto,
                                                        @RequestParam(required = false, defaultValue = "0") int pageNum,
                                                        @RequestParam(required = false, defaultValue = "10") int pageSize){
        IPage<HealthMeasurementsPageVo> page = healthMeasurementsService.userPageList(pageNum,pageSize,searchDto);
        return Result.success(page);
    }

    @PostMapping("userSaveOrUpdate")
    @ApiOperation("用户添加或修改健康记录")
    @Auth({UserRoleEnum.USER})
    public Result userSaveOrUpdate(@RequestBody @Valid HealthMeasurementsAddOrUpdateDto dto){
        healthMeasurementsService.userSaveOrUpdate(dto);
        return Result.success();
    }

    @PostMapping("userRemove")
    @ApiOperation("用户删除健康记录")
    public Result userRemove(@RequestBody @Valid IdDto dto){
        healthMeasurementsService.userRemove(dto.getId());
        return Result.success();
    }

}
