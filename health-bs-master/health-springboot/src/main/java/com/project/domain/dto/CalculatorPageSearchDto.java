package com.project.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
*/
@Data
@ApiModel(description = "体脂率（bfr）计算器分页搜索入参")
public class CalculatorPageSearchDto {

    @ApiModelProperty("搜索词")
    private String search;

}
