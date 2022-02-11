package com.cingk.datameta.model.ao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(description = "采集数据表字段")
public class DataTableColumnAo {

    @ApiModelProperty(value = "数据源标识")
    @NotNull(message = "dataSourceId不能为空")
    private Integer dataSourceId;

    @NotBlank(message = "schemaName不能为空")
    @ApiModelProperty(value = "模式名")
    private String schemaName;

    @ApiModelProperty(value = "数据表名")
    private String[] tableNames;

    public Integer getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Integer dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String[] getTableNames() {
        return tableNames;
    }

    public void setTableNames(String[] tableNames) {
        this.tableNames = tableNames;
    }
}
