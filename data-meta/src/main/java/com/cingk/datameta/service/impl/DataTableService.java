package com.cingk.datameta.service.impl;

import java.util.List;

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

	/**
	 * 交由子类实现
	 * @param dataSourceDto
	 * @return
	 */
	@Override
	public List<DataTableEntity> getSrcAllTables(DataSourceDto dataSourceDto) {
		return null;
	}

	@Override
	public List<DataTableEntity> getLocalAllTables(Integer dataSourceId, String[] tableNames) {
		return dataTableRepository.queryAll(dataSourceId, tableNames);
	}

	@Override
	public List<DataTableEntity> getLocalAllTables(Integer dataSourceId, String schemaName, String[] tableNames) {
		return dataTableRepository.queryAll(dataSourceId,schemaName,tableNames);
	}

	@Override
	public List<DataTableEntity> saveAllTables(List<DataTableEntity> dataTableEntityList) {
		return (List<DataTableEntity>) dataTableRepository.saveAll(dataTableEntityList);
	}

	@Override
	public List<DataTableEntity> getSrcAllTables(DataSourceDto dataSourceDto, String sql, String resultClassName) {
		try {
			return dataTableUtil.getDataTableEntityList(dataSourceDto, sql, resultClassName);
		} catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException
			| InstantiationException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public List<DataTableEntity> getSrcAllTablesWithSchema(DataSourceDto dataSourceDto, String schema) {

		return null;
	}

	/**
	 * 通过指定数据源信息和模式名，查询<br/>
	 * 该模式名下的所有表的基本信息，并将<br/>
	 * 查询结果提取共有属性封装在公共对象中。
	 *
	 * @param dataSourceDto 数据源
	 * @param schema 模式名
	 * @param sql 具体的sql
	 * @param resultClassName 返回的结果集存储对象（子类传入，适应不同的数据源）
	 * @return List<DataTableEntity> 用于本地存储的实体
	 */
	@Override
	public List<DataTableEntity> getSrcAllTablesWithSchema(DataSourceDto dataSourceDto,
		String schema, String sql, String resultClassName) {
		try {
			return dataTableUtil.getDataTableEntityList(dataSourceDto, sql, resultClassName);
		} catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException
			| InstantiationException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Integer getSrcTotalDataTableCount(DataSourceDto dataSourceDto, String sql) {
		try {
			return dataTableUtil.getTotalDataTableCount(dataSourceDto, sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
