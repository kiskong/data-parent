package com.cingk.datameta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cingk.datameta.mapper.ITableRepository;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.entity.DataTableEntity;
import com.cingk.datameta.service.intf.ITableService;
import com.cingk.datameta.utils.TableUtil;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-02-26
 */
@Service
public class TableService implements ITableService {

	private ITableRepository dataTableRepository;

	@Autowired
	public void setDataTableRepository(ITableRepository dataTableRepository) {
		this.dataTableRepository = dataTableRepository;
	}

	@Override
	public List<DataTableEntity> saveTables(List<DataTableEntity> dataTableEntityList) {
		return (List<DataTableEntity>)dataTableRepository.saveAll(dataTableEntityList);
	}

	@Override
	public void deleteTable(Integer dataSourceId, String schemaName, String tableName) {

	}

	@Override
	public void deleteTable(Integer dataSourceId, String schemaName, Integer tabId) {

	}

	@Override
	public void deleteTable(DataTableEntity dataTableEntity) {

	}

	@Override
	public void updateTable(DataTableEntity dataTableEntity) {

	}

	@Override
	public List<DataTableEntity> getTables(Integer dataSourceId, String schemaName) {
		return null;
	}

	@Override
	public List<DataTableEntity> getTables(Integer dataSourceId, String[] schemaName) {
		return null;
	}

	@Override
	public List<DataTableEntity> getTables(Integer dataSourceId, String schemaName, String[] tableName) {
		return null;
	}

	@Override
	public List<DataTableEntity> getTables(Integer dataSourceId, String schemaName, Integer[] tabId) {
		return null;
	}

	@Override
	public DataTableEntity getTable(Integer dataSourceId, String schemaName, String tableName) {
		return null;
	}

	@Override
	public DataTableEntity getTable(Integer dataSourceId, String schemaName, Integer tabId) {
		return null;
	}


	private TableUtil tableUtil;
	private DataSourceService dataSourceService;

	@Autowired
	public void setTableUtil(TableUtil tableUtil) {
		this.tableUtil = tableUtil;
	}

	@Autowired
	public void setDataSourceService(DataSourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public DataSourceDto makeDataSourceDto(Integer dataSourceId) {
		return dataSourceService.getDataSourceDtoById(dataSourceId);
	}
}
