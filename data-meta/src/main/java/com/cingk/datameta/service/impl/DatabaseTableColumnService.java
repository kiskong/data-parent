package com.cingk.datameta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.dto.DatabaseTableDto;
import com.cingk.datameta.service.intf.IColumnService;
import com.cingk.datameta.utils.DataTableColumnUtil;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-01-30
 */
@Service
public class DatabaseTableColumnService implements IColumnService {

	@Autowired
	public DataTableColumnUtil dataTableColumnUtil;

	@Override
	public List<IDataTableColumnEntity> getTableColumn(DatabaseSourceDto databaseSourceDto, String sql, Class resultClass) {
		return null;
	}

	@Override
	public List<IDataTableColumnEntity> getTableColumn(DatabaseTableDto databaseTableDto, String sql, Class resultClass) {
		try {

			return dataTableColumnUtil.getTableColumnEntityList(databaseTableDto, sql, resultClass);
		} catch (SQLException | InvocationTargetException
			| NoSuchMethodException | IllegalAccessException
			| InstantiationException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<IDataTableColumnEntity> getTableColumn(DatabaseTableDto databaseTableDto) {
		return null;
	}
}
