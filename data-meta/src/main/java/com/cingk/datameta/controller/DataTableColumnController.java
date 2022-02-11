package com.cingk.datameta.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cingk.datameta.model.ao.DataTableColumnAo;
import com.cingk.datameta.model.entity.DataTableColumnEntity;
import com.cingk.datameta.model.entity.DataTableEntity;
import com.cingk.datameta.service.intf.IDataSource;
import com.cingk.datameta.service.intf.IDataTable;
import com.cingk.datameta.utils.DataTableUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
    private DataTableUtil dataTableUtil;
    @Autowired
    private IDataSource dataSourceService;

    @Autowired
    private IDataTableColumn dataTableColumnService;

    @GetMapping("getDataTableColumn")
    public ResponseDto getTabColumn(
            @ApiParam(value = "数据源标识", required = true) @NotNull Integer dataSourceId,
            @ApiParam(value = "模式名称", required = true) @NotNull String schema,
            @ApiParam(value = "数据表名", required = true) @NotNull String tableName) {
        DataSourceDto dataSourceDto = new DataSourceDto();
        ResponseDto responseDto = dataSourceService.getDataSourceById(dataSourceId, dataSourceDto);
        if (!responseDto.successed()) return responseDto;

        String tableColumnServiceName = dataTableColumnUtil.getServiceNameByUrl(dataSourceDto);
        IDataTableColumn columnService = SpringUtil.getBean(tableColumnServiceName);
        try {
            List<IDataTableColumnEntity> dataTableColumnEntityList = columnService.getTableColumn(dataSourceDto, schema, tableName);
            return responseUtil.success("查询数据成功", dataTableColumnEntityList);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            return responseUtil.failure(e);
        }
    }

    @Transactional
    @ApiOperation(value = "采集模式名下指定数据表的字段", notes = "")
    @PostMapping("/fetchAndSaveTabColumnByTableName")
    public ResponseDto fetchAndSaveTabColumnByTableName(@RequestBody @Valid DataTableColumnAo dataTableColumnAo, BindingResult bindingResult) {
        ResponseDto responseDto = responseUtil.failure();
        if (!responseUtil.validateParamFailure(responseDto, bindingResult)) {
            return responseDto;
        }
        return fetchAndSaveTabColumnByTableName(dataTableColumnAo);
    }

    @Transactional
    @ApiOperation(value = "采集模式名下所有数据表的字段", notes = "")
    @PostMapping("/fetchAndSaveTabColumn")
    public ResponseDto fetchAndSaveTabColumn(@RequestBody @Valid DataTableColumnAo dataTableColumnAo, BindingResult bindingResult) {
        ResponseDto responseDto = responseUtil.failure();
        if (!responseUtil.validateParamFailure(responseDto, bindingResult)) {
            return responseDto;
        }
        Integer dataSourceId = dataTableColumnAo.getDataSourceId();
        DataSourceDto dataSourceDto = new DataSourceDto();
        responseDto = dataSourceService.getDataSourceById(dataSourceId, dataSourceDto);
        if (!responseDto.successed()) return responseDto;
        String tableServiceName = dataTableUtil.getServiceNameByUrl(dataSourceDto);
        IDataTable tableService = SpringUtil.getBean(tableServiceName);
        List<DataTableEntity> dataTableEntityList = tableService.getAllTables(dataSourceDto);

        List<String> tableNames = dataTableEntityList.stream().map(DataTableEntity::getTabName).collect(Collectors.toList());
        dataTableColumnAo.setTableNames(tableNames.toArray(new String[0]));
        return this.fetchAndSaveTabColumnByTableName(dataTableColumnAo);
    }


    private ResponseDto fetchAndSaveTabColumnByTableName(DataTableColumnAo dataTableColumnAo) {
        Integer dataSourceId = dataTableColumnAo.getDataSourceId();
        String schemaName = dataTableColumnAo.getSchemaName();
        String[] tableNames = dataTableColumnAo.getTableNames();

        DataSourceDto dataSourceDto = new DataSourceDto();
        ResponseDto responseDto = dataSourceService.getDataSourceById(dataSourceId, dataSourceDto);
        if (!responseDto.successed()) return responseDto;

        String tableColumnServiceName = dataTableColumnUtil.getServiceNameByUrl(dataSourceDto);
        IDataTableColumn columnService = SpringUtil.getBean(tableColumnServiceName);

        Arrays.asList(tableNames).forEach(tableName -> {
            List<IDataTableColumnEntity> dataTableColumnEntityList = columnService.getTableColumn(dataSourceDto, schemaName, tableName);
            dataTableColumnService.saveAllTableColumn((List) dataTableColumnEntityList);
        });
        return responseUtil.success("采集数据库表字段成功");
    }

}
