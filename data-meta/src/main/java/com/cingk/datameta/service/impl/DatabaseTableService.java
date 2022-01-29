package com.cingk.datameta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.entity.DatabaseTableEntity;
import com.cingk.datameta.service.intf.ITableService;
import com.cingk.datameta.utils.DatabaseUtil;
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
	public DatabaseUtil databaseUtil;

	@Override
	public DatabaseTableEntity getDatabaseTable(DatabaseSourceDto databaseSourceDto,
		String tableName) {
		return null;
	}

	@Override
	public void delDatabaseTable(DatabaseSourceDto databaseTableDto) {

	}

	@Override
	public void updDatabaseTable(DatabaseSourceDto databaseTableDto) {

	}

	@Override
	public List<DatabaseTableEntity> getAllTables(DatabaseSourceDto databaseSourceDto) {
		return null;
	}

	@Override
	public List<DatabaseTableEntity> getAllTables(DatabaseSourceDto databaseSourceDto, String sql,
		String resultClassName) {
		try {
			return databaseUtil.getDataTableEntityList(databaseSourceDto, sql, resultClassName);
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
			return databaseUtil.getDataTableEntityList(databaseSourceDto, sql, resultClassName);
		} catch (SQLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException
			| InstantiationException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
