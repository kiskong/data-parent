package com.cingk.datameta.service.intf;

import com.cingk.datameta.model.InterfaceEntity;
import com.cingk.datameta.model.entity.DatabaseTableEntity;

import java.util.List;

public interface ITable{

    DatabaseTableEntity getDatabaseTable(InterfaceEntity databaseSourceDto,String tableName);
    DatabaseTableEntity getDatabaseTable(InterfaceEntity databaseTableDto);
    void delDatabaseTable(InterfaceEntity databaseTableDto);
    void updDatabaseTable(InterfaceEntity databaseTableDto);
    List<DatabaseTableEntity> getAllTables(InterfaceEntity databaseSourceDto);

}
