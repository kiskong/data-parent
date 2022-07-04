package com.cingk.datameta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.entity.TableEntity;
import com.cingk.datameta.service.intf.ITableService;
import com.cingk.datameta.utils.TableUtil;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-02-28
 */
@Service
public class TargetTableService {

	public TableUtil tableUtil;
	@Autowired
	public void setTableUtil(TableUtil tableUtil) {
		this.tableUtil = tableUtil;
	}


	public TableEntity getTable(DataSourceDto dataSourceDto, String schemaName, String tableName) {
		Integer dataSourceId = dataSourceDto.getId();
		ITableService tagTableService = tableUtil.getTableService(dataSourceDto);
		return tagTableService.getTable(dataSourceId, schemaName, tableName);
	}

	public List<TableEntity> getTable(DataSourceDto dataSourceDto, String schemaName, String[] tableName) {
		Integer dataSourceId = dataSourceDto.getId();
		ITableService tagTableService = tableUtil.getTableService(dataSourceDto);
		return tagTableService.getTables(dataSourceId, schemaName, tableName);
	}

	public List<TableEntity> getTable(DataSourceDto dataSourceDto, String schemaName) {
		Integer dataSourceId = dataSourceDto.getId();
		ITableService tagTableService = tableUtil.getTableService(dataSourceDto);
		return tagTableService.getTables(dataSourceId, schemaName);
	}
}
