package com.cingk.datameta.model.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;

@Schema(name = "数据源")
public class DataSourceAo {

    /**
     * 数据源名称
     *
     */
    @Schema(name="databaseName" ,description = "数据源名称")
    private String databaseName;

    /**
     * 数据源连接
     *
     */
    @Schema(name="url",description = "数据源连接")
    @NotBlank(message = "url不能为空")
    private String url;

    /**
     * 数据源访问用户
     *
     */
    @Schema(name="username",description = "数据源访问用户")
    @NotBlank(message = "username不能为空")
    private String username;

    /**
     * 数据源访问用户密码
     *
     */
    @Schema(name="pazzword",description = "数据源访问用户密码")
    @NotBlank(message = "pazzword不能为空")
    private String pazzword;

    /**
     * 备注
     *
     */
    @Schema(name="instruction",description = "备注")
    private String instruction;

    /**
     * 状态:0-停用;1-启用
     *
     */
    @Schema(name="status",description = "状态:0-停用;1-启用")
    private Integer status;

    private DataSourceAo(){}

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
