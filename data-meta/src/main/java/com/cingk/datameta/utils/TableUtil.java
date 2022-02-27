package com.cingk.datameta.utils;

import com.google.common.collect.Lists;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cingk.datameta.model.ITableEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.entity.TableEntity;
import com.cingk.datameta.service.impl.MysqlTableService;
import com.cingk.datameta.service.intf.ITableService;
import java.sql.SQLException;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-01-30
 */
@Component
public class TableUtil extends DatabaseUtil {

	/**
	 * 根据Url获取不同数据库对应的服务
	 *
	 * @param url database connect url
	 * @return database classic
	 */
	public String getServiceNameByUrl(String url) {
		boolean isMysql = url.toLowerCase().contains(DB_TYPE_MYSQL);
		if (isMysql) {
			return MysqlTableService.class.getName();
		}
		boolean isOracle = url.toLowerCase().contains(DB_TYPE_ORACLE);
		if (isOracle) {
//			return OracleDataTableService.class.getName();
			return "";
		}
		return "";
	}

	/**
	 * 根据数据源信息获取不同数据库对应的服务
	 *
	 * @param dataSourceDto 数据源连接信息对象
	 * @return database classic
	 */
	public String getServiceNameByUrl(DataSourceDto dataSourceDto) {
		return getServiceNameByUrl(dataSourceDto.getUrl());
	}

	/**
	 * 依据目标数据源获取数据库中表信息
	 *
	 * @param dataSourceDto 目标数据源信息
	 * @param sql 查询SQL
	 * @param className sql查询结果存储对象类名
	 * @return List<DatabaseTableEntity> {@link TableEntity}
	 */
	public List<TableEntity> getDataTableEntityList(DataSourceDto dataSourceDto, String sql, String className) {

		try {
			Integer databaseSourceId = dataSourceDto.getId();
			List<Object> resultList = super.getResultSet(dataSourceDto, sql, Class.forName(className));
			if (resultList == null)  return Lists.newArrayList();

			List<TableEntity> tableEntityList = Lists.newArrayList();
			resultList.forEach(object -> {
				ITableEntity dataTableEntity = (ITableEntity) object;
				TableEntity databaseTableEntity = new TableEntity();
				databaseTableEntity.setTabName(dataTableEntity.getTabName());
				databaseTableEntity.setSchemaName(dataTableEntity.getSchema());
				databaseTableEntity.setDatabaseSourceId(databaseSourceId);
				databaseTableEntity.setTabType(dataTableEntity.getTabType());
				tableEntityList.add(databaseTableEntity);
			});

			return tableEntityList;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 统计表数量
	 *
	 * @param dataSourceDto 数据源
	 * @param sql 统计SQL
	 * @return Integer 数量
	 */
	public Integer getTotalDataTableCount(DataSourceDto dataSourceDto, String sql) throws SQLException {
		return super.getRowCount(dataSourceDto, sql);
	}



	/**
	 * 具体数据源服务实现类
	 * @param dataSourceDto
	 * @return
	 */
	public ITableService getTableService(DataSourceDto dataSourceDto){
		return SpringUtil.getBean(getServiceNameByUrl(dataSourceDto));
	}
}
