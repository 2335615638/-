package com.project.domain.dto;

import com.project.enums.UserRoleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2023/12/12 9:32
 */
@Data
@ApiModel(description = "新增修改用户入参对象")
public class BackUserAddOrUpdateDto {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty("昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @ApiModelProperty("性别（1-男，0-女）")
    @NotBlank(message = "性别不能为空")
    private String sex;

    @ApiModelProperty("头像")
    @NotBlank(message = "头像不能为空")
    private String avatarUrl;

    @ApiModelProperty("角色")
    private UserRoleEnum role;

}
