package com.cingk.datameta.utils;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.cingk.datameta.model.IColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.ColumnDto;
import com.cingk.datameta.model.dto.TableDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.service.impl.MysqlColumnService;
import com.cingk.datameta.service.intf.IColumnService;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-01-30
 */
@Component
public class ColumnUtil extends DatabaseUtil {


    /**
     * 根据Url获取不同数据库对应的服务
     *
     * @param url database connect url
     * @return database classic
     */
    public String getServiceNameByUrl(String url) {
        boolean isMysql = url.toLowerCase().contains(DB_TYPE_MYSQL);
        if (isMysql) {
            return MysqlColumnService.class.getName();
        }
        boolean isOracle = url.toLowerCase().contains(DB_TYPE_ORACLE);
//        if (isOracle) return OracleTableColumnService.class.getName();
        return "";
    }

    /**
     * 根据数据源信息获取不同数据库对应的服务
     *
     * @param dataSourceDto 数据源连接信息对象
     * @return database classic
     */
    public String getServiceNameByUrl(DataSourceDto dataSourceDto) {
        return getServiceNameByUrl(dataSourceDto.getUrl());
    }

    /**
     * 根据数据源信息获取不同数据库对应的服务
     *
     * @param tableDto 数据表源对象
     * @return database classic
     */
    public String getServiceNameByUrl(TableDto tableDto) {
        return getServiceNameByUrl(tableDto.getDatabaseSourceDto());
    }

    /**
     * 根据数据源信息获取不同数据库对应的服务
     *
     * @param dataSourceDto 数据源
     * @param schemaName    模式名
     * @param tableName     数据表名
     * @return database classic
     */
    public String getServiceNameByUrl(DataSourceDto dataSourceDto, String schemaName, String tableName) {
        TableDto tableDto = new TableDto();
        tableDto.setSchemaName(schemaName);
        tableDto.setTabName(tableName);
        tableDto.setDatabaseSourceDto(dataSourceDto);
        return getServiceNameByUrl(tableDto.getDatabaseSourceDto());
    }


    /**
     * 依据目标数据源获取数据库中表信息
     *
     * @param tableDto 目标数据表信息
     * @param sql          查询SQL
     * @param className    sql查询结果存储对象类名
     * @return List<IDataTableColumnEntity> {@link IColumnEntity}
     */
    public List<IColumnEntity> getTableColumnEntityList(TableDto tableDto, String sql, String className) {
        try {
            return getTableColumnEntityList(tableDto, sql, Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 依据目标数据源获取数据库中表信息
     *
     * @param tableDto 目标数据表信息
     * @param sql          查询SQL
     * @param classzz      sql查询结果存储对象类描述
     * @return List<IDataTableColumnEntity> {@link IColumnEntity}
     */
    public List<IColumnEntity> getTableColumnEntityList(TableDto tableDto, String sql, Class classzz) {
        DataSourceDto dataSourceDto = tableDto.getDatabaseSourceDto();
        List<IColumnEntity> targetDataTableColumnEntityList = getTableColumnEntityList(dataSourceDto, sql, classzz);
        List<IColumnEntity> localDataTableColumnEntityList = Lists.newArrayList();
        for (IColumnEntity iDatabaseTableEntity : targetDataTableColumnEntityList) {
            ColumnDto columnDto = new ColumnDto();
            BeanUtils.copyProperties(iDatabaseTableEntity, columnDto);
            columnDto.setDatabaseTableDto(tableDto);
            localDataTableColumnEntityList.add(columnDto);
        }
        return localDataTableColumnEntityList;
    }

    /**
     * 依据目标数据源获取数据库中表信息
     *
     * @param dataSourceDto 目标数据源信息
     * @param sql           查询SQL
     * @param clazz         sql查询结果存储对象描述
     * @return List<IDataTableColumnEntity> {@link IColumnEntity}
     */
    public List<IColumnEntity> getTableColumnEntityList(DataSourceDto dataSourceDto, String sql, Class clazz) {
        List<Object> resultList = super.getResultSet(dataSourceDto, sql, clazz);
        if (resultList == null) return Lists.newArrayList();
        return resultList.stream().map(object -> (IColumnEntity) object).collect(Collectors.toList());
    }

    /**
     * 依据目标数据源获取数据库中表信息
     *
     * @param dataSourceDto 目标数据源信息
     * @param sql           查询SQL
     * @param className     sql查询结果存储对象类名
     * @return List<IDataTableColumnEntity> {@link IColumnEntity}
     */
    public List<IColumnEntity> getTableColumnEntityList(DataSourceDto dataSourceDto, String sql, String className) {
        try {
            return getTableColumnEntityList(dataSourceDto, sql, Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public IColumnService getColumnService(DataSourceDto dataSourceDto) {
        return SpringUtil.getBean(getServiceNameByUrl(dataSourceDto));
    }
    public IColumnService getColumnService(ResponseDto responseDto) {
        return getColumnService((DataSourceDto) responseDto.getData());
    }

}
