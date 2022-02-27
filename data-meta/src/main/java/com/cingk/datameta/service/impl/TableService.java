package com.cingk.datameta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cingk.datameta.mapper.ITableRepository;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.entity.TableEntity;
import com.cingk.datameta.service.intf.ITableService;
import com.cingk.datameta.utils.TableUtil;
import java.time.Instant;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-02-26
 */
@Service
public class TableService implements ITableService {

	private static final int INIT_VERSION = 1;

	private ITableRepository dataTableRepository;

	@Autowired
	public void setDataTableRepository(ITableRepository dataTableRepository) {
		this.dataTableRepository = dataTableRepository;
	}

	@Override
	public List<TableEntity> saveTables(List<TableEntity> tableEntityList) {
		Instant creatTime = Instant.now();
		tableEntityList.stream().forEach(tableEntity -> {
				tableEntity.setCreatTime(creatTime);
				tableEntity.setVersion(INIT_VERSION);});
		return (List<TableEntity>)dataTableRepository.saveAll(tableEntityList);
	}

	@Override
	public void deleteTable(Integer dataSourceId, String schemaName, String tableName) {

	}

	@Override
	public void deleteTable(Integer dataSourceId, String schemaName, Integer tabId) {

	}

	@Override
	public void deleteTable(TableEntity tableEntity) {

	}

	@Override
	public void updateTable(TableEntity tableEntity) {

	}

	@Override
	public List<TableEntity> getTables(Integer dataSourceId, String schemaName) {
		return dataTableRepository.queryAll(dataSourceId,schemaName);
	}

	@Override
	public List<TableEntity> getTables(Integer dataSourceId, String[] schemaName) {
		return dataTableRepository.queryAll(dataSourceId,schemaName);
	}

	@Override
	public List<TableEntity> getTables(Integer dataSourceId, String schemaName, String[] tableName) {
		return dataTableRepository.queryAll(dataSourceId,schemaName,tableName);
	}

	@Override
	public List<TableEntity> getTables(Integer dataSourceId, String schemaName, Integer[] tabId) {
		return dataTableRepository.queryAll(dataSourceId,schemaName,tabId);
	}

	@Override
	public TableEntity getTable(Integer dataSourceId, String schemaName, String tableName) {
		return dataTableRepository.findByName(dataSourceId,schemaName,tableName);
	}

	@Override
	public TableEntity getTable(Integer dataSourceId, String schemaName, Integer tabId) {
		return dataTableRepository.findById(dataSourceId,schemaName,tabId);
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
