package com.cingk.datameta.model.dto;

import com.cingk.datameta.model.IColumnEntity;


public class ColumnDto implements IColumnEntity {
    private Integer id;

    private Integer tabId;

    private Integer colId;

    private String colName;

    private String dataType;

    private Integer colLength;

    private Integer colDecimal;

    private DataSourceDto dataSourceDto;

    private TableDto tableDto;

    private String tabName;

    public Integer getColDecimal() {
        return colDecimal;
    }

    public void setColDecimal(Integer colDecimal) {
        this.colDecimal = colDecimal;
    }

    public Integer getColLength() {
        return colLength;
    }

    public void setColLength(Integer colLength) {
        this.colLength = colLength;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public Integer getColId() {
        return colId;
    }

    public void setColId(Integer colId) {
        this.colId = colId;
    }

    public Integer getTabId() {
        return tabId;
    }

    public void setTabId(Integer tabId) {
        this.tabId = tabId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DataSourceDto getDatabaseSourceDto() {
        return dataSourceDto;
    }

    public void setDatabaseSourceDto(DataSourceDto dataSourceDto) {
        this.dataSourceDto = dataSourceDto;
    }

    public TableDto getDatabaseTableDto() {
        return tableDto;
    }

    public void setDatabaseTableDto(TableDto tableDto) {
        this.tableDto = tableDto;
    }

    @Override
    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
}