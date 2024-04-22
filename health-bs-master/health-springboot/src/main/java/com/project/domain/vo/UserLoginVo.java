package com.project.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2023/12/4 9:43
 */
@Data
@ApiModel(description = "用户登录返回参数")
public class UserLoginVo {

    @ApiModelProperty("用户信息")
    private UserVo user;

    @ApiModelProperty("token")
    private String token;


}
