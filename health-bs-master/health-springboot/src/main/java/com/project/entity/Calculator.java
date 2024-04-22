package com.project.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* @Author: zhoujinchuan
* @Description:  (体脂率（bfr）计算器)
*/
@Data
@TableName("sys_calculator")
@ApiModel("体脂率（bfr）计算器")
public class Calculator implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
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
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;

    @ApiModelProperty("体脂率")
    private BigDecimal bodyFatPercent;

    public Calculator() {}

}