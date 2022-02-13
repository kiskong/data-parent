package com.cingk.datameta.service.intf;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.DataTableDto;
import com.cingk.datameta.model.entity.DataTableEntity;

@Component
public interface IDataTable {

	DataTableEntity getDatabaseTable(DataSourceDto dataSourceDto, String tableName);

	void delDatabaseTable(DataTableDto dataTableDto);

	void updDatabaseTable(DataTableDto dataTableDto);

	DataTableEntity save(DataTableDto dataTableDto);

	List<DataTableEntity> getSrcAllTables(DataSourceDto dataSourceDto);

	List<DataTableEntity> getLocalAllTables(Integer dataSourceId, String[] tableNames);

	List<DataTableEntity> getLocalAllTables(Integer dataSourceId, String schemaName, String[] tableNames);

	List<DataTableEntity> saveAllTables(List<DataTableEntity> dataTableEntityList);

	List<DataTableEntity> getSrcAllTables(DataSourceDto dataSourceDto, String sql, String resultClassName);

	List<DataTableEntity> getSrcAllTablesWithSchema(DataSourceDto dataSourceDto, String schema);

	List<DataTableEntity> getSrcAllTablesWithSchema(DataSourceDto dataSourceDto, String schema, String sql, String resultClassName);

	Integer getSrcTotalDataTableCount(DataSourceDto dataSourceDto, String schema);


}
