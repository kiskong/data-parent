package com.cingk.datameta.service.impl;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cingk.datameta.mapper.IColumnRepository;
import com.cingk.datameta.model.IColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.entity.ColumnEntity;
import com.cingk.datameta.model.entity.TableEntity;
import com.cingk.datameta.service.intf.IColumnService;
import com.cingk.datameta.utils.ColumnUtil;
import com.cingk.datameta.utils.TableUtil;

@Service
public class ColumnService  {

	private TableUtil tableUtil;
	private ColumnUtil columnUtil;
	//本地数据源的数据表服务实现
	private TableService tableService;

	private IColumnRepository columnRepository;

	private TargetTableService targetTableService;

	@Autowired
	public void setTableUtil(TableUtil tableUtil) {
		this.tableUtil = tableUtil;
	}

	@Autowired
	public void setColumnUtil(ColumnUtil columnUtil) {
		this.columnUtil = columnUtil;
	}

	@Autowired
	public void setTableService(TableService tableService) {
		this.tableService = tableService;
	}

	@Autowired
	public void setColumnRepository(IColumnRepository columnRepository) {
		this.columnRepository = columnRepository;
	}

	@Autowired
	public void setTargetTableService(TargetTableService targetTableService) {
		this.targetTableService = targetTableService;
	}

	/**
	 * 找出新增的表
	 *
	 * @param dataSourceDto 数据源信息
	 * @param schemaName 模式名
	 * @return List<TableEntity>
	 */
	private List<TableEntity> getNewTables(DataSourceDto dataSourceDto, String schemaName) {

		List<TableEntity> tables = targetTableService.getTable(dataSourceDto, schemaName);
		List<String> tableName = tables.stream().map(TableEntity::getTabName).collect(Collectors.toList());

		//查询已存在的表
		List<TableEntity> existsTable = tableService.getTables(dataSourceDto.getId(), schemaName,
			tableName.toArray(new String[tableName.size()]));
		if (existsTable.isEmpty()) {
			return tables;
		}

		//新增的表
		List<TableEntity> newTables = tables.stream()
			.filter(d -> !existsTable.stream().anyMatch(t -> t.getTabName().equals(d.getTabName()))).collect(Collectors.toList());
		return newTables;
	}


	/**
	 * 找出新增的表
	 *
	 * @param dataSourceDto 数据源信息
	 * @param schemaName 模式名
	 * @param tableName 表名称
	 * @return List<TableEntity>
	 */
	private List<TableEntity> getNewTables(DataSourceDto dataSourceDto, String schemaName, String tableName) {
		TableEntity tableEntity = targetTableService.getTable(dataSourceDto, schemaName, tableName);
		//查询已存在的表
		List<TableEntity> existsTable = tableService.getTables(dataSourceDto.getId(), schemaName, new String[]{tableName});
		if (existsTable.isEmpty()) {
			return Lists.newArrayList(tableEntity);
		}
		return Lists.newArrayList();
	}

	/**
	 * 找出新增的表
	 *
	 * @param dataSourceDto 数据源信息
	 * @param schemaName 模式名
	 * @param tableName 表名称列表
	 * @return List<TableEntity>
	 */
	private List<TableEntity> getNewTables(DataSourceDto dataSourceDto, String schemaName, String[] tableName) {

		List<TableEntity> tables = targetTableService.getTable(dataSourceDto, schemaName, tableName);
		//查询已存在的表
		List<TableEntity> existsTable = tableService.getTables(dataSourceDto.getId(), schemaName, tableName);
		if (existsTable.isEmpty()) {
			return tables;
		}
		//忽略已存在的表
		//新增的表
		List<TableEntity> newTables = tables.stream()
			.filter(d -> !existsTable.stream().anyMatch(t -> t.getTabName().equals(d.getTabName()))).collect(Collectors.toList());
		return newTables;
	}

	/**
	 * 获取目标表字段信息
	 *
	 * @param dataSourceDto 数据源信息
	 * @param schemaName 模式名
	 * @param newTables 新增的表
	 * @return List<IColumnEntity>
	 */
	private List<IColumnEntity> getColumns(DataSourceDto dataSourceDto, String schemaName, List<TableEntity> newTables) {
		IColumnService tagColumnService = columnUtil.getColumnService(dataSourceDto);
		List<String> tableNames = newTables.stream().map(TableEntity::getTabName).collect(Collectors.toList());
		return tagColumnService.getTableColumn(dataSourceDto, schemaName, tableNames);

	}

