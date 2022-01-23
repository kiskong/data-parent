package com.cingk.datameta.service.impl;

import com.cingk.datameta.model.InterfaceEntity;
import com.cingk.datameta.model.entity.DatabaseTableEntity;
import com.cingk.datameta.service.intf.ITable;

import java.util.List;

public class OracleTableService implements ITable {
    @Override
    public DatabaseTableEntity getDatabaseTable(InterfaceEntity databaseSourceDto, String tableName) {
        return null;
    }

    @Override
    public DatabaseTableEntity getDatabaseTable(InterfaceEntity databaseTableDto) {
        return null;
    }

    @Override
    public void delDatabaseTable(InterfaceEntity databaseTableDto) {

    }

    @Override
    public void updDatabaseTable(InterfaceEntity databaseTableDto) {

    }

    @Override
    public List<DatabaseTableEntity> getAllTables(InterfaceEntity databaseSourceDto) {
        return null;
    }
}
