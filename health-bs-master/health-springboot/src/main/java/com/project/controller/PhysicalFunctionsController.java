package com.project.controller;

import cn.hutool.core.lang.Assert;
import com.project.config.Auth;
import com.project.config.Result;
import com.project.entity.PhysicalFunctions;
import com.project.entity.User;
import com.project.enums.UserRoleEnum;
import com.project.service.IPhysicalFunctionsService;
import com.project.service.IUserService;
import com.project.util.UserContext;
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
@RequestMapping("/physicalFunctions")
@Api(tags = "用户身体机能")
public class PhysicalFunctionsController {

    @Autowired
    private IPhysicalFunctionsService physicalFunctionsService;

    @Autowired
    private IUserService userService;

    @GetMapping("mine")
    @ApiOperation("个人信息")
    @Auth({UserRoleEnum.USER})
    public Result<PhysicalFunctions> mine(){
        PhysicalFunctions detail = physicalFunctionsService.lambdaQuery().eq(PhysicalFunctions::getUserId, UserContext.getUserId()).one();
        return Result.success(detail);
    }

    @PostMapping("maintenance")
    @ApiOperation("维护身体机能")
    @Auth({UserRoleEnum.USER})
    public Result maintenance(@RequestBody @Valid PhysicalFunctions entity){
        physicalFunctionsService.maintenance(entity);
        return Result.success();
    }

    @GetMapping("detail")
    @ApiOperation("用户身体机能信息")
    @Auth({UserRoleEnum.DOCTOR})
    public Result<PhysicalFunctions> detail(@RequestParam("userId")Integer userId){
        PhysicalFunctions physicalFunctions = physicalFunctionsService.lambdaQuery().eq(PhysicalFunctions::getUserId, userId).one();
        if(physicalFunctions == null){
            physicalFunctions =  new PhysicalFunctions();
        }
        User user = userService.getById(userId);
        Assert.notNull(user,"未找到用户信息");
        physicalFunctions.setUserName(user.getUserName());
        physicalFunctions.setNickName(user.getNickName());
        return Result.success(physicalFunctions);
    }
}
