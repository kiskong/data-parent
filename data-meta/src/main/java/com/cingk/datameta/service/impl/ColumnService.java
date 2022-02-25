package com.cingk.datameta.service.impl;

import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.DataTableDto;
import com.cingk.datameta.model.entity.DataTableColumnEntity;
import com.cingk.datameta.service.intf.IColumnService;

import java.util.List;

public class ColumnService implements IColumnService{
    @Override
    public List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String sql, Class resultClass) {
        return null;
    }

    @Override
    public List<IDataTableColumnEntity> getTableColumn(DataTableDto dataTableDto, String sql, Class resultClass) {
        return null;
    }

    @Override
    public List<IDataTableColumnEntity> getSrcTableColumn(DataSourceDto dataSourceDto,String schemaName, String tableName) {
        return null;
    }

    @Override
    public List<IDataTableColumnEntity> getSrcTableColumn(DataSourceDto dataSourceDto, String schemaName) {
        return null;
    }

    @Override
    public List<IDataTableColumnEntity> getSrcTableColumn(DataSourceDto dataSourceDto,String schemaName, List<String> tableName) {
        return null;
    }

    @Override
    public List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto,String schemaName, String tableName) {
        return null;
    }

    @Override
    public List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName) {
        return null;
    }

    @Override
    public List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto,String schemaName, List<String> tableName) {
        return null;
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
