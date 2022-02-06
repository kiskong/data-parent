package com.cingk.datameta.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DatabaseTableDto;
import com.cingk.datameta.model.entity.MysqlColumnEntity;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-01-30
 */
@Service
public class MysqlTableColumnService extends DatabaseTableColumnService {

	public static final String QUERY_COLUMN = "select * from information_schema.columns where table_schema='%s' and table_name='%s'";

	/**
	 * 根据schema，tableName，datasource 获取对应表字段
	 * @param databaseTableDto {@link com.cingk.datameta.model.dto.DatabaseTableDto}
	 * @return List<IDataTableColumnEntity> {@link com.cingk.datameta.model.IDataTableColumnEntity}
	 */
	@Override
	public List<IDataTableColumnEntity> getTableColumn(DatabaseTableDto databaseTableDto) {
		String tableName = databaseTableDto.getTabName();
		String tabSchema = databaseTableDto.getSchemaName();
		String sql = String.format(QUERY_COLUMN, tabSchema, tableName);
		return super.getTableColumn(databaseTableDto,sql,MysqlColumnEntity.class);
	}
}
