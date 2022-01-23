package com.cingk.datameta.model.dto;

import com.cingk.datameta.model.InterfaceEntity;


public class DatabaseTableColumnDto implements InterfaceEntity {
    private Integer id;

    private Integer tabId;

    private Integer colId;

    private String colName;

    private String colType;

    private Integer colLength;

    private Integer colDecimal;

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

    public String getColType() {
        return colType;
    }

    public void setColType(String colType) {
        this.colType = colType;
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
}