package com.cingk.datameta.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.InterfaceEntity;

public class ResponseBodyDto extends ResponseHeadDto {

	/**
	 * 具体数据
	 */
	private List data;

	/**
	 * 响应信息,响应编码对应的描述,通过枚举自动映射
	 */
	private String resMessage;

	/**
	 * 响应编码
	 */
	private String resCode;

	public void setDataSize(Integer dataSize) {
		this.dataSize = dataSize;
	}

	/**
	 * 数据长度
	 */
	private Integer dataSize;

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public String getResMessage() {
		return resMessage;
	}

	public void setData(Map data) {
		this.data = new ArrayList();
		this.data.add(data);
	}

	public void setData(InterfaceEntity data) {
		this.data = new ArrayList();
		this.data.add(data);
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
		this.resMessage = ResponseEnum.getValue(resCode);
	}

	public int getDataSize() {
		if (dataSize != null) {
			return dataSize;
		}
		if (data != null) {
			return data.size();
		}
		return dataSize;
	}

}
