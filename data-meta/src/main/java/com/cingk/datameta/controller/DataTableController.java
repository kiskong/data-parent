package com.cingk.datameta.controller;

import java.util.List;

import com.cingk.datameta.service.intf.IDataSource;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.model.entity.DataSourceEntity;
import com.cingk.datameta.model.entity.DataTableEntity;
import com.cingk.datameta.service.intf.IDataTable;
import com.cingk.datameta.utils.DataTableUtil;
import com.cingk.datameta.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
public class DataTableController extends BaseRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataTableController.class);

    @Autowired
    private DataTableUtil dataTableUtil;
    @Autowired
    private IDataSource dataSourceService;
    @Autowired
    private IDataTable dataTableService;

    @ApiOperation(value = "提取指定数据源的所有表")
    @GetMapping("getAllDatabaseTable")
    public ResponseDto getALLTables(@ApiParam(value = "数据源标识",required = true) @NotNull Integer dataSourceId)  {
        DataSourceDto dataSourceDto = new DataSourceDto();
        dataSourceDto.setId(dataSourceId);
        DataSourceEntity dataSourceEntity = dataSourceService.queryById(dataSourceId);
        if (dataSourceEntity == null){
            return responseUtil.failure(ResponseEnum.CODE_FAIL.getCode(),"数据源不存在");
        }
        BeanUtils.copyProperties(dataSourceEntity, dataSourceDto);
        //根据Url获取对应数据源的服务名
        String tableServiceName = dataTableUtil.getServiceNameByUrl(dataSourceDto);
        try {
            IDataTable tableService = SpringUtil.getBean(tableServiceName);
            List<DataTableEntity> tableList = tableService.getAllTables(dataSourceDto);
            return responseUtil.success(ResponseEnum.CODE_SUCCESS.getCode(), "查询数据成功", tableList);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            ResponseDto responseDto = responseUtil.failure(ResponseEnum.CODE_FAIL.getCode());
            responseDto.setExceptionTrace(ExceptionUtils.getStackTrace(e));
            return responseDto;
        }
    }

    @ApiOperation(value = "提取指定数据源的所有表，并保存到数据库", notes = "")
    @PutMapping("saveAllTable/{id}")
    public ResponseDto saveAllTable(@ApiParam(value = "数据源标识",required = true) @PathVariable(value = "id") @NotNull Integer id){
        ResponseDto responseDto = getALLTables(id);
        boolean isFail = ResponseEnum.CODE_FAIL.getCode().equals(responseDto.getStatus());
        if (isFail) return responseDto;
        List<DataTableEntity> dataTableEntityList = responseDto.getData();
        dataTableService.saveAllTables(dataTableEntityList);
        ResponseDto saveResponseDto = responseUtil.success(ResponseEnum.CODE_SUCCESS.getCode(), "保存数据成功");
        saveResponseDto.setDataSize(dataTableEntityList.size());
        return saveResponseDto;
    }

}
