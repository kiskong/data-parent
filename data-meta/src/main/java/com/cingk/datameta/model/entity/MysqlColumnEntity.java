package com.cingk.datameta.model.entity;

import com.cingk.datameta.model.IColumnEntity;

/**
 * (mysql columns )表实体类
 *
 * @author lvkc
 * @since 2022-01-30 20:15:36
 */
@SuppressWarnings("serial")
public class MysqlColumnEntity implements IColumnEntity {

	private String tableCatalog;

	private String tableSchema;

	private String tableName;

	private String columnName;

	private Long ordinalPosition;

	private String columnDefault;

	private String isNullable;

	private String dataType;

	private Long characterMaximumLength;

	private Long characterOctetLength;

	private Long numericPrecision;

	private Long numericScale;

	private Long datetimePrecision;

	private String characterSetName;

	private String collationName;

	private String columnType;

	private String columnKey;

	private String extra;
	private String privileges;
	private String columnComment;
	private String generationExpression;

	private Long srsId;


	public Long getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(Long ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}


	public Long getNumericPrecision() {
		return numericPrecision;
	}

	public void setNumericPrecision(Long numericPrecision) {
		this.numericPrecision = numericPrecision;
	}

	public Long getNumericScale() {
		return numericScale;
	}

	public void setNumericScale(Long numericScale) {
		this.numericScale = numericScale;
	}

	public Long getDatetimePrecision() {
		return datetimePrecision;
	}

	public void setDatetimePrecision(Long datetimePrecision) {
		this.datetimePrecision = datetimePrecision;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Long getSrsId() {
		return srsId;
	}

	public void setSrsId(Long srsId) {
		this.srsId = srsId;
	}

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

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnDefault() {
		return columnDefault;
	}

	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	@Override
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Long getCharacterMaximumLength() {
		return characterMaximumLength;
	}

	public void setCharacterMaximumLength(Long characterMaximumLength) {
		this.characterMaximumLength = characterMaximumLength;
	}

	public Long getCharacterOctetLength() {
		return characterOctetLength;
	}

	public void setCharacterOctetLength(Long characterOctetLength) {
		this.characterOctetLength = characterOctetLength;
	}

	public String getCharacterSetName() {
		return characterSetName;
	}

	public void setCharacterSetName(String characterSetName) {
		this.characterSetName = characterSetName;
	}

	public String getCollationName() {
		return collationName;
	}

	public void setCollationName(String collationName) {
		this.collationName = collationName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getGenerationExpression() {
		return generationExpression;
	}

	public void setGenerationExpression(String generationExpression) {
		this.generationExpression = generationExpression;
	}


	@Override
	public Integer getId() {
		return null;
	}

	@Override
	public Integer getTabId() {
		return null;
	}

	@Override
	public Integer getColId() {
		return ordinalPosition == null ? null : ordinalPosition.intValue();
	}

	@Override
	public String getColName() {
		return getColumnName();
	}

	@Override
	public Integer getColLength() {
		return numericPrecision == null ? null : numericPrecision.intValue();
	}

	@Override
	public Integer getColDecimal() {
		return numericScale == null ? null : numericScale.intValue();
	}

	@Override
	public String getTabName() {
		return this.tableName;
	}
}

