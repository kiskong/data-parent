package com.cingk.datameta.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.collection.ListUtil;
import com.cingk.datameta.model.entity.DataTableColumnEntity;
import com.cingk.datameta.model.entity.DataTableEntity;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.DataTableDto;
import com.cingk.datameta.model.entity.MysqlColumnEntity;
import com.cingk.datameta.utils.StrUtil;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-01-30
 */
@Service
public class MysqlTableColumnService extends DataTableColumnService {

    public static final String QUERY_COLUMN = "select * from information_schema.columns where table_schema='%s' and table_name='%s'";
    public static final String BATCH_QUERY_COLUMN = "select * from information_schema.columns where table_schema='%s' and table_name in ('%s')";

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

    @Override
    public List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, String[] tableNames) {
        String condition = String.join(StrUtil.SQL_COL_QUOTE,tableNames);
        String sql = String.format(BATCH_QUERY_COLUMN,condition);
        DataTableDto dataTableDto = new DataTableDto();
        dataTableDto.setSchemaName(schemaName);
        dataTableDto.setDatabaseSourceDto(dataSourceDto);
        return super.getTableColumn(dataTableDto, sql, MysqlColumnEntity.class);
    }

    @Override
    public List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, List<DataTableEntity> tableNames) {
        List<String> tableNameList = tableNames.stream().map(DataTableEntity::getTabName).collect(Collectors.toList());
        String[] tableNameArray = tableNameList.toArray(new String[tableNameList.size()]);
        List<IDataTableColumnEntity> dataTableColumnEntities = this.getTableColumn(dataSourceDto,schemaName,tableNameArray);

        List<IDataTableColumnEntity> dataTableColumnEntityList = Lists.newArrayList();
        dataTableColumnEntities.stream().forEach(iDataTableColumnEntity -> {
            DataTableColumnEntity dataTableColumnEntity = new DataTableColumnEntity();
            BeanUtils.copyProperties(iDataTableColumnEntity, dataTableColumnEntity);

            DataTableEntity localDataTableEntity =
            tableNames.stream()
                    .filter(tableEntity -> tableEntity.getTabName().equals(dataTableColumnEntity.getTabName()))
                    .findFirst()
                    .orElse(null);

            dataTableColumnEntity.setTabId(localDataTableEntity.getId());
            dataTableColumnEntityList.add(dataTableColumnEntity);
        });
        return dataTableColumnEntityList;
    }
}
