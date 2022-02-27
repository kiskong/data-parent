package com.cingk.datameta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.service.impl.ColumnService;
import com.cingk.datameta.service.intf.IDataSourceService;
import com.cingk.datameta.utils.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
public class ColumnController {


	private IDataSourceService dataSourceService;
	private ColumnService columnService;
	private ResponseUtil responseUtil;

	@Autowired
	public void setResponseUtil(ResponseUtil responseUtil) {
		this.responseUtil = responseUtil;
	}

	@Autowired
	public void setDataSourceService(IDataSourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	@Autowired
	public void setColumnService(ColumnService columnService) {
		this.columnService = columnService;
	}

	@Operation(summary = "采集目标数据源指定模式名下的所有表字段信息")
	@GetMapping("getAndSaveTagDBColumnBySchema")
	public ResponseDto getAndSaveTagDBColumnBySchema(
		@Parameter(description = "数据源标识", required = true) @NotNull Integer dataSourceId,
		@Parameter(description = "模式名称", required = true) @NotNull String schemaName) {
		ResponseDto responseDto = dataSourceService.getDataSourceById(dataSourceId);
		if (!responseDto.successed()) {
			return responseDto;
		}
		DataSourceDto dataSourceDto = (DataSourceDto) responseDto.getData();
		columnService.getAndSaveTagDBColumnBySchema(dataSourceDto, schemaName);
		return responseUtil.success("采集表字段成功");
	}

	@Operation(summary = "采集目标数据源指定模式名下的单个表字段信息")
	@GetMapping("getAndSaveTagDBColumnByTableName")
	public ResponseDto getAndSaveTagDBColumnByTableName(
		@Parameter(description = "数据源标识", required = true) @NotNull Integer dataSourceId,
		@Parameter(description = "模式名称", required = true) @NotNull String schemaName,
		@Parameter(description = "表名称", required = true) @NotNull String tableName) {

		ResponseDto responseDto = dataSourceService.getDataSourceById(dataSourceId);
		if (!responseDto.successed()) {
			return responseDto;
		}
		DataSourceDto dataSourceDto = (DataSourceDto) responseDto.getData();
		columnService.getAndSaveTagDBColumnByTableName(dataSourceDto, schemaName,tableName);
		return responseUtil.success("采集表字段成功");
	}

	@Operation(summary = "采集目标数据源指定模式名下的多个表字段信息")
	@GetMapping("getAndSaveTagDBColumnByTableNames")
	public ResponseDto getAndSaveTagDBColumnByTableNames(
		@Parameter(description = "数据源标识", required = true) @NotNull Integer dataSourceId,
		@Parameter(description = "模式名称", required = true) @NotNull String schemaName,
		@Parameter(description = "表名称列表", required = true) @NotNull String[] tableNames) {

		ResponseDto responseDto = dataSourceService.getDataSourceById(dataSourceId);
		if (!responseDto.successed()) {
			return responseDto;
		}
		DataSourceDto dataSourceDto = (DataSourceDto) responseDto.getData();
		columnService.getAndSaveTagDBColumnByTableName(dataSourceDto, schemaName,tableNames);
		return responseUtil.success("采集表字段成功");
	}
}
