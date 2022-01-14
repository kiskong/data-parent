package com.cingk.datameta.utils;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.dto.IntfDto;
import com.cingk.datameta.model.dto.ResponseBodyDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ResponseUtil {

    private static final int STATUS_SUCCESS = 1;
    private static final int STATUS_FAILURE = 0;
    private static final String DEFAULT_FAILURE_MESSAGE = "程序处理出错";
    private static final String DEFAULT_SUCCESS_MESSAGE = "程序处理成功";


    public ResponseBodyDto success(){
        ResponseBodyDto responseBodyDto = new ResponseBodyDto();
        responseBodyDto.setDescription(DEFAULT_SUCCESS_MESSAGE);
        responseBodyDto.setStatus(STATUS_SUCCESS);
        return responseBodyDto;
    }
    public ResponseBodyDto success(String resCode,String description){
        ResponseBodyDto responseBodyDto = success();
        responseBodyDto.setResCode(resCode);
        responseBodyDto.setDescription(description);
        return responseBodyDto;
    }

    public ResponseBodyDto success(List data){
        ResponseBodyDto responseBodyDto = success();
        responseBodyDto.setData(data);
        return responseBodyDto;
    }

    public ResponseBodyDto success(Map data){
        ResponseBodyDto responseBodyDto = success();
        responseBodyDto.setData(data);
        return responseBodyDto;
    }

    public ResponseBodyDto success(IntfDto data){
        ResponseBodyDto responseBodyDto = success();
        responseBodyDto.setData(data);
        return responseBodyDto;
    }

    public ResponseBodyDto success(String resCode,String description,List data){
        ResponseBodyDto responseBodyDto = success(resCode,description);
        responseBodyDto.setData(data);
        return responseBodyDto;
    }

    public ResponseBodyDto success(String resCode,String description,Map data){
        ResponseBodyDto responseBodyDto = success(resCode,description);
        responseBodyDto.setData(data);
        return responseBodyDto;
    }

    public ResponseBodyDto success(String resCode, String description, IntfDto data){
        ResponseBodyDto responseBodyDto = success(resCode,description);
        responseBodyDto.setData(data);
        return responseBodyDto;
    }

    public ResponseBodyDto failure(){
        ResponseBodyDto responseBodyDto = new ResponseBodyDto();
        responseBodyDto.setDescription(DEFAULT_FAILURE_MESSAGE);
        responseBodyDto.setStatus(STATUS_FAILURE);
        return responseBodyDto;
    }

    public ResponseBodyDto failure(String resCode,String description){
        ResponseBodyDto responseBodyDto = failure();
        responseBodyDto.setResCode(resCode);
        responseBodyDto.setDescription(description);
        return responseBodyDto;
    }

    public ResponseBodyDto failure(List data){
        ResponseBodyDto responseBodyDto = failure();
        responseBodyDto.setData(data);
        return responseBodyDto;
    }

    public ResponseBodyDto failure(Map data){
        ResponseBodyDto responseBodyDto = failure();
        responseBodyDto.setData(data);
        return responseBodyDto;
    }
    public ResponseBodyDto failure(IntfDto data){
        ResponseBodyDto responseBodyDto = failure();
        responseBodyDto.setData(data);
        return responseBodyDto;
    }

    public ResponseBodyDto failure(String resCode,String description,List data){
        ResponseBodyDto responseBodyDto = failure(resCode,description);
        responseBodyDto.setData(data);
        return responseBodyDto;
    }

    public ResponseBodyDto failure(String resCode,String description,Map data){
        ResponseBodyDto responseBodyDto = failure(resCode,description);
        responseBodyDto.setData(data);
        return responseBodyDto;
    }

    public ResponseBodyDto failure(String resCode, String description, IntfDto data){
        ResponseBodyDto responseBodyDto = failure(resCode,description);
        responseBodyDto.setData(data);
        return responseBodyDto;
    }

    /**
     * 入参校验结果
     * @param responseBodyDto 返回前端的响应结构
     * @param bindingResult 框架绑定的参数校验结果
     * @return boolean
     */
    public boolean validateParamFailure(ResponseBodyDto responseBodyDto, BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            return true;
        }
        List<ObjectError> errors = bindingResult.getAllErrors();
        StringBuffer errorMsg = new StringBuffer("");
        String errorStr =
                errors.stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(","));
        errorMsg.append(errorStr);
        responseBodyDto.setDescription(errorMsg.toString());
        responseBodyDto.setResCode(ResponseEnum.CODE_MESSAGE_F1000.getCode());
        return false;
    }
}
