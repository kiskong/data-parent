package com.cingk.datameta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.model.entity.DataTableEntity;
import com.cingk.datameta.service.intf.IDataSource;
import com.cingk.datameta.service.intf.IDataTable;
import com.cingk.datameta.utils.DataTableUtil;
import com.cingk.datameta.utils.SpringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Tag(name="数据表操作")
@RestController
@RequestMapping("/api")
public class DataTableController extends BaseRequestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataTableController.class);
    private DataTableUtil dataTableUtil;
    private IDataSource dataSourceService;
    private IDataTable dataTableService;

    @Operation(summary = "提取指定数据源的所有表")
    @GetMapping("getAllDatabaseTable")
    public ResponseDto getAllTables(@Parameter(description = "数据源标识", required = true) @NotNull Integer dataSourceId) {
        ResponseDto responseDto = dataSourceService.getDataSourceById(dataSourceId);
        if (!responseDto.successed()) {
            return responseDto;
        }

        DataSourceDto dataSourceDto = (DataSourceDto)responseDto.getData().get(0);

        //根据Url获取对应数据源的服务名
        String tableServiceName = dataTableUtil.getServiceNameByUrl(dataSourceDto);
        try {
            IDataTable tableService = SpringUtil.getBean(tableServiceName);
            List<DataTableEntity> tableList = tableService.getSrcAllTables(dataSourceDto);
            return responseUtil.success("查询数据成功", tableList);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            return responseUtil.failure(e);
        }
    }

    @Transactional()
    @Operation(summary = "提取指定数据源的所有表，并保存到数据库")
    @PutMapping("saveAllTable/{id}")
    public ResponseDto saveAllTable(@Parameter(description = "数据源标识", required = true) @PathVariable(value = "id") @NotNull Integer id) {
        ResponseDto responseDto = this.getAllTables(id);
        if (!responseDto.successed()) return responseDto;

        List<DataTableEntity> dataTableEntityList = responseDto.getData();
        dataTableService.saveAllTables(dataTableEntityList);
        ResponseDto saveResponseDto = responseUtil.success("保存数据成功");
        saveResponseDto.setDataSize(dataTableEntityList.size());
        return saveResponseDto;
    }

    @Autowired
    public void setDataTableUtil(DataTableUtil dataTableUtil) {
        this.dataTableUtil = dataTableUtil;
    }

    @Autowired
    public void setDataSourceService(IDataSource dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @Autowired
    public void setDataTableService(IDataTable dataTableService) {
        this.dataTableService = dataTableService;
    }
}
