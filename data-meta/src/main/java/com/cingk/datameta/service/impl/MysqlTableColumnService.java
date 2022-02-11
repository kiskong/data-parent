package com.cingk.datameta.service.impl;

import java.util.List;

import com.cingk.datameta.model.dto.DataSourceDto;
import org.springframework.stereotype.Service;

import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DataTableDto;
import com.cingk.datameta.model.entity.MysqlColumnEntity;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-01-30
 */
@Service
public class MysqlTableColumnService extends DataTableColumnService {

    public static final String QUERY_COLUMN = "select * from information_schema.columns where table_schema='%s' and table_name='%s'";

    /**
     * 根据schema，tableName，datasource 获取对应表字段
     *
     * @param dataTableDto {@link DataTableDto}
     * @return List<IDataTableColumnEntity> {@link com.cingk.datameta.model.IDataTableColumnEntity}
     */
    @Override
    public List<IDataTableColumnEntity> getTableColumn(DataTableDto dataTableDto) {
        String tableName = dataTableDto.getTabName();
        String tabSchema = dataTableDto.getSchemaName();
        String sql = String.format(QUERY_COLUMN, tabSchema, tableName);
        return super.getTableColumn(dataTableDto, sql, MysqlColumnEntity.class);
    }

    @Override
    public List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, String tableName) {
        DataTableDto dataTableDto = new DataTableDto();
        dataTableDto.setTabName(tableName);
        dataTableDto.setSchemaName(schemaName);
        dataTableDto.setDatabaseSourceDto(dataSourceDto);
        return this.getTableColumn(dataTableDto);
    }
}
