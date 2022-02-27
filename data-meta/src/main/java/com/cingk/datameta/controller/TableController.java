package com.cingk.datameta.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cingk.datameta.model.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.constraints.NotNull;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-02-26
 */
@RestController
@RequestMapping("/api")
public class TableController {

	//todo
	public ResponseDto getTableList(
		@Parameter(description = "数据源标识", required = true) @NotNull Integer dataSourceId,
		@Parameter(description = "模式名称", required = true) @NotNull String schemaName) {
		return null;
	}
}
