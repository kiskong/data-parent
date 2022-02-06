package com.cingk.datameta.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.dto.DatabaseTableDto;
import com.cingk.datameta.model.dto.ResponseBodyDto;
import com.cingk.datameta.model.entity.DatabaseSourceEntity;
import com.cingk.datameta.service.impl.DatabaseSourceService;
import com.cingk.datameta.service.intf.IColumnService;
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
@RequestMapping("/")
public class DatabaseTableColumnController extends BaseRequestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseTableColumnController.class);


	@Autowired
	private DataTableColumnUtil dataTableColumnUtil;
	@Autowired
	private DatabaseSourceService databaseSourceService;

	@GetMapping("getDataTableColumn")
	public ResponseBodyDto getTabColumn(Integer dataSourceId, String schema, String tableName) {
		DatabaseSourceDto databaseSourceDto = new DatabaseSourceDto();
		databaseSourceDto.setId(dataSourceId);
		DatabaseSourceEntity databaseSourceEntity = databaseSourceService.query(databaseSourceDto);
		if (databaseSourceEntity == null) {
			return responseUtil.failure(ResponseEnum.CODE_FAIL.getCode(), "数据源不存在");
		}
		BeanUtils.copyProperties(databaseSourceEntity, databaseSourceDto);
		DatabaseTableDto databaseTableDto = new DatabaseTableDto();
		databaseTableDto.setSchemaName(schema);
		databaseTableDto.setTabName(tableName);
		databaseTableDto.setDatabaseSourceDto(databaseSourceDto);
		String tableColumnServiceName = dataTableColumnUtil.getServiceNameByUrl(databaseTableDto);
		IColumnService columnService = SpringUtil.getBean(tableColumnServiceName);
		try {
			List<IDataTableColumnEntity> dataTableColumnEntityList = columnService.getTableColumn(databaseTableDto);
			return responseUtil.success(ResponseEnum.CODE_SUCCESS.getCode(), "查询数据成功", dataTableColumnEntityList);
		} catch (RuntimeException e) {
			LOGGER.error(e.getMessage(), e);
			ResponseBodyDto responseBodyDto = responseUtil.failure(ResponseEnum.CODE_FAIL.getCode());
			responseBodyDto.setExceptionTrace(e.toString());
			return responseBodyDto;
		}
	}
}
