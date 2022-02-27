package com.cingk.datameta.model.entity;

import com.cingk.datameta.model.ITableEntity;
import java.math.BigInteger;
import java.sql.Timestamp;

public class MysqlTableEntity implements ITableEntity {

    public MysqlTableEntity(){

    }

    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String tableType;
    private String engine;
    private Long version;
    private String rowFormat;
    private BigInteger tableRows;
    private BigInteger avgRowLength;
    private BigInteger dataLength;
    private BigInteger maxDataLength;
    private BigInteger indexLength;
    private BigInteger dataFree;
    private BigInteger autoIncrement;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp checkTime;
    private String tableCollation;
    private BigInteger checksum;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getRowFormat() {
        return rowFormat;
    }

    public void setRowFormat(String rowFormat) {
        this.rowFormat = rowFormat;
    }

    public BigInteger getTableRows() {
        return tableRows;
    }

    public void setTableRows(BigInteger tableRows) {
        this.tableRows = tableRows;
    }

    public BigInteger getAvgRowLength() {
        return avgRowLength;
    }

    public void setAvgRowLength(BigInteger avgRowLength) {
        this.avgRowLength = avgRowLength;
    }

    public BigInteger getDataLength() {
        return dataLength;
    }

    public void setDataLength(BigInteger dataLength) {
        this.dataLength = dataLength;
    }

    public BigInteger getMaxDataLength() {
        return maxDataLength;
    }

    public void setMaxDataLength(BigInteger maxDataLength) {
        this.maxDataLength = maxDataLength;
    }

    public BigInteger getIndexLength() {
        return indexLength;
    }

    public void setIndexLength(BigInteger indexLength) {
        this.indexLength = indexLength;
    }

    public BigInteger getDataFree() {
        return dataFree;
    }

    public void setDataFree(BigInteger dataFree) {
        this.dataFree = dataFree;
    }

    public BigInteger getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(BigInteger autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
    }

    public String getTableCollation() {
        return tableCollation;
    }

    public void setTableCollation(String tableCollation) {
        this.tableCollation = tableCollation;
    }

    public BigInteger getChecksum() {
        return checksum;
    }

    public void setChecksum(BigInteger checksum) {
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


    @Override
    public String getSchema() {
        return this.tableSchema;
    }

    @Override
    public String getTabName() {
        return this.tableName;
    }

    @Override
    public String getTabType(){
        return this.tableType;
    }
}
