package com.cingk.datameta.controller;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.dto.ResponseBodyDto;
import com.cingk.datameta.model.entity.DatabaseSourceEntity;
import com.cingk.datameta.model.entity.DatabaseTableEntity;
import com.cingk.datameta.service.impl.DatabaseSourceService;
import com.cingk.datameta.service.intf.ITableService;
import com.cingk.datameta.utils.DatabaseUtil;
import com.cingk.datameta.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class DatabaseTableController extends BaseRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseTableController.class);

    @Autowired
    private DatabaseUtil databaseUtil;
    @Autowired
    private DatabaseSourceService databaseSourceService;

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
        String tableServiceName = databaseUtil.getClassNameByUrl(databaseSourceDto);
        //todo 用的mysql测试，待抽象
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

}
