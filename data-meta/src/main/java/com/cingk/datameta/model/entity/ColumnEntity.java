package com.cingk.datameta.model.entity;

import com.cingk.datameta.model.IColumnEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "database_table_column")
public class ColumnEntity implements IColumnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "tab_id")
    private Integer tabId;

    @Column(name = "col_id")
    private Integer colId;

    @Column(name = "col_name")
    private String colName;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "col_length")
    private Integer colLength;

    @Column(name = "col_decimal")
    private Integer colDecimal;

    @Column(name = "tab_name")
    private String tabName;

    @Column(name = "instruction")
    private String instruction;

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

    public String getTabName() {
        return this.tabName;
    }
    public void setTabName(String tabName) {
        this.tabName=tabName;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}