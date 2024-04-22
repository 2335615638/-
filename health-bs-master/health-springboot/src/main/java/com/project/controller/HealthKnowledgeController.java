package com.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.project.config.Auth;
import com.project.config.Result;
import com.project.domain.dto.HealthKnowledgeAddOrUpdateDto;
import com.project.domain.dto.IdDto;
import com.project.domain.vo.HealthKnowledgePageVo;
import com.project.enums.UserRoleEnum;
import com.project.service.IHealthKnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
* @CreateTime: 2023/12/6 11:17
*/
@RestController
@RequestMapping("/healthKnowledge")
@Api(tags = "健康知识")
public class HealthKnowledgeController {

    @Autowired
    private IHealthKnowledgeService healthKnowledgeService;

    @GetMapping("pageList")
    @ApiOperation("知识分页列表")
    @Auth({UserRoleEnum.ADMIN})
    public Result<IPage<HealthKnowledgePageVo>> pageList(@RequestParam("type") String type,
                                                        @RequestParam(required = false, defaultValue = "0") int pageNum,
                                                        @RequestParam(required = false, defaultValue = "10") int pageSize){
        IPage<HealthKnowledgePageVo> page = healthKnowledgeService.pageList(pageNum,pageSize,type);
        return Result.success(page);
    }

    @PostMapping("saveOrUpdate")
    @ApiOperation("添加或修改知识")
    @Auth({UserRoleEnum.ADMIN})
    public Result saveOrUpdate(@RequestBody @Valid HealthKnowledgeAddOrUpdateDto dto){
        healthKnowledgeService.saveOrUpdate(dto);
        return Result.success();
    }

    @PostMapping("remove")
    @ApiOperation("删除知识")
    @Auth({UserRoleEnum.ADMIN})
    public Result remove(@RequestBody @Valid IdDto dto){
        healthKnowledgeService.remove(dto.getId());
        return Result.success();
    }

    @GetMapping("getListByType")
    @ApiOperation("根据类型获取知识")
    @Auth({UserRoleEnum.USER})
    public Result<List<HealthKnowledgePageVo>> getListByType(@RequestParam("type") String type){
        List<HealthKnowledgePageVo> resultList = healthKnowledgeService.getListByType(type);
        return Result.success(resultList);
    }

}
