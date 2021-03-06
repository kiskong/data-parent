package com.cingk.datameta.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cingk.datameta.mapper.IDataTableColumnRepository;
import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.DataTableDto;
import com.cingk.datameta.model.entity.DataTableColumnEntity;
import com.cingk.datameta.service.intf.IDataTableColumn;
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
public class DataTableColumnService implements IDataTableColumn {

	public DataTableColumnUtil dataTableColumnUtil;
	public IDataTableColumnRepository dataTableColumnRepository;

	@Override
	public List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String sql, Class resultClass) {
		return null;
	}

	@Override
	public List<IDataTableColumnEntity> getTableColumn(DataTableDto dataTableDto, String sql, Class resultClass) {
		try {
			return dataTableColumnUtil.getTableColumnEntityList(dataTableDto, sql, resultClass);
		} catch (SQLException | InvocationTargetException
			| NoSuchMethodException | IllegalAccessException
			| InstantiationException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<IDataTableColumnEntity> getTableColumn(DataTableDto dataTableDto) {
		return null;
	}

	@Override
	public List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, String tableName) {
		return null;
	}

	@Override
	public List<IDataTableColumnEntity> getTableColumn(DataSourceDto dataSourceDto, String schemaName, String[] tableNames) {
		return null;
	}

	@Override
	public List<DataTableColumnEntity> saveAllTableColumn(List<DataTableColumnEntity> dataTableColumnEntityList) {
		return (List<DataTableColumnEntity>) dataTableColumnRepository.saveAll(dataTableColumnEntityList);
	}

	@Override
	public List<DataTableColumnEntity> saveAllTableColumnNotExists(List<DataTableColumnEntity> dataTableEntityList) {
		return null;
	}

	@Override
	public List<DataTableColumnEntity> getLocalTableColumn(List<DataTableColumnEntity> dataTableColumnEntityList) {
		List<Integer> tabIds = dataTableColumnEntityList.stream().map(DataTableColumnEntity::getTabId).collect(Collectors.toList());
		return dataTableColumnRepository.getLocalTableColumn(tabIds);
	}

	@Autowired
	public void setDataTableColumnUtil(DataTableColumnUtil dataTableColumnUtil) {
		this.dataTableColumnUtil = dataTableColumnUtil;
	}

	@Autowired
	public void setDataTableColumnRepository(IDataTableColumnRepository dataTableColumnRepository) {
		this.dataTableColumnRepository = dataTableColumnRepository;
	}
}
