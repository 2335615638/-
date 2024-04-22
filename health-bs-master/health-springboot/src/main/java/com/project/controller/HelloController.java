package com.project.controller;

import com.project.config.Auth;
import com.project.enums.UserRoleEnum;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2024/2/26 9:01
 */
@RestController
@RequestMapping
@Api(tags = "心跳")
public class HelloController {


    @GetMapping("isOk")
    public String isOk(){
        return "ok";
    }

    @GetMapping("isTeacherOk")
    @Auth({UserRoleEnum.DOCTOR})
    public String isTeacherOk(){
        return "ok";
    }

    @GetMapping("isAdminOk")
    @Auth({UserRoleEnum.ADMIN})
    public String isAdminOk(){
        return "ok";
    }

    @GetMapping("isStudentOk")
    @Auth({UserRoleEnum.USER})
    public String isStudentOk(){
        return "ok";
    }

}
