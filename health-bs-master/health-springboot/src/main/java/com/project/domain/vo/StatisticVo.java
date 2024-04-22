package com.project.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2024/3/7 15:32
 */
@Data
public class StatisticVo {

    @ApiModelProperty("血压水平变化")
    private Map<String, List<StatisticEntityVo>> bloodPressureData;

    @ApiModelProperty("血糖水平变化")
    private List<StatisticEntityVo> glucoseData;

    @ApiModelProperty("体温变化")
    private List<StatisticEntityVo> temperatureData;

    @ApiModelProperty("脉搏率变化")
    private List<StatisticEntityVo> pulseData;



}
