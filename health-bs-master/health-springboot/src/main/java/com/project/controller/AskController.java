package com.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.project.config.Auth;
import com.project.config.Result;
import com.project.domain.dto.AskPageSearchDto;
import com.project.domain.dto.ConsultingServiceDto;
import com.project.domain.dto.DoctorReplyDto;
import com.project.domain.dto.IdDto;
import com.project.domain.vo.AskPageVo;
import com.project.enums.UserRoleEnum;
import com.project.service.IAskService;
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
@RequestMapping("/ask")
@Api(tags = "用户咨询医生记录表")
public class AskController {

    @Autowired
    private IAskService askService;

    @GetMapping("userPageList")
    @ApiOperation("用户咨询分页列表")
    @Auth({UserRoleEnum.USER})
    public Result<IPage<AskPageVo>> userPageList(AskPageSearchDto searchDto,
                                                        @RequestParam(required = false, defaultValue = "0") int pageNum,
                                                        @RequestParam(required = false, defaultValue = "10") int pageSize){
        IPage<AskPageVo> page = askService.userPageList(pageNum,pageSize,searchDto);
        return Result.success(page);
    }

    @GetMapping("doctorPageList")
    @ApiOperation("医生咨询分页列表")
    @Auth({UserRoleEnum.DOCTOR})
    public Result<IPage<AskPageVo>> doctorPageList(AskPageSearchDto searchDto,@RequestParam(required = false, defaultValue = "0") int pageNum,
                                                        @RequestParam(required = false, defaultValue = "10") int pageSize){
        IPage<AskPageVo> page = askService.doctorPageList(pageNum,pageSize,searchDto);
        return Result.success(page);
    }

    @ApiOperation("用户咨询")
    @PostMapping("consultingService")
    @Auth({UserRoleEnum.USER})
    public Result consultingService(@RequestBody @Valid ConsultingServiceDto dto){
        askService.consultingService(dto);
        return Result.success();
    }


    @ApiOperation("医生回复")
    @PostMapping("doctorReply")
    @Auth({UserRoleEnum.DOCTOR})
    public Result doctorReply(@RequestBody @Valid DoctorReplyDto dto){
        askService.doctorReply(dto);
        return Result.success();
    }

    @PostMapping("remove")
    @ApiOperation("删除")
    @Auth({UserRoleEnum.USER})
    public Result remove(@RequestBody @Valid IdDto dto){
        askService.remove(dto.getId());
        return Result.success();
    }

}
