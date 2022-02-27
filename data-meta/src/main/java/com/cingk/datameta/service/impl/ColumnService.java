package com.cingk.datameta.service.impl;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.NumberUtil;
import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.entity.DataTableColumnEntity;
import com.cingk.datameta.model.entity.DataTableEntity;
import com.cingk.datameta.service.intf.IColumnService;
import com.cingk.datameta.service.intf.ITableService;
import com.cingk.datameta.utils.ColumnUtil;
import com.cingk.datameta.utils.TableUtil;

@Service
public class ColumnService {

	private TableUtil tableUtil;
	private ColumnUtil columnUtil;
	//本地数据源的数据表服务实现
	private TableService tableService;

	@Autowired
	public void setDataTableUtil(TableUtil tableUtil) {
		this.tableUtil = tableUtil;
	}

	@Autowired
	public void setDataTableColumnUtil(ColumnUtil columnUtil) {
		this.columnUtil = columnUtil;
	}

	@Autowired
	public void setTableService(TableService tableService) {
		this.tableService = tableService;
	}

	private List<DataTableEntity> getNewTables(DataSourceDto dataSourceDto, String schemaName) {

		Integer dataSourceId = dataSourceDto.getId();
		ITableService tagTableService = tableUtil.getTableService(dataSourceDto);
		List<DataTableEntity> tables = tagTableService.getTables(dataSourceId, schemaName);

		List<String> tableName = tables.stream().map(DataTableEntity::getTabName).collect(Collectors.toList());

		//查询已存在的表
		List<DataTableEntity> existsTable = tableService.getTables(dataSourceId, schemaName,
			tableName.toArray(new String[tableName.size()]));

		//新增的表
		List<DataTableEntity> newTables = existsTable
			.stream()
			.filter(d ->
				!tables.stream().anyMatch(t -> NumberUtil.equals(t.getId(), d.getId())))
			.collect(Collectors.toList());
		return newTables;
	}

	private List<DataTableEntity> getNewTables(DataSourceDto dataSourceDto, String schemaName, String tableName) {
		Integer dataSourceId = dataSourceDto.getId();
		ITableService tagTableService = tableUtil.getTableService(dataSourceDto);
		DataTableEntity tableEntity = tagTableService.getTable(dataSourceId, schemaName, tableName);
		//查询已存在的表
		List<DataTableEntity> existsTable = tableService.getTables(dataSourceId, schemaName, new String[]{tableName});
		//新增的表
		List<DataTableEntity> newTables = existsTable
			.stream()
			.filter(d -> !NumberUtil.equals(d.getId(), tableEntity.getId()))
			.collect(Collectors.toList());
		return newTables;
	}

	private List<DataTableEntity> getNewTables(DataSourceDto dataSourceDto, String schemaName, String[] tableName) {

		Integer dataSourceId = dataSourceDto.getId();
		ITableService tagTableService = tableUtil.getTableService(dataSourceDto);
		List<DataTableEntity> tables = tagTableService.getTables(dataSourceId, schemaName, tableName);

		//查询已存在的表
		List<DataTableEntity> existsTable = tableService.getTables(dataSourceId, schemaName, tableName);

		//忽略已存在的表
		//新增的表
		List<DataTableEntity> newTables = existsTable
			.stream()
			.filter(d ->
				!tables.stream().anyMatch(t -> NumberUtil.equals(t.getId(), d.getId())))
			.collect(Collectors.toList());
		return newTables;
	}


	private List<IDataTableColumnEntity> getColumns(IColumnService columnService, DataSourceDto dataSourceDto, String schemaName,
		List<DataTableEntity> newTables) {
		//查询所有表字段信息
		List<String> tableNames = newTables.stream().map(DataTableEntity::getTabName).collect(Collectors.toList());
		return columnService.getTableColumn(dataSourceDto, schemaName, tableNames);

	}

	private List<DataTableColumnEntity> getDataTableColumnEntityList(List<IDataTableColumnEntity> columns, List<DataTableEntity> dbTables) {
		List<DataTableColumnEntity> columnEntityList = Lists.newArrayList();
		columns.stream().forEach(columnEntity -> {
			DataTableColumnEntity dataTableColumnEntity = new DataTableColumnEntity();
			dataTableColumnEntity.setTabName(columnEntity.getTabName());
			dataTableColumnEntity.setColDecimal(columnEntity.getColDecimal());
			dataTableColumnEntity.setColType(columnEntity.getColType());
			dataTableColumnEntity.setColLength(columnEntity.getColLength());
			dataTableColumnEntity.setColId(columnEntity.getColId());
			dataTableColumnEntity.setColName(columnEntity.getColName());

			//已保存的tabId标识
			DataTableEntity datatableEntity =
				dbTables.stream().filter(d -> d.getTabName().equals(columnEntity.getTabName()))
					.findFirst()
					.orElse(null);

			dataTableColumnEntity.setTabId(datatableEntity.getId());
			columnEntityList.add(dataTableColumnEntity);
		});
		return columnEntityList;
	}

	private void saveColumns(DataSourceDto dataSourceDto, String schemaName, List<DataTableEntity> newTables,
		List<DataTableEntity> dbTables) {
		IColumnService columnService = columnUtil.getColumnService(dataSourceDto);
		List<IDataTableColumnEntity> columns = getColumns(columnService, dataSourceDto, schemaName, newTables);
		List<DataTableColumnEntity> columnEntityList = getDataTableColumnEntityList(columns, dbTables);
		//保存所有字段信息
		columnService.saveTableColumn(columnEntityList);
	}


	public void getAndSaveTagDBColumnBySchema(DataSourceDto dataSourceDto, String schemaName) {
		//新增的表
		List<DataTableEntity> newTables = getNewTables(dataSourceDto, schemaName);
		//保存新增表信息
		List<DataTableEntity> dbTables = tableService.saveTables(newTables);
		//保存字段信息
		saveColumns(dataSourceDto, schemaName, dbTables, newTables);
	}


	public void getAndSaveTagDBColumnByTableName(DataSourceDto dataSourceDto, String schemaName, String tableName) {

		//新增的表
		List<DataTableEntity> newTables = getNewTables(dataSourceDto, schemaName, tableName);
		//保存新增表信息
		List<DataTableEntity> dbTables = tableService.saveTables(newTables);
		//保存字段信息
		saveColumns(dataSourceDto, schemaName, dbTables, newTables);
	}

	public void getAndSaveTagDBColumnByTableName(DataSourceDto dataSourceDto, String schemaName, String[] tableNames) {

		//新增的表
		List<DataTableEntity> newTables = getNewTables(dataSourceDto, schemaName, tableNames);
		//保存新增表信息
		List<DataTableEntity> dbTables = tableService.saveTables(newTables);
		//保存字段信息
		saveColumns(dataSourceDto, schemaName, dbTables, newTables);
	}
}
