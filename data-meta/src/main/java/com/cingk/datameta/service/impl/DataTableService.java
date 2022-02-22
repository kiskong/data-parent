package com.cingk.datameta.service.impl;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.collection.ListUtil;
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


	public DataTableUtil dataTableUtil;
	public IDataTableRepository dataTableRepository;

	@Override
	public DataTableEntity getDatabaseTable(DataSourceDto dataSourceDto, String tableName) {
		return null;
	}


	/**
	 * 删除表
	 * @param dataTableDto 表信息
	 */
	@Override
	public void delDatabaseTable(DataTableDto dataTableDto) {
		DataTableEntity dataTableEntity = new DataTableEntity();
		BeanUtils.copyProperties(dataTableDto, dataTableEntity);
		dataTableRepository.delete(dataTableEntity);
	}

	/**
	 * 修改表
	 * @param dataTableDto 表信息
	 */
	@Override
	public void updDatabaseTable(DataTableDto dataTableDto) {
		DataTableEntity dataTableEntity = new DataTableEntity();
		BeanUtils.copyProperties(dataTableDto, dataTableEntity);
		dataTableRepository.save(dataTableEntity);
	}

	/**
	 * 保存表
	 * @param dataTableDto 表信息
	 * @return DataTableEntity 数据表实体
	 */
	@Override
	public DataTableEntity save(DataTableDto dataTableDto) {
		DataTableEntity dataTableEntity = new DataTableEntity();
		BeanUtils.copyProperties(dataTableDto, dataTableEntity);
		return dataTableRepository.save(dataTableEntity);
	}

	/**
	 * 交由子类实现
	 */
	@Override
	public List<DataTableEntity> getSrcAllTables(DataSourceDto dataSourceDto) {
		return null;
	}

	/**
	 * 查询指定表名称的表信息
	 * @param dataSourceId 数据源
	 * @param tableNames 表名称
	 * @return List<DataTableEntity> 表信息
	 */
	@Override
	public List<DataTableEntity> getLocalAllTables(Integer dataSourceId, String[] tableNames) {
		return dataTableRepository.queryAll(dataSourceId, tableNames);
	}

	/**
	 * 查询指定表名称的表信息
	 * @param dataSourceId 数据源
	 * @param schemaName 模式名
	 * @param tableNames 表名称
	 * @return List<DataTableEntity> 表信息
	 */
	@Override
	public List<DataTableEntity> getLocalAllTables(Integer dataSourceId, String schemaName, String[] tableNames) {

		List<DataTableEntity> dataTableEntityList = Lists.newArrayList();
		List<String> tableNameList = Arrays.asList(tableNames);
		int listSize = tableNameList.size();
		int limit = listSize > 999 ? 999 : listSize;

		List<List<String>> splitList = ListUtil.split(tableNameList, limit);
		splitList.forEach(tables ->
			dataTableEntityList.addAll(dataTableRepository.queryAll(dataSourceId, schemaName, tables.toArray(new String[tables.size()])))
		);
		return dataTableEntityList;
	}

	/**
	 * 查询指定表名称的表信息
	 * @param dataSourceId 数据源
	 * @param schemaName 模式名
	 * @return List<DataTableEntity> 表信息
	 */
	@Override
	public List<DataTableEntity> getLocalAllTables(Integer dataSourceId, String schemaName) {
		return dataTableRepository.queryAll(dataSourceId,schemaName);
	}

	/**
	 * 保存表
	 *
	 * @param dataTableEntityList 表信息列表
	 * @return List<DataTableEntity> 表信息
	 */
	@Override
	public List<DataTableEntity> saveAllTables(List<DataTableEntity> dataTableEntityList) {
		return (List<DataTableEntity>) dataTableRepository.saveAll(dataTableEntityList);
	}

	/**
	 * 保存新抓取的表，已存在的表不处理
	 *
	 * @param dataSourceId 数据源ID
	 * @param schemaName 模式名
	 * @param dataTableEntityList 源数据的表信息
	 * @return List<DataTableEntity> 新表
	 */
	@Override
	public List<DataTableEntity> saveAllTablesNotExists(Integer dataSourceId, String schemaName,
		List<DataTableEntity> dataTableEntityList) {
		List<DataTableEntity> dbDataTableEntityList = getLocalAllTables(dataSourceId, schemaName, new String[0]);
		dataTableEntityList.removeAll(dbDataTableEntityList);
		return (List<DataTableEntity>) dataTableRepository.saveAll(dataTableEntityList);
	}

	/**
	 * 获取指定数据源的表信息
	 *
	 * @param dataSourceDto 数据源信息
	 * @param sql 子类实现的SQL（不同的数据源SQL不同）
	 * @param resultClassName 子类表结构实体类（不同的数据源应用不同的实体类）
	 * @return List<DataTableEntity> 表的公共属性实体，由子类转换而来
	 */
	@Override
	public List<DataTableEntity> getSrcAllTables(DataSourceDto dataSourceDto, String sql, String resultClassName) {
		try {
			return dataTableUtil.getDataTableEntityList(dataSourceDto, sql, resultClassName);
		} catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException
			| InstantiationException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * 子类实现
	 *
	 * @param dataSourceDto 数据源信息
	 * @param schema 模式名
	 */
	@Override
	public List<DataTableEntity> getSrcAllTablesWithSchema(DataSourceDto dataSourceDto, String schema) {

		return null;
	}

	/**
	 * 通过指定数据源信息和模式名，查询<br/> 该模式名下的所有表的基本信息，并将<br/> 查询结果提取共有属性封装在公共对象中。
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

	/**
	 * 统计表数量
	 */
	@Override
	public Integer getSrcTotalDataTableCount(DataSourceDto dataSourceDto, String sql) {
		try {
			return dataTableUtil.getTotalDataTableCount(dataSourceDto, sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Autowired
	public void setDataTableUtil(DataTableUtil dataTableUtil) {
		this.dataTableUtil = dataTableUtil;
	}

	@Autowired
	public void setDataTableRepository(IDataTableRepository dataTableRepository) {
		this.dataTableRepository = dataTableRepository;
	}
}
