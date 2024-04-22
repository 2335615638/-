package com.project.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
*/
@Data
@ApiModel(description = "医生诊断建议列表展示出参")
public class DoctorSuggestPageVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("诊断人id")
    private Integer doctorId;

    @ApiModelProperty("诊断人医生姓名")
    private String doctorName;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("分析建议")
    private String diagnosisContent;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

}
