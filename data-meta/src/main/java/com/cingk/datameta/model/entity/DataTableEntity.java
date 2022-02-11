package com.cingk.datameta.model.entity;

import com.cingk.datameta.model.InterfaceEntity;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "database_table")
public class DataTableEntity implements InterfaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tab_id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "tab_name")
    private String tabName;

    @Column(name = "database_source_id")
    private Integer databaseSourceId;

    @Column(name= "schemaName")
    private String schemaName;

    @Lob
    @Column(name = "instruction")
    private String instruction;

    @Lob
    @Column(name = "alias_name")
    private String aliasName;

    @Lob
    @Column(name = "version")
    private String version;

    @Column(name = "creat_time")
    private Instant creatTime;

    @Column(name = "tab_type")
    private String tabType;

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

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTabType() {
        return tabType;
    }

    public void setTabType(String tabType) {
        this.tabType = tabType;
    }
}