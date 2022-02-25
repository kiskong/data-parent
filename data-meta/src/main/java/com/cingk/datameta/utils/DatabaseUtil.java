package com.cingk.datameta.utils;

import com.google.common.collect.Lists;

import java.util.List;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.StrUtil;
import com.cingk.datameta.constant.enums.DatabaseDriverEnum;
import com.cingk.datameta.model.dto.DataSourceDto;

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
        if (isMysql) {
            return DatabaseDriverEnum.MYSQL.getValue(DB_TYPE_MYSQL);
        }
        return "";
    }


    /**
     * 构造数据源连接信息
     *
     * @param dataSourceDto 数据源连接信息对象
     * @return See also {@link org.springframework.jdbc.datasource.SingleConnectionDataSource}
     */
    public DataSource getDataSource(DataSourceDto dataSourceDto) {
        return new SingleConnectionDataSource(
                dataSourceDto.getUrl(),
                dataSourceDto.getUsername(),
                dataSourceDto.getPazzword(),
                true);
    }

    /**
     * 获取sql查询结果
     *
     * @param dataSourceDto 数据源信息
     * @param sql           查询sql
     * @param resultEntity  指定返回实体类名
     * @return List<Object> 与resultEntity类型一致
     */
    public List<Object> getResultSet(DataSourceDto dataSourceDto, String sql, Class resultEntity) {
        DataSource dataSource = getDataSource(dataSourceDto);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getResultSet(connection, sql, resultEntity);
    }

    private List<Object> getResultSet(Connection connection, String sql, Class resultEntity) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getResultSet(rs, resultEntity);
    }

    private List<Object> getResultSet(ResultSet rs, Class resultEntity) {
        List<Object> columnEntity = Lists.newArrayList();
        while (true) {
            try {
                if (!rs.next()) break;
                columnEntity.add(copyResultToEntity(rs, resultEntity));
            } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        return columnEntity;
    }


    /**
     * sql查询结果copy至指定的实体对象
     *
     * @param resultSet    sql查询结果集
     * @param resultEntity 实体类的描述
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
            if (value == null) {
                continue;
            }
            String cameColumnName = getCameName(columnName);
            for (Field field : fields) {
                if (!field.getName().equalsIgnoreCase(cameColumnName)) {
                    continue;
                }
                String methodName = String.format(METHOD_NAME_SET, StrUtil.upperFirst(cameColumnName));
                Method method = instanceClass.getClass().getDeclaredMethod(methodName, value.getClass());
                method.invoke(instanceClass, value);
            }
        }
        return instanceClass;
    }

    private Integer getRowCount(Connection connection, String sql) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            return null;
        }
        return rs.getObject(1, Integer.class);
    }

    public Integer getRowCount(DataSourceDto dataSourceDto, String sql) throws SQLException {
        DataSource dataSource = getDataSource(dataSourceDto);
        try (Connection connection = dataSource.getConnection()) {
            return getRowCount(connection, sql);
        }
    }


    /**
     * 数据库字段转换为驼峰格式
     *
     * @param columnName 字段名称
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
