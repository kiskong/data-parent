package com.cingk.datameta.constant.enums;

public enum ServiceEnum {

	//table service
	MYSQL_TAB_SERVICE("mysql_table", "mysqlTableService"),
	ORACLE_TAB_SERVICE("oracle_table", "oracleTableService"),

	//table column service
	MYSQL_COL_SERVICE("mysql_tab_column", "mysqlTableColumnService"),
	ORACLE_COL_SERVICE("oracle_tab_column", "oracleTableColumnService");

	private final String key;
	private final String value;

	ServiceEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}


	public String getValue() {
		return this.value;
	}

	public String getValue(String key) {
		for (ServiceEnum driver : ServiceEnum.values()) {
			if (driver.key.equals(key)) {
				return driver.value;
			}
		}
		return "";
	}
}
