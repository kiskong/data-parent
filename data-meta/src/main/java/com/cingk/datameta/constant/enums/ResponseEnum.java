package com.cingk.datameta.constant.enums;

public enum ResponseEnum {

    CODE_MESSAGE_F1000("F1000", "参数验证不通过"),
    CODE_MESSAGE_F1001("F1001", "数据保存失败");

    private String code;
    private String message;

    ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getValue() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }

    public static String getValue(String code) {
        for (ResponseEnum responseEnum : ResponseEnum.values()) {
            if (!responseEnum.getCode().equals(code)) {
                continue;
            }
            return responseEnum.getValue();
        }
        return null;
    }
}
