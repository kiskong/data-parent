package com.cingk.datameta.service.impl;

import cn.hutool.core.util.StrUtil;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.entity.DatabaseTableEntity;
import com.cingk.datameta.model.entity.OracleTableEntity;
import com.cingk.datameta.service.intf.ITableService;
import com.cingk.datameta.utils.DatabaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Service
public class OracleTableService implements ITableService {

    private static final String[] DB_SCHEMA = {"ORDSYS", "SYSMAN", "APPQOSSYS", "XDB", "ORDDATA",
            "SYS", "WMSYS", "SYSTEM", "MDSYS", "OUTLN",
            "CTXSYS", "OLAPSYS", "OWBSYS", "EXFSYS", "APEX_030200",
            "SCOTT", "DBSNMP", "FLOWS_FILES"};
    private static final String SINGLE_QUOTE = "'";
    private static final String QUERY_CONDITION = StrUtil.join(StrUtil.COMMA, StrUtil.wrapAllWithPair(SINGLE_QUOTE, DB_SCHEMA));
    private static final String QUERY_ALL_TABLES = "select * from ALL_TABLES where OWNER not in (%s)";
    private static final String QUERY_TABLES_WITH_SCHEMA = QUERY_ALL_TABLES + " and owner='%s'";


    @Autowired
    private DatabaseUtil databaseUtil;

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

    @Override
    public List<DatabaseTableEntity> getAllTables(DatabaseSourceDto databaseSourceDto) {
        String sql = String.format(QUERY_ALL_TABLES, QUERY_CONDITION);
        try {
            return databaseUtil.getDataTableEntityList(databaseSourceDto, sql, OracleTableEntity.class.getName());
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<DatabaseTableEntity> getAllTablesWithSchema(DatabaseSourceDto databaseSourceDto, String schema) {
        String sql = String.format(QUERY_TABLES_WITH_SCHEMA, QUERY_CONDITION, schema);
        try {
            return databaseUtil.getDataTableEntityList(databaseSourceDto, sql, OracleTableEntity.class.getName());
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
