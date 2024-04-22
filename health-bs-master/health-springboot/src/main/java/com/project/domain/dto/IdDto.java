package com.project.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2024/2/29 8:40
 */
@Data
@ApiModel(description = "id参数")
public class IdDto {

    @NotNull(message = "id不能为空")
    private Integer id;

}
