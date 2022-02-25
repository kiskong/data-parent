package com.cingk.datameta.service.intf;

import java.util.List;

import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.DataTableDto;
import com.cingk.datameta.model.entity.DataTableColumnEntity;
import com.cingk.datameta.model.entity.DataTableEntity;

public interface IDataTableColumn extends IBaseColumnService {


	/**
	 * 获取数据源下的表字段信息
	 * @param dataTableDto 表对象(含数据源信息)
	 * @return List<IDataTableColumnEntity>
	 */
	List<IDataTableColumnEntity> getTableColumn(DataTableDto dataTableDto);

	List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, String tableName);

	List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, String[] tableNames);

	List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, List<DataTableEntity> tableNames);

	List<DataTableColumnEntity> saveAllTableColumn(List<DataTableColumnEntity> dataTableEntityList);

	List<DataTableColumnEntity> saveAllTableColumnNotExists(List<DataTableColumnEntity> dataTableEntityList);


	List<DataTableColumnEntity> getLocalTableColumn(List<DataTableColumnEntity> dataTableColumnEntityList);

}
