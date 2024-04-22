package com.project.domain.dto;

import com.project.enums.UserRoleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2024/3/1 9:21
 */
@Data
@ApiModel(description = "修改角色入参")
public class UpdateRoleDto {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Integer id;

    @NotEmpty(message = "角色不能为空")
    @ApiModelProperty("角色 ，管理员，商家，用户，")
    private List<UserRoleEnum> roles;

}
