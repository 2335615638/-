package com.project.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
*/
@Data
@ApiModel(description = "体脂率（bfr）计算器列表展示出参")
public class CalculatorPageVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("体重kg")
    private Integer weight;

    @ApiModelProperty("身高cm")
    private Integer height;

    @ApiModelProperty("年龄（周岁）")
    private Integer age;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    private String createName;

    @ApiModelProperty("体脂率")
    private BigDecimal bodyFatPercent;

}
