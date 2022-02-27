package com.cingk.datameta.service.intf;

import java.util.List;

import com.cingk.datameta.model.entity.DataTableEntity;

public interface ITableService {

	List<DataTableEntity> saveTables(List<DataTableEntity> dataTableEntityList);

	void deleteTable(Integer dataSourceId, String schemaName, String tableName);

	void deleteTable(Integer dataSourceId, String schemaName, Integer tabId);

	void deleteTable(DataTableEntity dataTableEntity);

	void updateTable(DataTableEntity dataTableEntity);


	List<DataTableEntity> getTables(Integer dataSourceId, String schemaName);

	List<DataTableEntity> getTables(Integer dataSourceId, String[] schemaName);

	List<DataTableEntity> getTables(Integer dataSourceId, String schemaName, String[] tableName);

	List<DataTableEntity> getTables(Integer dataSourceId, String schemaName, Integer[] tabId);

	DataTableEntity getTable(Integer dataSourceId, String schemaName, String tableName);

	DataTableEntity getTable(Integer dataSourceId, String schemaName, Integer tabId);

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
