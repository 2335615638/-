package com.project.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
*/
@Data
@ApiModel(description = "健康知识列表展示出参")
public class HealthKnowledgePageVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("知识标题")
    private String title;

    @ApiModelProperty("知识类型")
    private String type;

    @ApiModelProperty("知识类型")
    private String typeStr;

    @ApiModelProperty("知识内容")
    private String content;

}
