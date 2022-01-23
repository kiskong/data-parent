package com.cingk.datameta.model.entity;

import com.cingk.datameta.model.InterfaceEntity;

import java.time.Instant;

public class MysqlTableEntity implements InterfaceEntity {

    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String tableType;
    private String engine;
    private String version;
    private String rowFormat;
    private Integer tableRows;
    private Integer avgRowLength;
    private Integer dataLenght;
    private Integer maxDataLenght;
    private Integer indexLength;
    private Integer dataFree;
    private Integer autoIncrement;
    private Instant createTime;
    private Instant updateTime;
    private Instant checkTime;
    private String tableCollation;
    private Integer checksum;
    private String createOptions;
    private String tableComment;

    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRowFormat() {
        return rowFormat;
    }

    public void setRowFormat(String rowFormat) {
        this.rowFormat = rowFormat;
    }

    public Integer getTableRows() {
        return tableRows;
    }

    public void setTableRows(Integer tableRows) {
        this.tableRows = tableRows;
    }

    public Integer getAvgRowLength() {
        return avgRowLength;
    }

    public void setAvgRowLength(Integer avgRowLength) {
        this.avgRowLength = avgRowLength;
    }

    public Integer getDataLenght() {
        return dataLenght;
    }

    public void setDataLenght(Integer dataLenght) {
        this.dataLenght = dataLenght;
    }

    public Integer getMaxDataLenght() {
        return maxDataLenght;
    }

    public void setMaxDataLenght(Integer maxDataLenght) {
        this.maxDataLenght = maxDataLenght;
    }

    public Integer getIndexLength() {
        return indexLength;
    }

    public void setIndexLength(Integer indexLength) {
        this.indexLength = indexLength;
    }

    public Integer getDataFree() {
        return dataFree;
    }

    public void setDataFree(Integer dataFree) {
        this.dataFree = dataFree;
    }

    public Integer getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(Integer autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Instant getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Instant checkTime) {
        this.checkTime = checkTime;
    }

    public String getTableCollation() {
        return tableCollation;
    }

    public void setTableCollation(String tableCollation) {
        this.tableCollation = tableCollation;
    }

    public Integer getChecksum() {
        return checksum;
    }

    public void setChecksum(Integer checksum) {
        this.checksum = checksum;
    }

    public String getCreateOptions() {
        return createOptions;
    }

    public void setCreateOptions(String createOptions) {
        this.createOptions = createOptions;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
}
