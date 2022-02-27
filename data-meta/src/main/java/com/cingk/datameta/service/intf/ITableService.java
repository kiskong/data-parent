package com.cingk.datameta.service.intf;

import java.util.List;

import com.cingk.datameta.model.entity.TableEntity;

public interface ITableService {

	List<TableEntity> saveTables(List<TableEntity> tableEntityList);

	void deleteTable(Integer dataSourceId, String schemaName, String tableName);

	void deleteTable(Integer dataSourceId, String schemaName, Integer tabId);

	void deleteTable(TableEntity tableEntity);

	void updateTable(TableEntity tableEntity);


	List<TableEntity> getTables(Integer dataSourceId, String schemaName);

	List<TableEntity> getTables(Integer dataSourceId, String[] schemaName);

	List<TableEntity> getTables(Integer dataSourceId, String schemaName, String[] tableName);

	List<TableEntity> getTables(Integer dataSourceId, String schemaName, Integer[] tabId);

	TableEntity getTable(Integer dataSourceId, String schemaName, String tableName);

	TableEntity getTable(Integer dataSourceId, String schemaName, Integer tabId);

//	//取其他数据源
//
//	List<DataTableEntity> getTagTables(Integer dataSourceId, String schemaName);
//
//	List<DataTableEntity> getTagTables(Integer dataSourceId, String[] schemaName);
//
//	List<DataTableEntity> getTagTables(Integer dataSourceId, String schemaName, String[] tableName);
//
//	DataTableEntity getTagTable(Integer dataSourceId, String schemaName, String tableName);


}
