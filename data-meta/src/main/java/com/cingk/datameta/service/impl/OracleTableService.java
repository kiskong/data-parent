package com.cingk.datameta.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.entity.DatabaseTableEntity;
import com.cingk.datameta.model.entity.OracleTableEntity;

@Service
public class OracleTableService extends DatabaseTableService {

    private static final String[] DB_SYS_SCHEMA = {"ORDSYS", "SYSMAN", "APPQOSSYS", "XDB", "ORDDATA",
            "SYS", "WMSYS", "SYSTEM", "MDSYS", "OUTLN",
            "CTXSYS", "OLAPSYS", "OWBSYS", "EXFSYS", "APEX_030200",
            "SCOTT", "DBSNMP", "FLOWS_FILES"};
    private static final String QUERY_CONDITION = StrUtil.join(StrUtil.COMMA,
        StrUtil.wrapAllWithPair(SINGLE_QUOTE, DB_SYS_SCHEMA));
    //查询所有表
    private static final String QUERY_ALL_TABLES = "select * from ALL_TABLES";
    //查询非系统表
    private static final String QUERY_ALL_TAB_UNSYS = QUERY_ALL_TABLES + " where OWNER not in (%s)";
    //查询指定schema下的表
    private static final String QUERY_TABLES_WITH_SCHEMA = QUERY_ALL_TABLES + " where owner='%s'";
    //查询Sql结果存储对象
    private static final String JDBC_RESULT_CLASS_NAME = OracleTableEntity.class.getName();

    public List<DatabaseTableEntity> getAllTables(DatabaseSourceDto databaseSourceDto) {
        String sql = String.format(QUERY_ALL_TAB_UNSYS, QUERY_CONDITION);
        return super.getAllTables(databaseSourceDto,sql,JDBC_RESULT_CLASS_NAME);
    }

    public List<DatabaseTableEntity> getAllTablesWithSchema(DatabaseSourceDto databaseSourceDto, String schema) {
        String sql = String.format(QUERY_TABLES_WITH_SCHEMA, QUERY_CONDITION, schema);
        return super.getAllTablesWithSchema(databaseSourceDto,schema,sql,JDBC_RESULT_CLASS_NAME);
    }

}
