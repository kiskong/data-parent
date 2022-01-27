package com.cingk.datameta.service.intf;

import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.entity.DatabaseTableEntity;

import java.util.List;

public interface ITableService {

    DatabaseTableEntity getDatabaseTable(DatabaseSourceDto databaseSourceDto, String tableName);
    void delDatabaseTable(DatabaseSourceDto databaseTableDto);
    void updDatabaseTable(DatabaseSourceDto databaseTableDto);
    List<DatabaseTableEntity> getAllTables(DatabaseSourceDto databaseSourceDto) ;
    List<DatabaseTableEntity> getAllTablesWithSchema(DatabaseSourceDto databaseSourceDto,String schema) ;

}
