package com.cingk.datameta.service.impl;

import cn.hutool.core.util.StrUtil;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public DatabaseTableEntity getDatabaseTable(InterfaceEntity databaseSourceDto, String tableName) {
        return null;
    }


    @Override
    public void delDatabaseTable(InterfaceEntity databaseTableDto) {

    }

    @Override
    public void updDatabaseTable(InterfaceEntity databaseTableDto) {

    }

    @Override
    public List<DatabaseTableEntity> getAllTables(InterfaceEntity databaseSourceDto) throws SQLException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        String sql = String.format(QUERY_ALL_TABLES, QUERY_CONDITION);
        return databaseUtil.getDataTableEntityList(databaseSourceDto, sql,OracleTableEntity.class.getName());
    }


    @Override
    public List<DatabaseTableEntity> getAllTablesWithSchema(InterfaceEntity databaseSourceDto, String schema)
            throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, ClassNotFoundException {
        String sql = String.format(QUERY_TABLES_WITH_SCHEMA, QUERY_CONDITION, schema);
        return databaseUtil.getDataTableEntityList(databaseSourceDto, sql,OracleTableEntity.class.getName());
    }



}
