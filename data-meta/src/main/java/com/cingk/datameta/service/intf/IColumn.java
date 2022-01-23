package com.cingk.datameta.service.intf;

import com.cingk.datameta.model.InterfaceEntity;
import com.cingk.datameta.model.entity.DatabaseTableColumnEntity;

import java.util.List;

public interface IColumn  {
    DatabaseTableColumnEntity getDatabaseTableColumn(InterfaceEntity DatabaseTableDto);
    DatabaseTableColumnEntity addDatabaseTableColumn(InterfaceEntity databaseTableColumnEntity);
    List<DatabaseTableColumnEntity> addDatabaseTableColumn(List<DatabaseTableColumnEntity> databaseTableColumnEntityList);
    void delDatabaseTableColumn(InterfaceEntity databaseTableColumnEntity);
    DatabaseTableColumnEntity updDatabaseTableColumn(InterfaceEntity databaseTableColumnEntity);
    List<DatabaseTableColumnEntity> getAllTableColumn(List<InterfaceEntity> DatabaseTableDtoList);
    List<DatabaseTableColumnEntity> getAllTableColumn(InterfaceEntity DatabaseSourceDto);
}
