package com.cingk.datameta.service.intf;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.dto.DatabaseTableDto;
import com.cingk.datameta.model.entity.DatabaseTableEntity;

@Component
public interface ITableService {

    DatabaseTableEntity getDatabaseTable(DatabaseSourceDto databaseSourceDto, String tableName);
    void delDatabaseTable(DatabaseTableDto databaseTableDto);
    void updDatabaseTable(DatabaseTableDto databaseTableDto);
    DatabaseTableEntity save(DatabaseTableDto databaseTableDto);
    List<DatabaseTableEntity> getAllTables(DatabaseSourceDto databaseSourceDto) ;
    List<DatabaseTableEntity> saveAllTables(List<DatabaseTableEntity> databaseTableEntityList) ;
    List<DatabaseTableEntity> getAllTables(DatabaseSourceDto databaseSourceDto,String sql,String resultClassName) ;
    List<DatabaseTableEntity> getAllTablesWithSchema(DatabaseSourceDto databaseSourceDto,String schema) ;
    List<DatabaseTableEntity> getAllTablesWithSchema(DatabaseSourceDto databaseSourceDto,String schema,String sql,String resultClassName) ;


}
