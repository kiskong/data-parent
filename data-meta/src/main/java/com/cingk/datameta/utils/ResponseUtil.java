package com.cingk.datameta.utils;

import cn.hutool.core.util.StrUtil;
import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.InterfaceEntity;
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

    public ResponseBodyDto success(){
        ResponseBodyDto responseBodyDto = new ResponseBodyDto();
        responseBodyDto.setDescription(ResponseEnum.CODE_SUCCESS.getValue());
        responseBodyDto.setStatus(ResponseEnum.CODE_SUCCESS.getCode());
        return responseBodyDto;
    }

    public ResponseBodyDto success(String description){
        ResponseBodyDto responseBodyDto = success();
        responseBodyDto.setDescription(description);
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

    public ResponseBodyDto success(InterfaceEntity data){
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

    public ResponseBodyDto success(String resCode, String description, InterfaceEntity data){
        ResponseBodyDto responseBodyDto = success(resCode,description);
        responseBodyDto.setData(data);
        return responseBodyDto;
    }

    public ResponseBodyDto failure(){
        ResponseBodyDto responseBodyDto = new ResponseBodyDto();
        responseBodyDto.setDescription(ResponseEnum.CODE_FAIL.getValue());
        responseBodyDto.setStatus(ResponseEnum.CODE_FAIL.getCode());
        return responseBodyDto;
    }

    public ResponseBodyDto failure(String description){
        ResponseBodyDto responseBodyDto = failure();
        responseBodyDto.setDescription(description);
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
    public ResponseBodyDto failure(InterfaceEntity data){
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

    public ResponseBodyDto failure(String resCode, String description, InterfaceEntity data){
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
        StringBuffer errorMsg = new StringBuffer();
        String errorStr = errors.stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(StrUtil.COMMA));
        errorMsg.append(errorStr);
        responseBodyDto.setDescription(errorMsg.toString());
        responseBodyDto.setResCode(ResponseEnum.CODE_MESSAGE_F1000.getCode());
        return false;
    }
}
