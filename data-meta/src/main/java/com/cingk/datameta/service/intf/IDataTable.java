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
    List<DataTableEntity> getAllTables(DataSourceDto dataSourceDto) ;
    List<DataTableEntity> saveAllTables(List<DataTableEntity> dataTableEntityList) ;
    List<DataTableEntity> getAllTables(DataSourceDto dataSourceDto, String sql, String resultClassName) ;
    List<DataTableEntity> getAllTablesWithSchema(DataSourceDto dataSourceDto, String schema) ;
    List<DataTableEntity> getAllTablesWithSchema(DataSourceDto dataSourceDto, String schema, String sql, String resultClassName);

    Integer getTotalDataTableCount(DataSourceDto dataSourceDto, String schema);
//    Integer getTotalDataTableCount(Integer dataSourceId, String schema);
//    Integer getTotalDataTableCount(String dataSourceName, String schema);


}
