package com.project.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2024/3/7 14:51
 */
@Data
@ApiModel(description = "医生回复dto")
public class DoctorReplyDto {

    @NotNull(message = "咨询记录id不能为空")
    private Integer id;

    @ApiModelProperty("回复内容")
    @NotBlank(message = "回复内容不能为空")
    private String replyContent;

}
