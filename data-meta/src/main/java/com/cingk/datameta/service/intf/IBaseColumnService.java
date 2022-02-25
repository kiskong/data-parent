package com.cingk.datameta.service.intf;

import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.DataTableDto;

import java.util.List;

public interface IBaseColumnService {
    /**
     * 获取数据源下的表字段信息
     * @param dataSourceDto 数据源
     * @param sql 查询字段的SQL
     * @param resultClass 返回结果的封装对象
     * @return List<IDataTableColumnEntity>
     */
    List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String sql, Class resultClass);

    /**
     * 获取数据源下的表字段信息
     * @param dataTableDto 表对象(含数据源信息)
     * @param sql 查询字段的SQL
     * @param resultClass 返回结果的封装对象
     * @return List<IDataTableColumnEntity>
     */
    List<IDataTableColumnEntity> getTableColumn(DataTableDto dataTableDto, String sql, Class resultClass);
}
