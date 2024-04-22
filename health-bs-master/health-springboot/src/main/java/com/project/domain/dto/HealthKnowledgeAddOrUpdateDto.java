package com.project.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
*/
@Data
@ApiModel(description = "健康知识添加和修改入参")
public class HealthKnowledgeAddOrUpdateDto {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("知识标题")
    @NotBlank(message = "知识标题不能为空")
    private String title;

    @ApiModelProperty("知识类型")
    @NotBlank(message = "知识类型不能为空")
    private String type;

    @ApiModelProperty("知识内容")
    @NotBlank(message = "知识内容不能为空")
    private String content;

}
