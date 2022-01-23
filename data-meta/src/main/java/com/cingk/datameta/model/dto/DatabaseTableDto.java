package com.cingk.datameta.model.dto;

import com.cingk.datameta.model.InterfaceEntity;

import java.time.Instant;

public class DatabaseTableDto implements InterfaceEntity {
    private Integer id;

    private String tabName;

    private Integer databaseSourceId;

    private String instruction;

    private String aliasName;

    private String version;

    private Instant creatTime;

    public Instant getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Instant creatTime) {
        this.creatTime = creatTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Integer getDatabaseSourceId() {
        return databaseSourceId;
    }

    public void setDatabaseSourceId(Integer databaseSourceId) {
        this.databaseSourceId = databaseSourceId;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}