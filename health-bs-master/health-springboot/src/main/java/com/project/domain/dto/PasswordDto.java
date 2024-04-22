package com.project.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2023/12/5 11:46
 */
@Data
@ApiModel(description = "修改密码DTO")
public class PasswordDto {

    @NotBlank(message = "旧密码不能为空")
    @ApiModelProperty("旧密码")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty("新密码")
    private String newPassword;

}
