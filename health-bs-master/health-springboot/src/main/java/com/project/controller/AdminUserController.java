package com.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.project.config.Auth;
import com.project.config.Result;
import com.project.domain.dto.BackUserAddOrUpdateDto;
import com.project.domain.dto.IdDto;
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
 * @CreateTime: 2023/12/4 9:38
 */
@RestController//注解表示这是一个 RESTful 风格的控制器类，用于处理 HTTP 请求，并返回 JSON 格式的数据
@RequestMapping("/admin/user")//注解指定了控制器处理请求的基础路径，即 "/admin/user"，后续的请求路径会在此基础路径之后。
@Api(tags = "管理端-用户信息管理")// Swagger API 文档注解，用于指定接口的分组名称，方便接口的分类和查找。
public class AdminUserController {

    @Autowired
    private IUserService userService;

    @ApiOperation("用户信息管理-用户分页列表")
    @GetMapping("pageList")//处理 GET 请求，路径为 "/admin/user/pageList"，用于获取用户分页列表。
    @Auth({UserRoleEnum.ADMIN})//权限验证表示只有管理员角色才能访问该接口。
    public Result<IPage<UserPageVo>> pageList(@RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam(defaultValue = "") String search) {
        IPage<UserPageVo> page = userService.pageList(pageNum, pageSize, search);
        return Result.success(page);//将获取到的用户分页列表封装成 Result 对象，并返回给调用方。
    }

    @ApiOperation("用户信息管理-用户删除用户")
    @PostMapping("removeUser")
    @Auth({UserRoleEnum.ADMIN})
    public Result removeUser(@RequestBody @Valid IdDto dto) {
        userService.removeUser(dto.getId());
        return Result.success();
    }

    @ApiOperation("用户信息管理-添加修改用户")
    @Auth({UserRoleEnum.ADMIN})
    @PostMapping("backSaveOrUpdate")
    public Result backSaveOrUpdate(@RequestBody @Valid BackUserAddOrUpdateDto dto) {
        userService.backSaveOrUpdate(dto);
        return Result.success();
    }

    @ApiOperation("用户信息管理-修改用户状态")
    @Auth({UserRoleEnum.ADMIN})
    @PostMapping("changeStatus")
    public Result changeStatus(@RequestBody @Valid IdDto dto) {
        userService.changeStatus(dto.getId());
        return Result.success();
    }


}
