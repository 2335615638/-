package com.project.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2023/12/5 11:16
 */
@Data
@ApiModel(description = "修改用户信息入参")
public class UserUpdateDto {

    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty("昵称")
    private String nickName;

    @NotBlank(message = "性别不能为空")
    @ApiModelProperty("性别（1-男，0-女）")
    private String sex;

    @NotBlank(message = "头像不能为空")
    @ApiModelProperty("头像")
    private String avatarUrl;

    @ApiModelProperty("兴趣爱好")
    private String hobby;

}
