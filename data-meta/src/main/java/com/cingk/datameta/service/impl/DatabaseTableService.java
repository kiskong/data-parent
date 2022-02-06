package com.cingk.datameta.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cingk.datameta.mapper.IDatabaseTableRepository;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.dto.DatabaseTableDto;
import com.cingk.datameta.model.entity.DatabaseTableEntity;
import com.cingk.datameta.service.intf.ITableService;
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
public class DatabaseTableService implements ITableService {

	public static final String SINGLE_QUOTE = "'";

	@Autowired
	public DataTableUtil dataTableUtil;

	@Autowired
	public IDatabaseTableRepository databaseTableRepository;

	@Override
	public DatabaseTableEntity getDatabaseTable(DatabaseSourceDto databaseSourceDto,
		String tableName) {
		return null;
	}

	@Override
	public void delDatabaseTable(DatabaseTableDto databaseTableDto) {
		DatabaseTableEntity databaseTableEntity = new DatabaseTableEntity();
		BeanUtils.copyProperties(databaseTableDto,databaseTableEntity);
		databaseTableRepository.delete(databaseTableEntity);
	}

	@Override
	public void updDatabaseTable(DatabaseTableDto databaseTableDto) {
		DatabaseTableEntity databaseTableEntity = new DatabaseTableEntity();
		BeanUtils.copyProperties(databaseTableDto,databaseTableEntity);
		databaseTableRepository.save(databaseTableEntity);
	}

	@Override
	public DatabaseTableEntity save(DatabaseTableDto databaseTableDto) {
		DatabaseTableEntity databaseTableEntity = new DatabaseTableEntity();
		BeanUtils.copyProperties(databaseTableDto,databaseTableEntity);
		return databaseTableRepository.save(databaseTableEntity);
	}

	@Override
	public List<DatabaseTableEntity> getAllTables(DatabaseSourceDto databaseSourceDto) {
		return null;
	}

	@Override
	public List<DatabaseTableEntity> saveAllTables(List<DatabaseTableEntity> databaseTableEntityList) {
		return (List<DatabaseTableEntity>)databaseTableRepository.saveAll(databaseTableEntityList);
	}

	@Override
	public List<DatabaseTableEntity> getAllTables(DatabaseSourceDto databaseSourceDto, String sql,
		String resultClassName) {
		try {
			return dataTableUtil.getDataTableEntityList(databaseSourceDto, sql, resultClassName);
		} catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException
			| InstantiationException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<DatabaseTableEntity> getAllTablesWithSchema(DatabaseSourceDto databaseSourceDto,
		String schema) {
		return null;
	}

	@Override
	public List<DatabaseTableEntity> getAllTablesWithSchema(DatabaseSourceDto databaseSourceDto,
		String schema, String sql, String resultClassName) {
		try {
			return dataTableUtil.getDataTableEntityList(databaseSourceDto, sql, resultClassName);
		} catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException
			| InstantiationException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
