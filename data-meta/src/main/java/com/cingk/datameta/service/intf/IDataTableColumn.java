package com.cingk.datameta.service.intf;

import java.util.List;

import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.DataTableDto;
import com.cingk.datameta.model.entity.DataTableColumnEntity;

public interface IDataTableColumn {

	List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String sql, Class resultClass);

	List<IDataTableColumnEntity> getTableColumn(DataTableDto dataTableDto, String sql, Class resultClass);

	List<IDataTableColumnEntity> getTableColumn(DataTableDto dataTableDto);

	List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, String tableName);

	List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, String[] tableNames);

	List<DataTableColumnEntity> saveAllTableColumn(List<DataTableColumnEntity> dataTableEntityList);

	List<DataTableColumnEntity> saveAllTableColumnNotExists(List<DataTableColumnEntity> dataTableEntityList);


	List<DataTableColumnEntity> getLocalTableColumn(List<DataTableColumnEntity> dataTableColumnEntityList);

}
