package com.cingk.datameta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.entity.DataTableColumnEntity;
import com.cingk.datameta.model.entity.MysqlColumnEntity;
import com.cingk.datameta.service.intf.IColumnService;
import com.cingk.datameta.utils.ColumnUtil;
import com.cingk.datameta.utils.StrUtil;

@Service
public class MysqlColumnService implements IColumnService {

    public ColumnUtil columnUtil;

    @Autowired
    public void setDataTableColumnUtil(ColumnUtil columnUtil) {
        this.columnUtil = columnUtil;
    }

    public static final String QUERY_SQL = "select * from information_schema.columns";
    public static final String QUERY_COLUMN_BY_SCHEMA = QUERY_SQL + " where table_schema='%s'";
    public static final String QUERY_COLUMN_BY_TABLE_NAME = QUERY_COLUMN_BY_SCHEMA + " and table_name='%s'";
    public static final String QUERY_COLUMN_BY_TABLE_NAMES = QUERY_COLUMN_BY_SCHEMA + " and table_name in ('%s')";

    @Override
    public List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName) {
        String sql = String.format(QUERY_COLUMN_BY_SCHEMA, schemaName);
        return columnUtil.getTableColumnEntityList(dataSourceDto, sql, MysqlColumnEntity.class);
    }


    @Override
    public List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, String tableName) {
        String sql = String.format(QUERY_COLUMN_BY_TABLE_NAME, schemaName, tableName);
        return columnUtil.getTableColumnEntityList(dataSourceDto, sql, MysqlColumnEntity.class);
    }

    @Override
    public List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, List<String> tableName) {
        String condition = String.join(StrUtil.SQL_COL_QUOTE, tableName);
        String sql = String.format(QUERY_COLUMN_BY_TABLE_NAMES, condition);
        return columnUtil.getTableColumnEntityList(dataSourceDto, sql, MysqlColumnEntity.class);
    }

    @Override
    public void updateTableColumn(DataTableColumnEntity dataTableColumnEntity) {

    }

    @Override
    public void updateTableColumn(List<DataTableColumnEntity> dataTableColumnEntityList) {

    }

    @Override
    public void saveTableColumn(List<DataTableColumnEntity> dataTableColumnEntityList) {

    }

    @Override
    public void deleteTableColumn(Integer tabId) {

    }

    @Override
    public void deleteTableColumn(DataTableColumnEntity dataTableColumnEntity) {

    }

    @Override
    public void deleteTableColumn(List<DataTableColumnEntity> dataTableColumnEntityList) {

    }

}
