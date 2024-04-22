package com.project.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
*/
@Data
@ApiModel(description = "用户咨询医生入参")
public class ConsultingServiceDto {

    @ApiModelProperty("咨询内容")
    @NotBlank(message = "咨询内容不能为空")
    private String consultationContent;

    @ApiModelProperty("医生id")
    @NotNull(message = "医生id不能为空")
    private Integer doctorId;

}
