package com.cingk.datameta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.entity.ColumnEntity;
import com.cingk.datameta.model.entity.TableEntity;
import com.cingk.datameta.utils.ColumnUtil;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-02-28
 */
@Service
public class TargetColumnService {

	private ColumnUtil columnUtil;

	@Autowired
	public void setColumnUtil(ColumnUtil columnUtil) {
		this.columnUtil = columnUtil;
	}

	public ColumnEntity getColumn(DataSourceDto dataSourceDto, TableEntity tableEntity){

		return null;
	}
}
