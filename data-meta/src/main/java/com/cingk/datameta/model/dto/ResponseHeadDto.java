package com.cingk.datameta.model.dto;

public class ResponseHeadDto {

    /**
     * 响应状态 0-失败,1-成功
     */
    private int status;

    /**
     * 响应描述，成功描述或出错描述
     */
    private String description;

    /**
     * 异常描述
     */
    private String exceptionMessage;

    /**
     * 异常堆栈信息
     */
    private String exceptionTrace;

    /**
     * 接口耗时,单位ms
     */
    private int costTime;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionTrace() {
        return exceptionTrace;
    }

    public void setExceptionTrace(String exceptionTrace) {
        this.exceptionTrace = exceptionTrace;
    }

    public int getCostTime() {
        return costTime;
    }

    public void setCostTime(int costTime) {
        this.costTime = costTime;
    }
}
