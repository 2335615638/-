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
* @Description:  (健康测量记录表)
*/
@Data
@TableName("sys_health_measurements")
@ApiModel("健康测量记录表")
public class HealthMeasurements implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
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

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;

    public HealthMeasurements() {}

}