package com.project.controller;

import com.project.config.Auth;
import com.project.config.Result;
import com.project.domain.dto.PasswordDto;
import com.project.domain.dto.UserLoginDto;
import com.project.domain.dto.UserUpdateDto;
import com.project.domain.vo.UserLoginVo;
import com.project.enums.UserRoleEnum;
import com.project.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2024/2/27 11:03
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关信息（三个角色通用）")
public class BaseUserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result<UserLoginVo> login(@Valid @RequestBody UserLoginDto dto) {
        UserLoginVo vo = userService.login(dto);
        return Result.success(vo);
    }

    @ApiOperation(value = "注销")
    @PostMapping("/logout")
    @Auth({UserRoleEnum.ADMIN,UserRoleEnum.USER, UserRoleEnum.DOCTOR})
    public Result logout() {
        userService.logout();
        return Result.success();
    }

    @ApiOperation("更改用户信息")
    @PostMapping("updateInfo")
    @Auth({UserRoleEnum.ADMIN,UserRoleEnum.USER, UserRoleEnum.DOCTOR})
    public Result updateInfo(@RequestBody @Valid UserUpdateDto dto) {
        userService.updateInfo(dto);
        return Result.success();
    }

    @ApiOperation("更改密码")
    @PostMapping("updatePassword")
    @Auth({UserRoleEnum.ADMIN,UserRoleEnum.USER, UserRoleEnum.DOCTOR})
    public Result updatePassword(@RequestBody @Valid PasswordDto dto) {
        userService.updatePassword(dto);
        return Result.success();
    }


}
