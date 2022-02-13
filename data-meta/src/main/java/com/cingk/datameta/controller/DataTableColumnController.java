package com.cingk.datameta.controller;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.ao.DataTableColumnAo;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.model.entity.DataTableColumnEntity;
import com.cingk.datameta.model.entity.DataTableEntity;
import com.cingk.datameta.service.impl.DataTableService;
import com.cingk.datameta.service.intf.IDataSource;
import com.cingk.datameta.service.intf.IDataTable;
import com.cingk.datameta.service.intf.IDataTableColumn;
import com.cingk.datameta.utils.DataTableColumnUtil;
import com.cingk.datameta.utils.DataTableUtil;
import com.cingk.datameta.utils.SpringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-02-02
 */
//@Tag(name="数据库字段操作")
@RestController
@RequestMapping("/api")
public class DataTableColumnController extends BaseRequestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataTableColumnController.class);

	@Autowired
	private DataTableColumnUtil dataTableColumnUtil;
	@Autowired
	private DataTableUtil dataTableUtil;
	@Autowired
	private IDataSource dataSourceService;

	@Autowired
	private DataTableService dataTableService;

	@Autowired
	private IDataTableColumn dataTableColumnService;

	@Operation(summary = "查看数据源模式名下指定表字段信息", description = "上游结构")
	@GetMapping("getSrcDataTableColumn")
	public ResponseDto getSrcDataTableColumn(
		@Parameter(description = "数据源标识", required = true) @NotNull Integer dataSourceId,
		@Parameter(description = "模式名称", required = true) @NotNull String schema,
		@Parameter(description = "数据表名", required = true) @NotNull String tableName) {
		ResponseDto responseDto = dataSourceService.getDataSourceById(dataSourceId);
		if (!responseDto.successed()) {
			return responseDto;
		}
		DataSourceDto dataSourceDto = (DataSourceDto) responseDto.getData().get(0);
		String tableColumnServiceName = dataTableColumnUtil.getServiceNameByUrl(dataSourceDto);
		IDataTableColumn columnService = SpringUtil.getBean(tableColumnServiceName);
		try {
			List<IDataTableColumnEntity> dataTableColumnEntityList = columnService.getTableColumn(dataSourceDto, schema, tableName);
			return responseUtil.success("查询数据成功", dataTableColumnEntityList);
		} catch (RuntimeException e) {
			LOGGER.error(e.getMessage(), e);
			return responseUtil.failure(e);
		}
	}

	public ResponseDto getLocalDataTableColumn(
		@Parameter(description = "数据源标识", required = true) @NotNull Integer dataSourceId,
		@Parameter(description = "模式名称", required = true) @NotNull String schema,
		@Parameter(description = "数据表名", required = true) @NotNull String tableName) {

		List<DataTableEntity> dataTableEntityList = dataTableService.getLocalAllTables(dataSourceId, schema, new String[]{tableName});

		List<DataTableColumnEntity> dataTableColumnEntityList = Lists.newArrayList();
		dataTableEntityList.stream().forEach(dataTableEntity -> {
			DataTableColumnEntity dataTableColumnEntity = new DataTableColumnEntity();
			dataTableColumnEntity.setTabId(dataTableEntity.getId());
		});

		List<DataTableColumnEntity> dataTableColumnEntities = dataTableColumnService.getLocalTableColumn(dataTableColumnEntityList);
		return responseUtil.success("查询成功", dataTableColumnEntities);
	}


	@Transactional
	@Operation(summary = "采集模式名下指定数据表的字段")
	@PostMapping("/fetchAndSaveTabColumnByTableName")
	public ResponseDto fetchAndSaveTabColumnByTableName(@RequestBody @Valid DataTableColumnAo dataTableColumnAo,
		BindingResult bindingResult) {
		ResponseDto responseDto = responseUtil.failure();
		if (!responseUtil.validateParamFailure(responseDto, bindingResult)) {
			return responseDto;
		}
		boolean empty = Arrays.asList(dataTableColumnAo.getTableNames()).isEmpty();
//		if (empty){
//			return fe
//		}
		return fetchAndSaveTabColumnByTableName(dataTableColumnAo);
	}


	@Transactional
	@Operation(summary = "采集模式名下所有数据表的字段")
	@PostMapping("/fetchAndSaveTabColumn")
	public ResponseDto fetchAndSaveTabColumn(@RequestBody @Valid DataTableColumnAo dataTableColumnAo, BindingResult bindingResult) {
		ResponseDto responseDto = responseUtil.failure();
		if (!responseUtil.validateParamFailure(responseDto, bindingResult)) {
			return responseDto;
		}
		Integer dataSourceId = dataTableColumnAo.getDataSourceId();
		responseDto = dataSourceService.getDataSourceById(dataSourceId);
		if (!responseDto.successed()) {
			return responseDto;
		}
		DataSourceDto dataSourceDto = (DataSourceDto) responseDto.getData().get(0);
		String schemaName = dataTableColumnAo.getSchemaName();
		String tableServiceName = dataTableUtil.getServiceNameByUrl(dataSourceDto);
		IDataTable tableService = SpringUtil.getBean(tableServiceName);
		List<DataTableEntity> dataTableEntityList = tableService.getSrcAllTablesWithSchema(dataSourceDto, schemaName);
		List<DataTableEntity> dataTableEntities = dataTableService.saveAllTables(dataTableEntityList);
		return this.fetchAndSaveTabColumnByTableName(dataSourceDto, schemaName, dataTableEntities);
	}


	/**
	 * 提取指定数据源的表结构信息，并将其保存在本地服务
	 *
	 * @param dataSourceDto 数据源信息
	 * @param schemaName 模式名
	 * @param dataTableEntityList 表信息
	 */
	private ResponseDto fetchAndSaveTabColumnByTableName(DataSourceDto dataSourceDto, String schemaName,
		List<DataTableEntity> dataTableEntityList) {
		String tableColumnServiceName = dataTableColumnUtil.getServiceNameByUrl(dataSourceDto);
		IDataTableColumn columnService = SpringUtil.getBean(tableColumnServiceName);

		dataTableEntityList.stream().forEach(dataTableEntity -> {
			List<IDataTableColumnEntity> dataTableColumnEntityList =
				columnService.getTableColumn(dataSourceDto, schemaName, dataTableEntity.getTabName());

			List<DataTableColumnEntity> dataTableColumnEntities = Lists.newArrayList();
			dataTableColumnEntityList.stream()
				.forEach(iDataTableColumnEntity -> {
					DataTableColumnEntity dataTableColumnEntity = copyProperties(iDataTableColumnEntity);
					dataTableColumnEntity.setTabId(dataTableEntity.getId());
					dataTableColumnEntities.add(dataTableColumnEntity);
				});

			dataTableColumnService.saveAllTableColumn(dataTableColumnEntities);
		});
		return responseUtil.success("采集数据库表字段成功");
	}

	private ResponseDto fetchAndSaveTabColumnByTableName(DataTableColumnAo dataTableColumnAo) {
		Integer dataSourceId = dataTableColumnAo.getDataSourceId();
		String schemaName = dataTableColumnAo.getSchemaName();
		String[] tableNames = dataTableColumnAo.getTableNames();

		ResponseDto responseDto = dataSourceService.getDataSourceById(dataSourceId);
		if (!responseDto.successed()) {
			return responseDto;
		}
		DataSourceDto dataSourceDto = (DataSourceDto) responseDto.getData().get(0);
		String tableColumnServiceName = dataTableColumnUtil.getServiceNameByUrl(dataSourceDto);
		IDataTableColumn columnService = SpringUtil.getBean(tableColumnServiceName);

		Arrays.asList(tableNames).forEach(tableName -> {
			List<IDataTableColumnEntity> dataTableColumnEntityList = columnService.getTableColumn(dataSourceDto, schemaName, tableName);

			List<DataTableColumnEntity> dataTableColumnEntities = Lists.newArrayList();
			dataTableColumnEntityList.stream()
				.forEach(iDataTableColumnEntity -> {
					DataTableColumnEntity dataTableColumnEntity = copyProperties(iDataTableColumnEntity);
					dataTableColumnEntity.setTabId(iDataTableColumnEntity.getTabId());
					dataTableColumnEntities.add(dataTableColumnEntity);
				});

			dataTableColumnService.saveAllTableColumn(dataTableColumnEntities);
		});
		return responseUtil.success("采集数据库表字段成功");
	}


	private DataTableColumnEntity copyProperties(IDataTableColumnEntity iDataTableColumnEntity) {
		DataTableColumnEntity dataTableColumnEntity = new DataTableColumnEntity();
		dataTableColumnEntity.setColDecimal(iDataTableColumnEntity.getColDecimal());
		dataTableColumnEntity.setColId(iDataTableColumnEntity.getColId());
		dataTableColumnEntity.setId(iDataTableColumnEntity.getId());
		dataTableColumnEntity.setColName(iDataTableColumnEntity.getColName());
		dataTableColumnEntity.setColType(iDataTableColumnEntity.getColType());
		dataTableColumnEntity.setColLength(iDataTableColumnEntity.getColLength());
		return dataTableColumnEntity;
	}

}
