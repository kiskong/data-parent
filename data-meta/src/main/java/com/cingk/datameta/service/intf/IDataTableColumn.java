package com.cingk.datameta.service.intf;

import java.util.List;

import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.DataTableDto;
import com.cingk.datameta.model.entity.DataTableColumnEntity;
import com.cingk.datameta.model.entity.DataTableEntity;

public interface IDataTableColumn {
    List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String sql, Class resultClass);
    List<IDataTableColumnEntity> getTableColumn(DataTableDto dataTableDto, String sql, Class resultClass);
    List<IDataTableColumnEntity> getTableColumn(DataTableDto dataTableDto);
    List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto,String schemaName, String tableName);

    List<DataTableColumnEntity> saveAllTableColumn(List<DataTableColumnEntity> dataTableEntityList) ;

}
