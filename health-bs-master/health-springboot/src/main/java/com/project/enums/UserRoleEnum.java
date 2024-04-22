package com.project.enums;

import lombok.Getter;

/**
 * @Author: zhoujinchuan
 * @Description: （用户角色）
 * @CreateTime: 2023/12/20 11:29
 */
@Getter
public enum UserRoleEnum {

    /**
     *
     */
    ADMIN("管理员"),
    USER("用户"),
    DOCTOR("医生");


    private String label;


    UserRoleEnum(String label) {
        this.label = label;
    }


}
