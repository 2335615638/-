package com.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.project.config.Auth;
import com.project.config.Result;
import com.project.domain.dto.UserRegisterDto;
import com.project.domain.vo.UserPageVo;
import com.project.enums.UserRoleEnum;
import com.project.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2024/2/28 10:11
 */
@RestController
@RequestMapping("/user/base")
@Api(tags = "用户端-基础接口")
public class UserUserController {

    @Autowired
    private IUserService userService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody @Valid UserRegisterDto dto){
        userService.userRegister(dto);
        return Result.success();
    }

    @ApiOperation("医生端用户列表")
    @GetMapping("userPageList")
    @Auth({UserRoleEnum.DOCTOR})
    public Result<IPage<UserPageVo>> userPageList(@RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam(defaultValue = "") String search) {
        IPage<UserPageVo> page = userService.userPageList(pageNum, pageSize, search);
        return Result.success(page);
    }

    @ApiOperation("用户端医生列表")
    @GetMapping("doctorPageList")
    @Auth({UserRoleEnum.USER})
    public Result<IPage<UserPageVo>> doctorPageList(@RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam(defaultValue = "") String search) {
        IPage<UserPageVo> page = userService.doctorPageList(pageNum, pageSize, search);
        return Result.success(page);
    }

}
