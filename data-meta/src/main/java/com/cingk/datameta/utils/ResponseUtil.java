package com.cingk.datameta.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import cn.hutool.core.util.StrUtil;
import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.InterfaceEntity;
import com.cingk.datameta.model.dto.ResponseDto;

@Component
public class ResponseUtil {

	public ResponseDto success() {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setDescription(ResponseEnum.CODE_SUCCESS.getValue());
		responseDto.setStatus(ResponseEnum.CODE_SUCCESS.getCode());
		return responseDto;
	}

	public ResponseDto success(String description) {
		ResponseDto responseDto = success();
		responseDto.setDescription(description);
		return responseDto;
	}

	public ResponseDto success(String resCode, String description) {
		ResponseDto responseDto = success();
		responseDto.setResCode(resCode);
		responseDto.setDescription(description);
		return responseDto;
	}

	public ResponseDto success(List data) {
		ResponseDto responseDto = success();
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto success(Map data) {
		ResponseDto responseDto = success();
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto success(InterfaceEntity data) {
		ResponseDto responseDto = success();
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto success(String resCode, String description, List data) {
		ResponseDto responseDto = success(resCode, description);
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto success(String description, List data) {
		ResponseDto responseDto = success(description);
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto success(String resCode, String description, Map data) {
		ResponseDto responseDto = success(resCode, description);
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto success(String description, Map data) {
		ResponseDto responseDto = success(description);
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto success(String resCode, String description, InterfaceEntity data) {
		ResponseDto responseDto = success(resCode, description);
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto success(String description, InterfaceEntity data) {
		ResponseDto responseDto = success(description);
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto failure() {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setDescription(ResponseEnum.CODE_FAIL.getValue());
		responseDto.setStatus(ResponseEnum.CODE_FAIL.getCode());
		return responseDto;
	}

	public ResponseDto failure(String description) {
		ResponseDto responseDto = failure();
		responseDto.setDescription(description);
		return responseDto;
	}

	public ResponseDto failure(String resCode, String description) {
		ResponseDto responseDto = failure();
		responseDto.setResCode(resCode);
		responseDto.setDescription(description);
		return responseDto;
	}

	public ResponseDto failure(List data) {
		ResponseDto responseDto = failure();
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto failure(Map data) {
		ResponseDto responseDto = failure();
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto failure(InterfaceEntity data) {
		ResponseDto responseDto = failure();
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto failure(String resCode, String description, List data) {
		ResponseDto responseDto = failure(resCode, description);
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto failure(String description, List data) {
		ResponseDto responseDto = failure(description);
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto failure(String resCode, String description, Map data) {
		ResponseDto responseDto = failure(resCode, description);
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto failure(String description, Map data) {
		ResponseDto responseDto = failure(description);
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto failure(String resCode, String description, InterfaceEntity data) {
		ResponseDto responseDto = failure(resCode, description);
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto failure(String description, InterfaceEntity data) {
		ResponseDto responseDto = failure(description);
		responseDto.setData(data);
		return responseDto;
	}

	public ResponseDto failure(Exception e) {
		ResponseDto responseDto = failure();
		responseDto.setExceptionMessage(e.getMessage());
		responseDto.setExceptionTrace(ExceptionUtils.getStackTrace(e));
		return responseDto;
	}

	/**
	 * ??????????????????
	 *
	 * @param responseDto ???????????????????????????
	 * @param bindingResult ?????????????????????????????????
	 * @return boolean
	 */
	public boolean validateParamFailure(ResponseDto responseDto, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			return true;
		}
		List<ObjectError> errors = bindingResult.getAllErrors();
		StringBuffer errorMsg = new StringBuffer();
		String errorStr = errors.stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.collect(Collectors.joining(StrUtil.COMMA));
		errorMsg.append(errorStr);
		responseDto.setDescription(errorMsg.toString());
		responseDto.setResCode(ResponseEnum.CODE_MESSAGE_F1000.getCode());
		return false;
	}
}
