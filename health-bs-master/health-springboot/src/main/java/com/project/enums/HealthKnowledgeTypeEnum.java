package com.project.enums;

/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
*/
public enum HealthKnowledgeTypeEnum {

    /**
    * (1-就医篇,2-误区篇,3-饮食篇,4-生活篇,)
    */
    JIUYI("1","就医篇"),
    WUQU("2","误区篇"),
    YINSHI("3","饮食篇"),
    SHENGHUO("4","生活篇"),
    ;

    private String code;

    private String label;

    HealthKnowledgeTypeEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public static String getLabelByCode(String code){
        for (HealthKnowledgeTypeEnum value : values()) {
            if (value.getCode().equals(code)){
                return value.getLabel();
            }
        }
        return "";
    }

}
