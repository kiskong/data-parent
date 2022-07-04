package com.cingk.datameta.constant.enums;

import java.util.Arrays;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

public enum DatabaseDriverEnum {

	MYSQL("mysql", "com.mysql.cj.jdbc.Driver"),
	ORACLE("oracle", "oracle.jdbc.Driver"),
	MSSQL("mssql", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),
	IBMDB2("db2", "com.ibm.db2.jcc.DB2Driver"),
	POSTGRESQL("postgresql", "org.postgresql.Driver");
	private final String key;
	private final String value;

	DatabaseDriverEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getValue(String key) {
		DatabaseDriverEnum driverEnum = databaseDriverEnum(key);
		return ObjectUtil.isNull(driverEnum) ? StrUtil.EMPTY : driverEnum.value;
	}

	private DatabaseDriverEnum databaseDriverEnum(String key) {
		return Arrays.stream(DatabaseDriverEnum.values())
			.filter(driver -> driver.key.equals(key))
			.findFirst().orElse(null);
	}

}
