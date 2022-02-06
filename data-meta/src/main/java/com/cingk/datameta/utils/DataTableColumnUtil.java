package com.cingk.datameta.utils;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.cingk.datameta.constant.enums.ServiceEnum;
import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.dto.DatabaseTableColumnDto;
import com.cingk.datameta.model.dto.DatabaseTableDto;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-01-30
 */
@Component
public class DataTableColumnUtil extends DatabaseUtil {

	/**
	 * 根据Url获取不同数据库对应的服务
	 * @param url database connect url
	 * @return database classic
	 */
	public String getServiceNameByUrl(String url){
		boolean isMysql = url.toLowerCase().contains(DB_TYPE_MYSQL);
		if (isMysql) return ServiceEnum.MYSQL_COL_SERVICE.getValue();
		boolean isOracle = url.toLowerCase().contains(DB_TYPE_ORACLE);
		if (isOracle) return ServiceEnum.ORACLE_COL_SERVICE.getValue();
		return "";
	}

	/**
	 * 根据数据源信息获取不同数据库对应的服务
	 * @param databaseSourceDto 数据源连接信息对象
	 * @return database classic
	 */
	public String getServiceNameByUrl(DatabaseSourceDto databaseSourceDto){
		return getServiceNameByUrl(databaseSourceDto.getUrl());
	}
	/**
	 * 根据数据源信息获取不同数据库对应的服务
	 * @param databaseTableDto 数据表源对象
	 * @return database classic
	 */
	public String getServiceNameByUrl(DatabaseTableDto databaseTableDto){
		return getServiceNameByUrl(databaseTableDto.getDatabaseSourceDto());
	}




	/**
	 * 依据目标数据源获取数据库中表信息
	 *
	 * @param databaseTableDto 目标数据表信息
	 * @param sql 查询SQL
	 * @param className sql查询结果存储对象类名
	 * @return List<IDataTableColumnEntity> {@link com.cingk.datameta.model.IDataTableColumnEntity}
	 */
	public List<IDataTableColumnEntity> getTableColumnEntityList(DatabaseTableDto databaseTableDto, String sql, String className)
		throws SQLException,
		InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {
		return getTableColumnEntityList(databaseTableDto, sql, Class.forName(className));
	}

	/**
	 * 依据目标数据源获取数据库中表信息
	 *
	 * @param databaseTableDto 目标数据表信息
	 * @param sql 查询SQL
	 * @param classzz sql查询结果存储对象类描述
	 * @return List<IDataTableColumnEntity> {@link com.cingk.datameta.model.IDataTableColumnEntity}
	 */
	public List<IDataTableColumnEntity> getTableColumnEntityList(DatabaseTableDto databaseTableDto, String sql, Class classzz)
		throws SQLException,
		InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {

		DatabaseSourceDto databaseSourceDto = databaseTableDto.getDatabaseSourceDto();
		List<IDataTableColumnEntity> targetDataTableColumnEntityList = getTableColumnEntityList(databaseSourceDto, sql, classzz);
		List<IDataTableColumnEntity> localDataTableColumnEntityList = Lists.newArrayList();
		for (IDataTableColumnEntity iDatabaseTableEntity : targetDataTableColumnEntityList) {
			DatabaseTableColumnDto databaseTableColumnDto = new DatabaseTableColumnDto();
			BeanUtils.copyProperties(iDatabaseTableEntity, databaseTableColumnDto);
			databaseTableColumnDto.setDatabaseTableDto(databaseTableDto);
			localDataTableColumnEntityList.add(databaseTableColumnDto);
		}
		return localDataTableColumnEntityList;
	}

	/**
	 * 依据目标数据源获取数据库中表信息
	 *
	 * @param databaseSourceDto 目标数据源信息
	 * @param sql 查询SQL
	 * @param clazz sql查询结果存储对象描述
	 * @return List<IDataTableColumnEntity> {@link com.cingk.datameta.model.IDataTableColumnEntity}
	 */
	public List<IDataTableColumnEntity> getTableColumnEntityList(DatabaseSourceDto databaseSourceDto, String sql, Class clazz)
		throws SQLException,
		InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
		List<Object> resultList = super.getResultSet(databaseSourceDto, sql, clazz);
		List<IDataTableColumnEntity> dataTableColumnEntityList = Lists.newArrayList();
		if (resultList == null) {
			return dataTableColumnEntityList;
		}
		dataTableColumnEntityList = resultList.stream().map(object -> (IDataTableColumnEntity) object).collect(Collectors.toList());
		return dataTableColumnEntityList;
	}

	/**
	 * 依据目标数据源获取数据库中表信息
	 *
	 * @param databaseSourceDto 目标数据源信息
	 * @param sql 查询SQL
	 * @param className sql查询结果存储对象类名
	 * @return List<IDataTableColumnEntity> {@link com.cingk.datameta.model.IDataTableColumnEntity}
	 */
	public List<IDataTableColumnEntity> getTableColumnEntityList(DatabaseSourceDto databaseSourceDto, String sql, String className)
		throws SQLException,
		InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {
		return getTableColumnEntityList(databaseSourceDto,sql,Class.forName(className));
	}

}
