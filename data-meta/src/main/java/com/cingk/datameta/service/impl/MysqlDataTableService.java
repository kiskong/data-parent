package com.cingk.datameta.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.entity.DataTableEntity;
import com.cingk.datameta.model.entity.MysqlTableEntity;

@Service
public class MysqlDataTableService extends DataTableService {

    private static final String[] DB_SYS_SCHEMA = {"information_schema", "mysql", "performance_schema", "sys"};
    private static final String QUERY_CONDITION = StrUtil.join(StrUtil.COMMA,
        StrUtil.wrapAllWithPair(SINGLE_QUOTE, DB_SYS_SCHEMA));
    //查询所有表
    private static final String QUERY_ALL_TABLES = "select * from information_schema.tables";
    //查询非系统表
    private static final String QUERY_ALL_TAB_UNSYS = QUERY_ALL_TABLES + " where table_schema not in (%s)";
    //查询指定schema下的表
    private static final String QUERY_TABLES_WITH_SCHEMA = QUERY_ALL_TABLES + " where table_schema='%s'";
    //查询Sql结果存储对象
    private static final String JDBC_RESULT_CLASS_NAME = MysqlTableEntity.class.getName();

    private static final String QUERY_TABLE_COUNT = "select count(1) from information_schema.tables";
    private static final String QUERY_TABLE_COUNT_WITH_SCHEMA = QUERY_TABLE_COUNT + " where table_schema='%s'";


    @Override
    public List<DataTableEntity> getAllTables(DataSourceDto dataSourceDto)  {
        String sql = String.format(QUERY_ALL_TAB_UNSYS, QUERY_CONDITION);
        return super.getAllTables(dataSourceDto,sql,JDBC_RESULT_CLASS_NAME);
    }


    public List<DataTableEntity> getAllTablesWithSchema(DataSourceDto dataSourceDto, String schema) {
        String sql = String.format(QUERY_TABLES_WITH_SCHEMA,QUERY_CONDITION,schema);
        return super.getAllTablesWithSchema(dataSourceDto,schema,sql,JDBC_RESULT_CLASS_NAME);
    }

    public Integer getTotalDataTableCount(DataSourceDto dataSource,String schema){
        String sql = String.format(QUERY_TABLE_COUNT_WITH_SCHEMA,schema);
        return super.getTotalDataTableCount(dataSource, sql);
    }
}
