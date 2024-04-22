package com.project.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2024/3/6 14:53
 */
@Data
@ApiModel(description = "体脂计算器结果对象")
public class CalculatorResultVo {

    @ApiModelProperty("体脂率")
    private BigDecimal bodyFatPercent;

    @ApiModelProperty("信息")
    private String msg;

}
