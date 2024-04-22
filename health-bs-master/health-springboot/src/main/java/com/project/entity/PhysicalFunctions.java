package com.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
* @Author: zhoujinchuan
* @Description:  (用户身体机能)
*/
@Data
@TableName("sys_physical_functions")
@ApiModel("用户身体机能")
public class PhysicalFunctions implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("账号（身份证号）")
    @TableField(exist = false)
    private String userName;

    @ApiModelProperty("用户姓名")
    @TableField(exist = false)
    private String nickName;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @ApiModelProperty("身高（精确到小数点后两位）")
    private BigDecimal height;

    @ApiModelProperty("体重（精确到小数点后两位）")
    private BigDecimal weight;

    @ApiModelProperty("肺活量")
    private Integer lungCapacity;

    @ApiModelProperty("立定跳远距离")
    private Integer standingLongJumpDistance;

    @ApiModelProperty("仰卧起坐次数")
    private Integer sitUpsCount;

    @ApiModelProperty("50米跑时间")
    private Integer sprintFiftymTime;

    @ApiModelProperty("1000米跑时间")
    private Integer sprintThousandmTime;

    @ApiModelProperty("坐位体前屈距离（精确到小数点后两位）")
    private BigDecimal sitAndReachDistance;

    public PhysicalFunctions() {}

}