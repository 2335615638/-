package com.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.project.config.Auth;
import com.project.config.Result;
import com.project.domain.dto.DoctorSuggestAddDto;
import com.project.domain.dto.IdDto;
import com.project.domain.vo.DoctorSuggestPageVo;
import com.project.enums.UserRoleEnum;
import com.project.service.IDoctorSuggestService;
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
@RequestMapping("/doctorSuggest")
@Api(tags = "医生诊断建议")
public class DoctorSuggestController {

    @Autowired
    private IDoctorSuggestService doctorSuggestService;

    @GetMapping("userPageList")
    @ApiOperation("用户查看自己的诊断意见列表")
    @Auth({UserRoleEnum.USER})
    public Result<IPage<DoctorSuggestPageVo>> userPageList(
                                                        @RequestParam(required = false, defaultValue = "0") int pageNum,
                                                        @RequestParam(required = false, defaultValue = "10") int pageSize){
        IPage<DoctorSuggestPageVo> page = doctorSuggestService.userPageList(pageNum,pageSize);
        return Result.success(page);
    }

    @PostMapping("saveSuggest")
    @ApiOperation("医生保存建议")
    @Auth({UserRoleEnum.DOCTOR})
    public Result saveOrUpdate(@RequestBody @Valid DoctorSuggestAddDto dto){
        doctorSuggestService.saveSuggest(dto);
        return Result.success();
    }

    @PostMapping("remove")
    @ApiOperation("用户删除诊断建议")
    @Auth({UserRoleEnum.USER})
    public Result remove(@RequestBody @Valid IdDto dto){
        doctorSuggestService.remove(dto.getId());
        return Result.success();
    }

}
