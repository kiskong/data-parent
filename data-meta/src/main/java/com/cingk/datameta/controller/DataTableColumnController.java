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

	private DataTableColumnUtil dataTableColumnUtil;
	private DataTableUtil dataTableUtil;
	private IDataSource dataSourceService;
	private DataTableService dataTableService;
	private IDataTableColumn dataTableColumnService;

	@Operation(summary = "查看目标数据源指定模式名下的表字段信息", description = "查看目标数据源指定模式名下的表字段信息")
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
		IDataTableColumn columnService = dataTableColumnUtil.getDataTableColumnService(dataSourceDto);
		try {
			List<IDataTableColumnEntity> dataTableColumnEntityList = columnService.getTableColumn(dataSourceDto, schema, tableName);
			return responseUtil.success("查询数据成功", dataTableColumnEntityList);
		} catch (RuntimeException e) {
			LOGGER.error(e.getMessage(), e);
			return responseUtil.failure(e);
		}
	}

	@Operation(summary = "查看目标数据源指定模式名下的表字段信息", description = "查看目标数据源指定模式名下的表字段信息")
	@GetMapping("getSrcDataTableColumn")
	public ResponseDto getSrcDataTableColumn(
		@Parameter(description = "数据源标识", required = true) @NotNull Integer dataSourceId,
		@Parameter(description = "模式名称", required = true) @NotNull String schema,
		@Parameter(description = "数据表名", required = true) @NotNull String[] tableNames) {
		ResponseDto responseDto = dataSourceService.getDataSourceById(dataSourceId);
		if (!responseDto.successed()) {
			return responseDto;
		}
		DataSourceDto dataSourceDto = (DataSourceDto) responseDto.getData().get(0);
		IDataTableColumn columnService = dataTableColumnUtil.getDataTableColumnService(dataSourceDto);
		try {
			List<IDataTableColumnEntity> dataTableColumnEntityList = columnService.getTableColumn(dataSourceDto, schema, tableNames);
			return responseUtil.success("查询数据成功", dataTableColumnEntityList);
		} catch (RuntimeException e) {
			LOGGER.error(e.getMessage(), e);
			return responseUtil.failure(e);
		}
	}

	/**
	 * 查看本地服务存储的数据库表字段信息
	 *
	 * @param dataSourceId 数据源标识
	 * @param schemaName 模式名
	 * @param tableName 表名
	 * @return ResponseDto 响应体
	 */
	@Operation(summary = "查看本地服务存储的数据库表字段信息")
	@GetMapping("/getLocalDataTableColumn")
	public ResponseDto getLocalDataTableColumn(
		@Parameter(description = "数据源标识", required = true) @NotNull Integer dataSourceId,
		@Parameter(description = "模式名称", required = true) @NotNull String schemaName,
		@Parameter(description = "数据表名", required = true) @NotNull String tableName) {
		List<DataTableColumnEntity> dataTableColumnEntities = getTableColumn(dataSourceId, schemaName, new String[]{tableName});
		return responseUtil.success("查询成功", dataTableColumnEntities);
	}

	/**
	 * 查看本地服务存储的数据库表字段信息
	 *
	 * @param dataSourceId 数据源标识
	 * @param schemaName 模式名
	 * @param tableNames 表名列表
	 * @return ResponseDto 响应体
	 */
	@Operation(summary = "查看本地服务存储的数据库表字段信息")
	@GetMapping("/getLocalDataTableColumn")
	public ResponseDto getLocalDataTableColumn(
		@Parameter(description = "数据源标识", required = true) @NotNull Integer dataSourceId,
		@Parameter(description = "模式名称", required = true) @NotNull String schemaName,
		@Parameter(description = "数据表名", required = true) @NotNull String[] tableNames) {
		List<DataTableColumnEntity> dataTableColumnEntities = getTableColumn(dataSourceId, schemaName, tableNames);
		return responseUtil.success("查询成功", dataTableColumnEntities);
	}


	@Transactional
	@Operation(summary = "采集模式名下指定数据表的字段")
	@PostMapping("/fetchAndSaveTabColumnByTableName")
	public ResponseDto fetchAndSaveTabColumnByTableName(@RequestBody @Valid DataTableColumnAo dataTableColumnAo,
		BindingResult bindingResult) {
		// 1. 参数校验
		ResponseDto responseDto = responseUtil.failure();
		if (!responseUtil.validateParamFailure(responseDto, bindingResult)) {
			return responseDto;
		}
		// 2. 数据源是否存在
		responseDto = dataSourceService.getDataSourceById(dataTableColumnAo.getDataSourceId());
		if (!responseDto.successed()) {
			return responseDto;
		}
		DataSourceDto dataSourceDto = (DataSourceDto) responseDto.getData().get(0);
		// 3. 找到对应数据源的Service实现
		IDataTableColumn columnService = dataTableColumnUtil.getDataTableColumnService(dataSourceDto);
		// 5. 提取并保存数据表及数据表字段
		return fetchAndSaveTabColumnByTableName(dataSourceDto, dataTableColumnAo, columnService);
	}


	@Transactional
	@Operation(summary = "采集模式名下所有数据表的字段")
	@PostMapping("/fetchAndSaveTabColumnBySchema")
	public ResponseDto fetchAndSaveTabColumnBySchema(
		@Parameter(description = "数据源标识", required = true) @NotNull Integer dataSourceId,
		@Parameter(description = "模式名称", required = true) @NotNull String schemaName) {
		ResponseDto responseDto = dataSourceService.getDataSourceById(dataSourceId);
		if (!responseDto.successed()) {
			return responseDto;
		}
		DataSourceDto dataSourceDto = (DataSourceDto) responseDto.getData().get(0);
		IDataTable tableService = dataTableUtil.getDataTableService(dataSourceDto);
		List<DataTableEntity> dataTableEntityList = tableService.getSrcAllTablesWithSchema(dataSourceDto, schemaName);
		List<DataTableEntity> dataTableEntities = dataTableService.saveAllTablesNotExists(dataSourceId, schemaName, dataTableEntityList);
		return this.fetchAndSaveTabColumnByTableName(dataSourceDto, schemaName, dataTableEntities);
	}

	/**
	 * 同步目标数据源表，更新本地存储的数据库表字段信息
	 *
	 * @param dataTableColumnAo 数据源-模式名-表参数
	 * @param bindingResult 校验检查结果
	 * @return ResponseDto 响应体
	 */
	@Operation(summary = "同步目标数据源表，更新本地存储的数据库表字段信息")
	@PostMapping("/updateTableColumn")
	public ResponseDto updateTableColumn(@RequestBody @Valid DataTableColumnAo dataTableColumnAo,
		BindingResult bindingResult) {
		// 1. 参数校验
		ResponseDto responseDto = responseUtil.failure();
		if (!responseUtil.validateParamFailure(responseDto, bindingResult)) {
			return responseDto;
		}
		Integer dataSourceId = dataTableColumnAo.getDataSourceId();
		responseDto = dataSourceService.getDataSourceById(dataSourceId);
		if (!responseDto.successed()) {
			return responseDto;
		}
		String[] tableNames = dataTableColumnAo.getTableNames();
		String schemaName = dataTableColumnAo.getSchemaName();
		DataSourceDto dataSourceDto = (DataSourceDto) responseDto.getData().get(0);
		IDataTableColumn columnService = dataTableColumnUtil.getDataTableColumnService(dataSourceDto);
		//目标源数据表字段
		List<IDataTableColumnEntity> dataTableColumnEntityList = columnService.getTableColumn(dataSourceDto, schemaName, tableNames);

		List<DataTableColumnEntity> srcDataTableColumnEntityList = Lists.newArrayList();
		dataTableColumnEntityList.forEach(dataTableColumnEntity -> {
			DataTableColumnEntity commonDataTableColumnEntity = new DataTableColumnEntity();
			commonDataTableColumnEntity.setTabId(dataTableColumnEntity.getTabId());
			commonDataTableColumnEntity.setColType(dataTableColumnEntity.getColType());
			commonDataTableColumnEntity.setColLength(dataTableColumnEntity.getColLength());
			commonDataTableColumnEntity.setColName(dataTableColumnEntity.getColName());
			commonDataTableColumnEntity.setColId(dataTableColumnEntity.getColId());
			commonDataTableColumnEntity.setColDecimal(dataTableColumnEntity.getColDecimal());
			srcDataTableColumnEntityList.add(commonDataTableColumnEntity);
		});

		//本地存储的表字段
		List<DataTableColumnEntity> localDataTableColumnEntityList = getTableColumn(dataSourceId, schemaName, tableNames);

		//有变化的表
		//todo
		// 更新版本号，记录新的字段信息

		return null;
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

		dataTableEntityList.forEach(dataTableEntity -> {
			List<IDataTableColumnEntity> dataTableColumnEntityList =
				columnService.getTableColumn(dataSourceDto, schemaName, dataTableEntity.getTabName());

			List<DataTableColumnEntity> dataTableColumnEntities = Lists.newArrayList();
			dataTableColumnEntityList.forEach(iDataTableColumnEntity -> {
				DataTableColumnEntity dataTableColumnEntity = copyProperties(iDataTableColumnEntity);
				dataTableColumnEntity.setTabId(dataTableEntity.getId());
				dataTableColumnEntities.add(dataTableColumnEntity);
			});

			dataTableColumnService.saveAllTableColumn(dataTableColumnEntities);
		});
		return responseUtil.success("采集数据库表字段成功");
	}

	/**
	 * 查询目标数据源的表字段并将其保存在本地服务
	 *
	 * @param dataSourceDto 数据源
	 * @param dataTableColumnAo 传入的表信息参数
	 * @param columnService 具体数据源的列服务实现类
	 * @return ResponseDto 响应结构
	 */
	private ResponseDto fetchAndSaveTabColumnByTableName(DataSourceDto dataSourceDto, DataTableColumnAo dataTableColumnAo,
		IDataTableColumn columnService) {
		String schemaName = dataTableColumnAo.getSchemaName();
		String[] tableNames = dataTableColumnAo.getTableNames();
		Arrays.asList(tableNames).forEach(tableName -> {
			List<IDataTableColumnEntity> dataTableColumnEntityList = columnService.getTableColumn(dataSourceDto, schemaName, tableName);

			List<DataTableColumnEntity> dataTableColumnEntities = Lists.newArrayList();
			dataTableColumnEntityList.forEach(iDataTableColumnEntity -> {
				DataTableColumnEntity dataTableColumnEntity = copyProperties(iDataTableColumnEntity);
				dataTableColumnEntity.setTabId(iDataTableColumnEntity.getTabId());
				dataTableColumnEntities.add(dataTableColumnEntity);
			});

			dataTableColumnService.saveAllTableColumn(dataTableColumnEntities);
		});
		return responseUtil.success("采集数据库表字段成功");
	}


	/**
	 * 属性copy，将各数据源原始的表字段属性提取到共性的表字段属性
	 *
	 * @param iDataTableColumnEntity 字段实体接口
	 * @return DataTableColumnEntity 表字段实体
	 */
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


	/**
	 * 查询本地存储的表字段信息
	 * @param dataSourceId 数据源标识
	 * @param schemaName 模式名
	 * @param tableNames 表名列表
	 * @return List<DataTableColumnEntity> 数据表字段实体
	 */
	private List<DataTableColumnEntity> getTableColumn(Integer dataSourceId, String schemaName, String[] tableNames) {
		List<DataTableEntity> dataTableEntityList = dataTableService.getLocalAllTables(dataSourceId, schemaName, tableNames);
		List<DataTableColumnEntity> dataTableColumnEntityList = Lists.newArrayList();
		dataTableEntityList.forEach(dataTableEntity -> {
			DataTableColumnEntity dataTableColumnEntity = new DataTableColumnEntity();
			dataTableColumnEntity.setTabId(dataTableEntity.getId());
			dataTableColumnEntityList.add(dataTableColumnEntity);
		});
		return dataTableColumnService.getLocalTableColumn(dataTableColumnEntityList);
	}


	@Autowired
	public void setDataTableColumnUtil(DataTableColumnUtil dataTableColumnUtil) {
		this.dataTableColumnUtil = dataTableColumnUtil;
	}

	@Autowired
	public void setDataTableUtil(DataTableUtil dataTableUtil) {
		this.dataTableUtil = dataTableUtil;
	}

	@Autowired
	public void setDataSourceService(IDataSource dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	@Autowired
	public void setDataTableService(DataTableService dataTableService) {
		this.dataTableService = dataTableService;
	}

	@Autowired
	public void setDataTableColumnService(IDataTableColumn dataTableColumnService) {
		this.dataTableColumnService = dataTableColumnService;
	}
}
