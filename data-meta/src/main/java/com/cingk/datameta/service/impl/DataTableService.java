package com.cingk.datameta.service.impl;

import java.util.List;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.entity.DataSourceEntity;
import com.cingk.datameta.service.intf.IDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cingk.datameta.mapper.IDataTableRepository;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.DataTableDto;
import com.cingk.datameta.model.entity.DataTableEntity;
import com.cingk.datameta.service.intf.IDataTable;
import com.cingk.datameta.utils.DataTableUtil;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-01-29
 */
@Service
public class DataTableService implements IDataTable {

    public static final String SINGLE_QUOTE = "'";

    @Autowired
    public DataTableUtil dataTableUtil;

    @Autowired
    public IDataTableRepository dataTableRepository;

    @Override
    public DataTableEntity getDatabaseTable(DataSourceDto dataSourceDto, String tableName) {
        return null;
    }


    @Override
    public void delDatabaseTable(DataTableDto dataTableDto) {
        DataTableEntity dataTableEntity = new DataTableEntity();
        BeanUtils.copyProperties(dataTableDto, dataTableEntity);
        dataTableRepository.delete(dataTableEntity);
    }

    @Override
    public void updDatabaseTable(DataTableDto dataTableDto) {
        DataTableEntity dataTableEntity = new DataTableEntity();
        BeanUtils.copyProperties(dataTableDto, dataTableEntity);
        dataTableRepository.save(dataTableEntity);
    }

    @Override
    public DataTableEntity save(DataTableDto dataTableDto) {
        DataTableEntity dataTableEntity = new DataTableEntity();
        BeanUtils.copyProperties(dataTableDto, dataTableEntity);
        return dataTableRepository.save(dataTableEntity);
    }

    @Override
    public List<DataTableEntity> getAllTables(DataSourceDto dataSourceDto) {
        return null;
    }

    @Override
    public List<DataTableEntity> saveAllTables(List<DataTableEntity> dataTableEntityList) {
        return (List<DataTableEntity>) dataTableRepository.saveAll(dataTableEntityList);
    }

    @Override
    public List<DataTableEntity> getAllTables(DataSourceDto dataSourceDto, String sql, String resultClassName) {
        try {
            return dataTableUtil.getDataTableEntityList(dataSourceDto, sql, resultClassName);
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DataTableEntity> getAllTablesWithSchema(DataSourceDto dataSourceDto, String schema) {
        return null;
    }

    @Override
    public List<DataTableEntity> getAllTablesWithSchema(DataSourceDto dataSourceDto,
                                                        String schema, String sql, String resultClassName) {
        try {
            return dataTableUtil.getDataTableEntityList(dataSourceDto, sql, resultClassName);
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getTotalDataTableCount(DataSourceDto dataSourceDto, String sql) {
        try {
            return dataTableUtil.getTotalDataTableCount(dataSourceDto,sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
