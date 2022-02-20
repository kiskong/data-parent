package com.cingk.datameta.model.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(name = "采集数据表字段")
public class DataTableColumnAo {

	@Schema(name = "dataSourceId", description = "数据源标识")
	@NotNull(message = "dataSourceId不能为空")
	private Integer dataSourceId;

	@NotBlank(message = "schemaName不能为空")
	@Schema(name = "schemaName", description = "模式名")
	private String schemaName;

	@NotEmpty(message = "tableNames不能为空")
	@Schema(name = "tableNames", description = "数据表名")
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
