package com.cingk.datameta.service.impl;

import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.entity.DatabaseTableEntity;
import com.cingk.datameta.model.entity.MysqlTableEntity;
import com.cingk.datameta.service.intf.ITableService;
import com.cingk.datameta.utils.DatabaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Service
public class MysqlTableService implements ITableService {

    private static final String QUERY_ALL_TABLES = "select * from information_schema.tables";

    private static final String QUERY_TABLES_WITH_SCHEMA = QUERY_ALL_TABLES + " where table_schema='%s'";


    @Override
    public DatabaseTableEntity getDatabaseTable(DatabaseSourceDto databaseSourceDto, String tableName) {
        return null;
    }


    @Override
    public void delDatabaseTable(DatabaseSourceDto databaseTableDto) {

    }

    @Override
    public void updDatabaseTable(DatabaseSourceDto databaseTableDto) {

    }
    @Autowired
    private DatabaseUtil databaseUtil;

    @Override
    public List<DatabaseTableEntity> getAllTables(DatabaseSourceDto databaseSourceDto)  {
        try {
            return databaseUtil.getDataTableEntityList(databaseSourceDto,QUERY_ALL_TABLES,MysqlTableEntity.class.getName());
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DatabaseTableEntity> getAllTablesWithSchema(DatabaseSourceDto databaseSourceDto,String schema) {
        String sql = String.format(QUERY_TABLES_WITH_SCHEMA,schema);
        try {
            return databaseUtil.getDataTableEntityList(databaseSourceDto,sql,MysqlTableEntity.class.getName());
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
