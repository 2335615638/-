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
@ApiModel(description = "用户备忘录添加和修改入参")
public class NoteAddOrUpdateDto {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty("内容")
    @NotBlank(message = "内容不能为空")
    private String noteContent;

    @ApiModelProperty("图片")
    @NotBlank(message = "图片不能为空")
    private String imgUrl;

}