	/**
	 * 封装字段对象，用于保存到数据库
	 *
	 * @param columns 字段列表
	 * @param dbTables 已保存的表
	 */
	private List<ColumnEntity> getDataTableColumnEntityList(List<IColumnEntity> columns, List<TableEntity> dbTables) {
		List<ColumnEntity> columnEntityList = Lists.newArrayList();
		columns.stream().forEach(columnEntity -> {
			ColumnEntity dataTableColumnEntity = new ColumnEntity();
			dataTableColumnEntity.setTabName(columnEntity.getTabName());
			dataTableColumnEntity.setColDecimal(columnEntity.getColDecimal());
			dataTableColumnEntity.setDataType(columnEntity.getDataType());
			dataTableColumnEntity.setColLength(columnEntity.getColLength());
			dataTableColumnEntity.setColId(columnEntity.getColId());
			dataTableColumnEntity.setColName(columnEntity.getColName());

			//已保存的tabId标识
			TableEntity datatableEntity = dbTables.stream().filter(d -> d.getTabName().equals(columnEntity.getTabName())).findFirst()
				.orElse(null);

			dataTableColumnEntity.setTabId(datatableEntity.getId());
			columnEntityList.add(dataTableColumnEntity);
		});
		return columnEntityList;
	}

	/**
	 * 保存字段信息
	 *
	 * @param dataSourceDto 数据源信息
	 * @param schemaName 模式名
	 * @param newTables 新增的表
	 * @param dbTables 已保存的表
	 */
	private void saveColumns(DataSourceDto dataSourceDto, String schemaName, List<TableEntity> newTables, List<TableEntity> dbTables) {
		List<IColumnEntity> columns = getColumns(dataSourceDto, schemaName, newTables);
		List<ColumnEntity> columnEntityList = getDataTableColumnEntityList(columns, dbTables);
		//保存所有字段信息
		columnRepository.saveAll(columnEntityList);
	}


	@Transactional
	public void getAndSaveColumnFromTagDBBySchema(DataSourceDto dataSourceDto, String schemaName) {
		//新增的表
		List<TableEntity> newTables = getNewTables(dataSourceDto, schemaName);
		//保存新增表信息
		List<TableEntity> dbTables = tableService.saveTables(newTables);
		//保存字段信息
		saveColumns(dataSourceDto, schemaName, dbTables, newTables);
	}

	@Transactional
	public void getAndSaveColumnFromTagDBByTableName(DataSourceDto dataSourceDto, String schemaName, String tableName) {

		//新增的表
		List<TableEntity> newTables = getNewTables(dataSourceDto, schemaName, tableName);
		//保存新增表信息
		List<TableEntity> dbTables = tableService.saveTables(newTables);
		//保存字段信息
		saveColumns(dataSourceDto, schemaName, dbTables, newTables);
	}

	@Transactional
	public void getAndSaveColumnFromTagDBByTableName(DataSourceDto dataSourceDto, String schemaName, String[] tableNames) {

		//新增的表
		List<TableEntity> newTables = getNewTables(dataSourceDto, schemaName, tableNames);
		//保存新增表信息
		List<TableEntity> dbTables = tableService.saveTables(newTables);
		//保存字段信息
		saveColumns(dataSourceDto, schemaName, dbTables, newTables);
	}

	//todo
	public void modifiedColumnFromTagDBByTableName(DataSourceDto dataSourceDto, String schemaName, String tableName) {

		TableEntity tableEntity = tableService.getTable(dataSourceDto.getId(),schemaName,tableName);
		List<IColumnEntity> columnEntityList = getColumns(dataSourceDto,schemaName,Lists.newArrayList(tableEntity));

		TableEntity tagTableEntity = targetTableService.getTable(dataSourceDto, schemaName, tableName);
	}

	//todo
	public void modifiedColumnFromTagDBByTableId() {

	}

	//todo
	public void modifiedColumnFromTagDBBySchema() {

	}
}
