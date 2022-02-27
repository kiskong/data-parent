package com.cingk.datameta.service.intf;

import java.util.List;

import com.cingk.datameta.model.IColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.entity.ColumnEntity;

public interface IColumnService {



    /**
     * 获取本地连接数据源多个表的字段信息
     *
     * @param dataSourceDto 数据源
     * @param schemaName    模式名
     * @param tableName     表名称
     * @return List<IDataTableColumnEntity>
     */
    List<IColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, String tableName);

    /**
     * 获取本地连接数数据源模式名下所有表的字段信息
     *
     * @param dataSourceDto 数据源
     * @param schemaName    模式名
     * @return List<IDataTableColumnEntity>
     */
    List<IColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName);

    /**
     * 获取本地连接数据源多个表的字段信息
     *
     * @param dataSourceDto 数据源
     * @param schemaName    模式名
     * @param tableName     表名称
     * @return List<IDataTableColumnEntity>
     */
    List<IColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, List<String> tableName);

    /**
     * 更新某个字段信息
     *
     * @param columnEntity 表字段对象
     */
    void updateTableColumn(ColumnEntity columnEntity);

    /**
     * 更新多个字段信息
     *
     * @param columnEntityList 表字段对象
     */
    void updateTableColumn(List<ColumnEntity> columnEntityList);

    /**
     * 保存表字段信息
     *
     * @param columnEntityList 表字段对象
     */
    void saveTableColumn(List<ColumnEntity> columnEntityList);

    /**
     * 删除某表所有字段
     *
     * @param tabId 表标识
     */
    void deleteTableColumn(Integer tabId);

    /**
     * 删除某个字段
     *
     * @param columnEntity 表字段对象
     */
    void deleteTableColumn(ColumnEntity columnEntity);

    /**
     * 删除某些字段
     *
     * @param columnEntityList 表字段对象
     */
    void deleteTableColumn(List<ColumnEntity> columnEntityList);
}
