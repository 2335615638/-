package com.project.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
*/
@Data
@ApiModel(description = "用户咨询医生记录表列表展示出参")
public class AskPageVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("咨询内容")
    private String consultationContent;

    @ApiModelProperty("医生id")
    private Integer doctorId;

    @ApiModelProperty("医生")
    private String doctorName;

    @ApiModelProperty("回复内容")
    private String replyContent;

    @ApiModelProperty("创建时间(咨询时间)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("回复时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime replayTime;

}
