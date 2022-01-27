package com.cingk.datameta.utils;

import cn.hutool.core.util.StrUtil;
import com.cingk.datameta.constant.enums.DatabaseDriverEnum;
import com.cingk.datameta.model.IDataTableEntity;
import com.cingk.datameta.model.InterfaceEntity;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.entity.DatabaseTableEntity;
import com.google.common.collect.Lists;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.List;

@Component
public class DatabaseUtil {

    private static final String METHOD_NAME_SET = "set%s";
    private static final String MYSQL_TABLE_SERVICE = "MysqlTableService";
    private static final String ORACLE_TABLE_SERVICE = "OracleTableService";
    private static final String DB_TYPE_MYSQL = "mysql";
    private static final String DB_TYPE_ORACLE = "oracle";

    public String getDriverByUrl(String url) {
        boolean isMysql = url.toLowerCase().contains(DB_TYPE_MYSQL);
        if (isMysql) return DatabaseDriverEnum.MYSQL.getValue(DB_TYPE_MYSQL);
        return "";
    }

    public String getClassNameByUrl(String url){
        boolean isMysql = url.toLowerCase().contains(DB_TYPE_MYSQL);
        if (isMysql) return MYSQL_TABLE_SERVICE;
        boolean isOracle = url.toLowerCase().contains(DB_TYPE_ORACLE);
        if (isOracle) return ORACLE_TABLE_SERVICE;
        return "";
    }

    public String getClassNameByUrl(DatabaseSourceDto databaseSourceDto){
        return getClassNameByUrl(databaseSourceDto.getUrl());
    }

    public DataSource getDataSource(DatabaseSourceDto databaseSourceDto) {
        return new SingleConnectionDataSource(
                databaseSourceDto.getUrl(),
                databaseSourceDto.getUsername(),
                databaseSourceDto.getPazzword(),
                true);
    }


    public List<DatabaseTableEntity> getDataTableEntityList(InterfaceEntity databaseSourceDto, String sql,String className) throws SQLException,
            InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        List<IDataTableEntity> dataTableEntityList =
                getDataTableEntityList(databaseSourceDto, sql, Class.forName(className));
        return getDataTableEntityList((DatabaseSourceDto) databaseSourceDto, dataTableEntityList);
    }

    public List<DatabaseTableEntity> getDataTableEntityList(DatabaseSourceDto databaseSourceDto, List<IDataTableEntity> dataTableEntityList) {
        List<DatabaseTableEntity> databaseTableEntityList = Lists.newArrayList();
        if (dataTableEntityList == null) {
            return databaseTableEntityList;
        }
        dataTableEntityList.forEach(dataTableEntity -> {
            DatabaseTableEntity databaseTableEntity = new DatabaseTableEntity();
            databaseTableEntity.setTabName(dataTableEntity.getTabName());
            databaseTableEntity.setSchemaName(dataTableEntity.getSchema());
            databaseTableEntity.setDatabaseSourceId(databaseSourceDto.getId());
            databaseTableEntityList.add(databaseTableEntity);
        });
        return databaseTableEntityList;
    }

    public List<IDataTableEntity> getDataTableEntityList(InterfaceEntity databaseSourceDto, String sql, Class clazz) throws SQLException,
            InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        return getDataTableEntityList((DatabaseSourceDto) databaseSourceDto, sql, clazz);
    }

    public List<IDataTableEntity> getDataTableEntityList(DatabaseSourceDto databaseSourceDto, String sql, Class clazz) throws SQLException,
            InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        DataSource dataSource = getDataSource(databaseSourceDto);
        try (Connection connection = dataSource.getConnection()) {
            return getDataTableEntityList(connection, sql, clazz);
        }
    }

    public List<IDataTableEntity> getDataTableEntityList(Connection connection, String sql, Class clazz) throws SQLException,
            InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return getDataTableEntityList(rs, clazz);
    }

    public List<IDataTableEntity> getDataTableEntityList(ResultSet rs, Class clazz) throws SQLException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        List<IDataTableEntity> databaseTableEntityList = Lists.newArrayList();
        while (rs.next()) {
            IDataTableEntity dataTableEntity = copyResultToEntity(rs, clazz);
            databaseTableEntityList.add(dataTableEntity);
        }
        return databaseTableEntityList;
    }

    public IDataTableEntity copyResultToEntity(ResultSet resultSet, Class clazz) throws SQLException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        //元数据
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        //实体属性
        Object instanceClass = clazz.newInstance();
        Field[] fields = instanceClass.getClass().getDeclaredFields();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = resultSetMetaData.getColumnName(i);
            Object value = resultSet.getObject(columnName);
            if (value == null) continue;
            String cameColumnName = getCameName(columnName);
            for (Field field : fields) {
                if (!field.getName().equalsIgnoreCase(cameColumnName)) continue;
                String methodName = String.format(METHOD_NAME_SET, StrUtil.upperFirst(cameColumnName));
                Method method = instanceClass.getClass().getDeclaredMethod(methodName, value.getClass());
                method.invoke(instanceClass, value);
            }
        }
        return (IDataTableEntity) instanceClass;
    }


    public String getCameName(String columnName) {
        boolean underline = StrUtil.contains(columnName, StrUtil.UNDERLINE);
        return underline ? getCameNameUnderLine(columnName) : getCameNameUnderLineNot(columnName);
    }

    public String getCameNameUnderLine(String columnName) {
        return StrUtil.toCamelCase(columnName);
    }

    public String getCameNameUnderLineNot(String columnName) {
        return StrUtil.toCamelCase(columnName.toLowerCase());
    }


}
