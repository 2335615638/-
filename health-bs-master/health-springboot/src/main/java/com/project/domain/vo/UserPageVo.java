package com.project.domain.vo;

import com.project.enums.UserRoleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2023/12/6 16:46
 */
@Data
@ApiModel(description = "用户分页列表展示对象")
public class UserPageVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("性别（1-男，0-女）")
    private String sex;

    @ApiModelProperty("头像")
    private String avatarUrl;

    @ApiModelProperty("是否启用（1-启用，0-未启用）")
    private String status;

    @ApiModelProperty("权限")
    private UserRoleEnum role;

}
