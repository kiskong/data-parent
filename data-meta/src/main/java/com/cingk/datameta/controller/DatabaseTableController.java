package com.cingk.datameta.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.dto.ResponseBodyDto;
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
@RequestMapping("/")
public class DatabaseTableController extends BaseRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseTableController.class);

    @Autowired
    private DataTableUtil dataTableUtil;
    @Autowired
    private DatabaseSourceService databaseSourceService;
    @Autowired
    private DatabaseTableService databaseTableService;

    @GetMapping("getDatabaseTable")
    public ResponseBodyDto getALLTables(Integer dataSourceId)  {
        DatabaseSourceDto databaseSourceDto = new DatabaseSourceDto();
        databaseSourceDto.setId(dataSourceId);
        DatabaseSourceEntity databaseSourceEntity = databaseSourceService.query(databaseSourceDto);
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
            ResponseBodyDto responseBodyDto = responseUtil.failure(ResponseEnum.CODE_FAIL.getCode());
            responseBodyDto.setExceptionTrace(e.toString());
            return responseBodyDto;
        }
    }


    @GetMapping("saveAllTable")
    public ResponseBodyDto addAllTable(Integer dataSourceId){
        ResponseBodyDto responseBodyDto = getALLTables(dataSourceId);
        boolean isFail = ResponseEnum.CODE_FAIL.getCode().equals(responseBodyDto.getStatus());
        if (isFail) return responseBodyDto;
        List<DatabaseTableEntity> databaseTableEntityList = responseBodyDto.getData();
        databaseTableService.saveAllTables(databaseTableEntityList);
        ResponseBodyDto saveResponseBodyDto = responseUtil.success(ResponseEnum.CODE_SUCCESS.getCode(), "保存数据成功");
        saveResponseBodyDto.setDataSize(databaseTableEntityList.size());
        return saveResponseBodyDto;
    }

}
