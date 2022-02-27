package com.cingk.datameta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.entity.DataTableEntity;
import com.cingk.datameta.model.entity.MysqlTableEntity;
import com.cingk.datameta.utils.TableUtil;
import com.cingk.datameta.utils.StrUtil;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-02-26
 */
@Service
public class MysqlTableService extends TableService {


	//查询所有表
	private static final String QUERY_BASE = "select * from information_schema.tables";

	private static final String[] DB_SYS_SCHEMA = {"information_schema", "mysql", "performance_schema", "sys"};
	private static final String QUERY_CONDITION = StrUtil.join(StrUtil.COMMA,
		StrUtil.wrapAllWithPair(StrUtil.SINGLE_QUOTE, DB_SYS_SCHEMA));

	//查询非系统表
	private static final String QUERY_USER_TABLE = QUERY_BASE + " where table_schema not in (%s)";

	//查询指定schema下的表
	private static final String QUERY_BY_SCHEMA = QUERY_BASE + " where table_schema='%s'";
	private static final String QUERY_BY_SCHEMAS = QUERY_BASE + " where table_schema in ('%s')";

	private static final String QUERY_BY_SCHEMA_TABLE_NAME = QUERY_BY_SCHEMA + " and table_name = '%s' ";
	private static final String QUERY_BY_SCHEMA_TABLE_NAMES = QUERY_BY_SCHEMA + " and table_name in ('%s') ";

	//查询Sql结果存储对象
	private static final String JDBC_RESULT_CLASS_NAME = MysqlTableEntity.class.getName();

	private static final String QUERY_COUNT = "select count(1) from information_schema.tables";
	private static final String QUERY_COUNT_BY_SCHEMA = QUERY_COUNT + " where table_schema='%s'";


	private TableUtil tableUtil;

	@Autowired
	public void setTableUtil(TableUtil tableUtil) {
		this.tableUtil = tableUtil;
	}

	@Override
	public DataTableEntity getTable(Integer dataSourceId, String schemaName, String tableName) {
		String sql = String.format(QUERY_BY_SCHEMA_TABLE_NAME, schemaName, tableName);
		DataSourceDto dataSourceDto = super.makeDataSourceDto(dataSourceId);
		List<DataTableEntity> dataTableEntityList = tableUtil.getDataTableEntityList(dataSourceDto, sql, JDBC_RESULT_CLASS_NAME);
		return dataTableEntityList.get(0);
	}

	@Override
	public List<DataTableEntity> getTables(Integer dataSourceId, String schemaName) {
		String sql = String.format(QUERY_BY_SCHEMA, schemaName);
		DataSourceDto dataSourceDto = super.makeDataSourceDto(dataSourceId);
		return tableUtil.getDataTableEntityList(dataSourceDto, sql, JDBC_RESULT_CLASS_NAME);
	}

	@Override
	public List<DataTableEntity> getTables(Integer dataSourceId, String[] schemaName) {
		String sql = String.format(QUERY_BY_SCHEMAS, String.join(StrUtil.SQL_COL_QUOTE, schemaName));
		DataSourceDto dataSourceDto = super.makeDataSourceDto(dataSourceId);
		return tableUtil.getDataTableEntityList(dataSourceDto, sql, JDBC_RESULT_CLASS_NAME);
	}

	@Override
	public List<DataTableEntity> getTables(Integer dataSourceId, String schemaName, String[] tableName) {
		String sql = String.format(QUERY_BY_SCHEMA_TABLE_NAMES, schemaName, String.join(StrUtil.SQL_COL_QUOTE, tableName));
		DataSourceDto dataSourceDto = super.makeDataSourceDto(dataSourceId);
		return tableUtil.getDataTableEntityList(dataSourceDto, sql, JDBC_RESULT_CLASS_NAME);
	}


}
