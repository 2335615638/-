package com.project.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* @Author: zhoujinchuan
* @Description:  (医生诊断建议)
*/
@Data
@TableName("sys_doctor_suggest")
@ApiModel("医生诊断建议")
public class DoctorSuggest implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("诊断人id")
    private Integer doctorId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("分析建议")
    private String diagnosisContent;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public DoctorSuggest() {}

}