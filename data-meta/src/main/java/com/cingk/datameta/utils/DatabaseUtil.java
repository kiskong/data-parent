package com.cingk.datameta.utils;

import com.google.common.collect.Lists;

import java.util.List;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.StrUtil;
import com.cingk.datameta.constant.enums.DatabaseDriverEnum;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.sql.DataSource;

@Component
public class DatabaseUtil {

    public static final String METHOD_NAME_SET = "set%s";
    public static final String DB_TYPE_MYSQL = "mysql";
    public static final String DB_TYPE_ORACLE = "oracle";

    public String getDriverByUrl(String url) {
        boolean isMysql = url.toLowerCase().contains(DB_TYPE_MYSQL);
        if (isMysql) return DatabaseDriverEnum.MYSQL.getValue(DB_TYPE_MYSQL);
        return "";
    }


    /**
     * 构造数据源连接信息
     * @param databaseSourceDto 数据源连接信息对象
     * @return See also {@link org.springframework.jdbc.datasource.SingleConnectionDataSource}
     */
    public DataSource getDataSource(DatabaseSourceDto databaseSourceDto) {
        return new SingleConnectionDataSource(
                databaseSourceDto.getUrl(),
                databaseSourceDto.getUsername(),
                databaseSourceDto.getPazzword(),
                true);
    }

    /**
     * 获取sql查询结果
     * @param databaseSourceDto 数据源信息
     * @param sql 查询sql
     * @param resultEntity 指定返回实体类名
     * @return List<Object> 与resultEntity类型一致
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public List<Object> getResultSet(DatabaseSourceDto databaseSourceDto,String sql,Class resultEntity)
        throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        DataSource dataSource = getDataSource(databaseSourceDto);
        try (Connection connection = dataSource.getConnection()) {
            return getResultSet(connection, sql, resultEntity);
        }
    }

    private List<Object> getResultSet(Connection connection, String sql, Class resultEntity)
        throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException{
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return getResultSet(rs, resultEntity);
    }

    private List<Object> getResultSet(ResultSet rs, Class resultEntity)
        throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        List<Object> columnEntity = Lists.newArrayList();
        while (rs.next()) {
            columnEntity.add(copyResultToEntity(rs, resultEntity));
        }
        return columnEntity;
    }


    /**
     * sql查询结果copy至指定的实体对象
     * @param resultSet sql查询结果集
     * @param resultEntity 实体类的描述
     * @return
     * @throws SQLException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object copyResultToEntity(ResultSet resultSet, Class resultEntity) throws SQLException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        //元数据
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        //实体属性
        Object instanceClass = resultEntity.newInstance();
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
        return instanceClass;
    }



    /**
     * 数据库字段转换为驼峰格式
     * @param columnName 字段名称
     * @return
     */
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
