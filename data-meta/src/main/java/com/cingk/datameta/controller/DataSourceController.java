package com.cingk.datameta.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.InterfaceEntity;
import com.cingk.datameta.model.ao.DataSourceAo;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.service.impl.DataSourceService;
import com.cingk.datameta.utils.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.constraints.NotNull;

//@Tag(name="数据源操作")
@RestController
@RequestMapping("/api")
public class DataSourceController {

	private DataSourceService dataSourceService;
	private ResponseUtil responseUtil;

	@Operation(summary = "通过数据源名称获取数据源")
	@GetMapping("getDatabaseSourceByName")
	public ResponseDto getDatabaseSourceByName(@Parameter(description = "数据源名称") String databaseName) {
		ResponseDto responseDto = dataSourceService.getDataSourceByName(databaseName);
		if (!responseDto.successed()) {
			return responseDto;
		}
		DataSourceDto dataSourceDto = (DataSourceDto) responseDto.getData();
		return responseUtil.success(dataSourceDto);
	}

	@Operation(summary = "通过数据源标识获取数据源")
	@GetMapping("getDatabaseSourceById")
	public ResponseDto getDatabaseSourceById(@Parameter(description = "数据源标识") Integer id) {

		ResponseDto responseDto = dataSourceService.getDataSourceById(id);
		if (!responseDto.successed()) {
			return responseDto;
		}
		DataSourceDto dataSourceDto = (DataSourceDto) responseDto.getData();
		return responseUtil.success(dataSourceDto);
	}

	@GetMapping("getAllDatabaseSource")
	public ResponseDto getAllDatabaseSource() {
		return responseUtil.success(dataSourceService.queryAll());
	}

	@PostMapping("getPageDatabaseSource")
	public ResponseDto getPageDatabaseSource(
		@RequestParam(defaultValue = "1") @Parameter(description = "页码") String pageIndex,
		@RequestParam(defaultValue = "10") @Parameter(description = "每页数量") String pageSize) {
		return responseUtil.success(dataSourceService.queryPage(pageIndex, pageSize));
	}

	@Transactional()
	@PostMapping("addDatabaseSource")
	public ResponseDto addDatabaseSource(
		@RequestBody @Validated DataSourceAo dataSourceAo, BindingResult bindingResult) {

		ResponseDto responseDto = responseUtil.failure();
		if (!responseUtil.validateParamFailure(responseDto, bindingResult)) {
			return responseDto;
		}
		InterfaceEntity databaseSourceEntity = dataSourceService.save(dataSourceAo);
		if (databaseSourceEntity == null) {
			return responseUtil.failure(ResponseEnum.CODE_MESSAGE_F9000.getCode(), "保存数据源出错");
		}

		DataSourceDto dataSourceDto = new DataSourceDto();
		BeanUtils.copyProperties(databaseSourceEntity, dataSourceDto);
		return responseUtil.success(dataSourceDto);
	}

	@Transactional()
	@DeleteMapping("delDatabaseSourceById")
	public ResponseDto deleteDatabaseSourceById(
		@NotNull @RequestParam(value = "id") @Parameter(description = "数据源标识") Integer id) {
		DataSourceDto dataSourceDto = new DataSourceDto();
		dataSourceDto.setId(id);
		dataSourceService.delete(dataSourceDto);
		return responseUtil.success("删除成功");
	}

	@Transactional()
	@DeleteMapping("delDatabaseSourceByName")
	public ResponseDto deleteDatabaseSourceByName(
		@NotNull @RequestParam(value = "name") @Parameter(description = "数据源名称") String name) {
		dataSourceService.deleteByName(name);
		return responseUtil.success("删除成功");
	}

	@Autowired
	public void setDataSourceService(DataSourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	@Autowired
	public void setResponseUtil(ResponseUtil responseUtil) {
		this.responseUtil = responseUtil;
	}
}
