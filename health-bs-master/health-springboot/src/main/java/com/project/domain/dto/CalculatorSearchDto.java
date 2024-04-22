package com.project.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2024/3/6 14:50
 */
@Data
@ApiModel(description = "计算器入参对象")
public class CalculatorSearchDto {

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("体重kg")
    private Integer weight;

    @ApiModelProperty("身高cm")
    private Integer height;

    @ApiModelProperty("年龄（周岁）")
    private Integer age;


}
