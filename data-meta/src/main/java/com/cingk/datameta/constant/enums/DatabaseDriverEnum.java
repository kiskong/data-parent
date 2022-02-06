package com.cingk.datameta.constant.enums;

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
        for (DatabaseDriverEnum driver : DatabaseDriverEnum.values()) {
            if (driver.key.equals(key)) return driver.value;
        }
        return "";
    }

}
