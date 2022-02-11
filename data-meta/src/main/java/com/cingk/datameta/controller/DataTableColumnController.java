package com.cingk.datameta.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.DataTableDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.model.entity.DataSourceEntity;
import com.cingk.datameta.service.impl.DataSourceService;
import com.cingk.datameta.service.intf.IDataTableColumn;
import com.cingk.datameta.utils.DataTableColumnUtil;
import com.cingk.datameta.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-02-02
 */
@RestController
@RequestMapping("/api")
public class DataTableColumnController extends BaseRequestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataTableColumnController.class);

	@Autowired
	private DataTableColumnUtil dataTableColumnUtil;
	@Autowired
	private DataSourceService dataSourceService;

	@GetMapping("getDataTableColumn")
	public ResponseDto getTabColumn(Integer dataSourceId, String schema, String tableName) {
		DataSourceDto dataSourceDto = new DataSourceDto();
		dataSourceDto.setId(dataSourceId);
		DataSourceEntity dataSourceEntity = dataSourceService.query(dataSourceDto);
		if (dataSourceEntity == null) {
			return responseUtil.failure(ResponseEnum.CODE_FAIL.getCode(), "数据源不存在");
		}
		BeanUtils.copyProperties(dataSourceEntity, dataSourceDto);
		DataTableDto dataTableDto = new DataTableDto();
		dataTableDto.setSchemaName(schema);
		dataTableDto.setTabName(tableName);
		dataTableDto.setDatabaseSourceDto(dataSourceDto);
		String tableColumnServiceName = dataTableColumnUtil.getServiceNameByUrl(dataTableDto);
		IDataTableColumn columnService = SpringUtil.getBean(tableColumnServiceName);
		try {
			List<IDataTableColumnEntity> dataTableColumnEntityList = columnService.getTableColumn(dataTableDto);
			return responseUtil.success(ResponseEnum.CODE_SUCCESS.getCode(), "查询数据成功", dataTableColumnEntityList);
		} catch (RuntimeException e) {
			LOGGER.error(e.getMessage(), e);
			ResponseDto responseDto = responseUtil.failure(ResponseEnum.CODE_FAIL.getCode());
			responseDto.setExceptionTrace(e.toString());
			return responseDto;
		}
	}
}
