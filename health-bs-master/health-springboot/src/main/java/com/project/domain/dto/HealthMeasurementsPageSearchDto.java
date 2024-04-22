package com.project.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
*/
@Data
@ApiModel(description = "健康测量记录表分页搜索入参")
public class HealthMeasurementsPageSearchDto {

    @ApiModelProperty("搜索词")
    private String search;

    @ApiModelProperty("用户id")
    private Integer userId;

}
