package com.project.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
*/
@Data
@ApiModel(description = "健康测量记录表添加和修改入参")
public class HealthMeasurementsAddOrUpdateDto {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("收缩压")
    private Integer systolicPressure;

    @ApiModelProperty("舒张压")
    private Integer diastolicPressure;

    @ApiModelProperty("血糖水平（精确到小数点后两位）")
    private BigDecimal bloodSugarLevel;

    @ApiModelProperty("体温（精确到小数点后两位）")
    private BigDecimal temperature;

    @ApiModelProperty("脉搏率")
    private Integer pulseRate;

}
