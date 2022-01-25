package com.cingk.datameta.service.intf;

import com.cingk.datameta.model.InterfaceEntity;
import com.cingk.datameta.model.entity.DatabaseTableEntity;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface ITableService {

    DatabaseTableEntity getDatabaseTable(InterfaceEntity databaseSourceDto,String tableName);
    void delDatabaseTable(InterfaceEntity databaseTableDto);
    void updDatabaseTable(InterfaceEntity databaseTableDto);
    List<DatabaseTableEntity> getAllTables(InterfaceEntity databaseSourceDto) throws SQLException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException;
    List<DatabaseTableEntity> getAllTablesWithSchema(InterfaceEntity databaseSourceDto,String schema) throws SQLException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException;

}
