package com.cingk.datameta.model.dto;

import com.google.common.collect.Lists;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.InterfaceEntity;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * 响应dto
 *
 * @author lvkc
 * @date 2022/02/10
 */
@Schema(description = "响应dto")
public class ResponseDto<T> implements Serializable {

    /**
     * 具体数据
     *
     */
    @Schema(description = "业务响应的具体数据")
    @SuppressWarnings("unchecked")
    private T data;

    /**
     * 响应信息,响应编码对应的描述,通过枚举自动映射
     */
    @Schema(description = "响应信息,响应编码对应的描述,通过枚举自动映射")
    private String resMessage;

    /**
     * 响应编码
     */
    @Schema(description = "响应编码")
    private String resCode;

    public void setDataSize(Integer dataSize) {
        this.dataSize = dataSize;
    }

    /**
     * 数据长度
     */
    @Schema(description = "数据长度")
    private Integer dataSize;

    @SuppressWarnings("unchecked")
    public T getData() {return data;}

    @SuppressWarnings("unchecked")
    public void setData(T data) {
        this.data = data;
    }

    public String getResMessage() {
        return resMessage;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
        this.resMessage = ResponseEnum.getValue(resCode);
    }

    public Integer getDataSize() {
        return dataSize;
    }

    /**
     * 响应状态 0-失败,1-成功
     */
    @Schema(description = "响应状态 0-失败,1-成功")
    private String status;

    /**
     * 响应描述，成功描述或出错描述
     */
    @Schema(description = "响应描述，成功描述或出错描述")
    private String description;

    /**
     * 异常描述
     */
    @Schema(description = "异常描述")
    private String exceptionMessage;

    /**
     * 异常堆栈信息
     */
    @Schema(description = "异常堆栈信息")
    private String exceptionTrace;

    /**
     * 接口耗时,单位ms
     */
    @Schema(description = "接口耗时,单位ms")
    private String costTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }

    public boolean successed(){
        return ResponseEnum.CODE_SUCCESS.getCode().equals(this.status);
    }

}
