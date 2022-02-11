package com.cingk.datameta.utils;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.cingk.datameta.constant.enums.ServiceEnum;
import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.DataTableColumnDto;
import com.cingk.datameta.model.dto.DataTableDto;
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
	 * @param dataSourceDto 数据源连接信息对象
	 * @return database classic
	 */
	public String getServiceNameByUrl(DataSourceDto dataSourceDto){
		return getServiceNameByUrl(dataSourceDto.getUrl());
	}
	/**
	 * 根据数据源信息获取不同数据库对应的服务
	 * @param dataTableDto 数据表源对象
	 * @return database classic
	 */
	public String getServiceNameByUrl(DataTableDto dataTableDto){
		return getServiceNameByUrl(dataTableDto.getDatabaseSourceDto());
	}




	/**
	 * 依据目标数据源获取数据库中表信息
	 *
	 * @param dataTableDto 目标数据表信息
	 * @param sql 查询SQL
	 * @param className sql查询结果存储对象类名
	 * @return List<IDataTableColumnEntity> {@link com.cingk.datameta.model.IDataTableColumnEntity}
	 */
	public List<IDataTableColumnEntity> getTableColumnEntityList(DataTableDto dataTableDto, String sql, String className)
		throws SQLException,
		InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {
		return getTableColumnEntityList(dataTableDto, sql, Class.forName(className));
	}

	/**
	 * 依据目标数据源获取数据库中表信息
	 *
	 * @param dataTableDto 目标数据表信息
	 * @param sql 查询SQL
	 * @param classzz sql查询结果存储对象类描述
	 * @return List<IDataTableColumnEntity> {@link com.cingk.datameta.model.IDataTableColumnEntity}
	 */
	public List<IDataTableColumnEntity> getTableColumnEntityList(DataTableDto dataTableDto, String sql, Class classzz)
		throws SQLException,
		InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {

		DataSourceDto dataSourceDto = dataTableDto.getDatabaseSourceDto();
		List<IDataTableColumnEntity> targetDataTableColumnEntityList = getTableColumnEntityList(dataSourceDto, sql, classzz);
		List<IDataTableColumnEntity> localDataTableColumnEntityList = Lists.newArrayList();
		for (IDataTableColumnEntity iDatabaseTableEntity : targetDataTableColumnEntityList) {
			DataTableColumnDto dataTableColumnDto = new DataTableColumnDto();
			BeanUtils.copyProperties(iDatabaseTableEntity, dataTableColumnDto);
			dataTableColumnDto.setDatabaseTableDto(dataTableDto);
			localDataTableColumnEntityList.add(dataTableColumnDto);
		}
		return localDataTableColumnEntityList;
	}

	/**
	 * 依据目标数据源获取数据库中表信息
	 *
	 * @param dataSourceDto 目标数据源信息
	 * @param sql 查询SQL
	 * @param clazz sql查询结果存储对象描述
	 * @return List<IDataTableColumnEntity> {@link com.cingk.datameta.model.IDataTableColumnEntity}
	 */
	public List<IDataTableColumnEntity> getTableColumnEntityList(DataSourceDto dataSourceDto, String sql, Class clazz)
		throws SQLException,
		InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
		List<Object> resultList = super.getResultSet(dataSourceDto, sql, clazz);
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
	 * @param dataSourceDto 目标数据源信息
	 * @param sql 查询SQL
	 * @param className sql查询结果存储对象类名
	 * @return List<IDataTableColumnEntity> {@link com.cingk.datameta.model.IDataTableColumnEntity}
	 */
	public List<IDataTableColumnEntity> getTableColumnEntityList(DataSourceDto dataSourceDto, String sql, String className)
		throws SQLException,
		InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {
		return getTableColumnEntityList(dataSourceDto,sql,Class.forName(className));
	}

}
