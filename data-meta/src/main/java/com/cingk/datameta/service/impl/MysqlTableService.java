package com.cingk.datameta.service.impl;

import com.cingk.datameta.model.IDataTableEntity;
import com.cingk.datameta.model.InterfaceEntity;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.entity.DatabaseTableEntity;
import com.cingk.datameta.model.entity.MysqlTableEntity;
import com.cingk.datameta.model.entity.OracleTableEntity;
import com.cingk.datameta.service.intf.ITableService;
import com.cingk.datameta.utils.DatabaseUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.List;

@Service
public class MysqlTableService implements ITableService {

    private static final String QUERY_ALL_TABLES = "select * from information_schema.tables";

    private static final String QUERY_TABLES_WITH_SCHEMA = QUERY_ALL_TABLES + " where table_schema='%s'";


    @Override
    public DatabaseTableEntity getDatabaseTable(InterfaceEntity databaseSourceDto, String tableName) {
        return null;
    }


    @Override
    public void delDatabaseTable(InterfaceEntity databaseTableDto) {

    }

    @Override
    public void updDatabaseTable(InterfaceEntity databaseTableDto) {

    }
    @Autowired
    private DatabaseUtil databaseUtil;

    @Override
    public List<DatabaseTableEntity> getAllTables(InterfaceEntity databaseSourceDto) throws SQLException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        return getDataTableEntityList(databaseSourceDto,QUERY_ALL_TABLES);
    }

    @Override
    public List<DatabaseTableEntity> getAllTablesWithSchema(InterfaceEntity databaseSourceDto,String schema) throws SQLException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        String sql = String.format(QUERY_TABLES_WITH_SCHEMA,schema);
        return getDataTableEntityList(databaseSourceDto,sql);
    }

    private List<DatabaseTableEntity> getDataTableEntityList(InterfaceEntity databaseSourceDto, String sql) throws SQLException,
            InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        List<IDataTableEntity> dataTableEntityList =
                databaseUtil.getDataTableEntityList(databaseSourceDto, sql, MysqlTableEntity.class);
        List<DatabaseTableEntity> databaseTableEntityList = Lists.newArrayList();
        if (dataTableEntityList == null) {
            return databaseTableEntityList;
        }
        dataTableEntityList.forEach(dataTableEntity -> {
            MysqlTableEntity mysqlTableEntity = (MysqlTableEntity) dataTableEntity;
            DatabaseTableEntity databaseTableEntity = new DatabaseTableEntity();
            databaseTableEntity.setTabName(mysqlTableEntity.getTableName());
            databaseTableEntity.setSchemaName(mysqlTableEntity.getTableSchema());
            databaseTableEntity.setDatabaseSourceId(((DatabaseSourceDto)databaseSourceDto).getId());
            databaseTableEntityList.add(databaseTableEntity);
        });
        return databaseTableEntityList;
    }
}
