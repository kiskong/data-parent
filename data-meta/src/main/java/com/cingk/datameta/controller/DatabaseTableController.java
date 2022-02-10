package com.cingk.datameta.controller;

import java.util.List;

import com.cingk.datameta.model.ao.DatabaseSourceAo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.model.entity.DatabaseSourceEntity;
import com.cingk.datameta.model.entity.DatabaseTableEntity;
import com.cingk.datameta.service.impl.DatabaseSourceService;
import com.cingk.datameta.service.impl.DatabaseTableService;
import com.cingk.datameta.service.intf.ITableService;
import com.cingk.datameta.utils.DataTableUtil;
import com.cingk.datameta.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class DatabaseTableController extends BaseRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseTableController.class);

    @Autowired
    private DataTableUtil dataTableUtil;
    @Autowired
    private DatabaseSourceService databaseSourceService;
    @Autowired
    private DatabaseTableService databaseTableService;

    @GetMapping("getAllDatabaseTable")
    public ResponseDto getALLTables(@ApiParam(value = "数据源标识") Integer dataSourceId)  {
        DatabaseSourceDto databaseSourceDto = new DatabaseSourceDto();
        databaseSourceDto.setId(dataSourceId);
        DatabaseSourceEntity databaseSourceEntity = databaseSourceService.queryById(dataSourceId);
        if (databaseSourceEntity == null){
            return responseUtil.failure(ResponseEnum.CODE_FAIL.getCode(),"数据源不存在");
        }
        BeanUtils.copyProperties(databaseSourceEntity, databaseSourceDto);
        //根据Url获取对应数据源的服务名
        String tableServiceName = dataTableUtil.getServiceNameByUrl(databaseSourceDto);
        try {
            ITableService tableService = SpringUtil.getBean(tableServiceName);
            List<DatabaseTableEntity> tableList = tableService.getAllTables(databaseSourceDto);
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
    public ResponseDto saveAllTable(@ApiParam(value = "数据源标识") @PathVariable(value = "id") Integer id){
        ResponseDto responseDto = getALLTables(id);
        boolean isFail = ResponseEnum.CODE_FAIL.getCode().equals(responseDto.getStatus());
        if (isFail) return responseDto;
        List<DatabaseTableEntity> databaseTableEntityList = responseDto.getData();
        databaseTableService.saveAllTables(databaseTableEntityList);
        ResponseDto saveResponseDto = responseUtil.success(ResponseEnum.CODE_SUCCESS.getCode(), "保存数据成功");
        saveResponseDto.setDataSize(databaseTableEntityList.size());
        return saveResponseDto;
    }

}
