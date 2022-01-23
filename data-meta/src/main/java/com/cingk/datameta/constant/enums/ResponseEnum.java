package com.cingk.datameta.constant.enums;

public enum ResponseEnum {

    /**
     * ResponseHead.Status
     */
    CODE_SUCCESS("1", "程序处理成功"),
    CODE_FAIL("0", "程序处理出错"),

    //ResponseBody.ResCode,ResMessage
    /**
     * F1000-F1100, 服务入参检查
     */
    CODE_MESSAGE_F1000("F1000", "参数验证不通过"),

    /**
     * F1101-F1110,数据源操作
     */
    CODE_MESSAGE_F1101("F1101", "新增数据源出错"),
    CODE_MESSAGE_F1102("F1102", "查询数据源出错"),
    CODE_MESSAGE_F1103("F1103", "删除数据源出错"),
    CODE_MESSAGE_F1104("F1104", "修改数据源出错"),

    /**
     * F9000-F9999, 服务异常定义
     */
    CODE_MESSAGE_F9000("F9000", "保存数据出错"),
    CODE_MESSAGE_F9001("F9001", "查询数据出错"),
    CODE_MESSAGE_F9002("F9002", "修改数据出错"),
    CODE_MESSAGE_F9003("F9003", "删除数据出错");

    private final String code;
    private final String message;

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
