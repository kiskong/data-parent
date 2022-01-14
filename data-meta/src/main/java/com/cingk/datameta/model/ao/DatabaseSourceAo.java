package com.cingk.datameta.model.ao;

import javax.validation.constraints.NotBlank;

public class DatabaseSourceAo {

    private String databaseName;

    @NotBlank(message = "url不能为空")
    private String url;

    @NotBlank(message = "username不能为空")
    private String username;

    @NotBlank(message = "pazzword不能为空")
    private String pazzword;

    private String instruction;

    private Integer status;

    private DatabaseSourceAo(){}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }


    public String getPazzword() {
        return pazzword;
    }

    public void setPazzword(String pazzword) {
        this.pazzword = pazzword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

}
