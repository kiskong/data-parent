package com.cingk.datameta.utils;

import com.google.common.collect.Lists;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cingk.datameta.constant.enums.ServiceEnum;
import com.cingk.datameta.model.IDataTableEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.entity.DataTableEntity;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-01-30
 */
@Component
public class DataTableUtil extends DatabaseUtil {


	/**
	 * 根据Url获取不同数据库对应的服务
	 * @param url database connect url
	 * @return database classic
	 */
	public String getServiceNameByUrl(String url){
		boolean isMysql = url.toLowerCase().contains(DB_TYPE_MYSQL);
		if (isMysql) return ServiceEnum.MYSQL_TAB_SERVICE.getValue();
		boolean isOracle = url.toLowerCase().contains(DB_TYPE_ORACLE);
		if (isOracle) return ServiceEnum.ORACLE_TAB_SERVICE.getValue();
		return "";
	}

	/**
	 * 根据数据源信息获取不同数据库对应的服务
	 * @param dataSourceDto 数据源连接信息对象
	 * @return database classic
	 */
	public String getServiceNameByUrl(DataSourceDto dataSourceDto){
		return getServiceNameByUrl(dataSourceDto.getUrl());
	}

	/**
	 * 依据目标数据源获取数据库中表信息
	 *
	 * @param dataSourceDto 目标数据源信息
	 * @param sql 查询SQL
	 * @param className sql查询结果存储对象类名
	 * @return List<DatabaseTableEntity> {@link DataTableEntity}
	 */
	public List<DataTableEntity> getDataTableEntityList(DataSourceDto dataSourceDto, String sql, String className)
		throws SQLException,
		InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {

		Integer databaseSourceId =  dataSourceDto.getId();
		List<Object> resultList = super.getResultSet(dataSourceDto, sql, Class.forName(className));
		List<DataTableEntity> dataTableEntityList = Lists.newArrayList();
		if (resultList == null) {
			return dataTableEntityList;
		}
		resultList.forEach(object -> {
			IDataTableEntity dataTableEntity = (IDataTableEntity) object;
			DataTableEntity databaseTableEntity = new DataTableEntity();
			databaseTableEntity.setTabName(dataTableEntity.getTabName());
			databaseTableEntity.setSchemaName(dataTableEntity.getSchema());
			databaseTableEntity.setDatabaseSourceId(databaseSourceId);
			databaseTableEntity.setTabType(dataTableEntity.getTabType());
			dataTableEntityList.add(databaseTableEntity);
		});

		return dataTableEntityList;
	}
}
