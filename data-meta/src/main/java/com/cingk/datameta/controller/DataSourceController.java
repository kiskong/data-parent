package com.cingk.datameta.controller;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.InterfaceEntity;
import com.cingk.datameta.model.ao.DataSourceAo;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.model.entity.DataSourceEntity;
import com.cingk.datameta.service.impl.DataSourceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
public class DataSourceController extends BaseRequestController {

    @Autowired
    private DataSourceService dataSourceService;

    @ApiOperation(value ="通过数据源名称获取数据源")
    @GetMapping("getDatabaseSourceByName")
    public ResponseDto getDatabaseSourceByName(@ApiParam(value = "数据源名称")String databaseName) {
        ResponseDto responseDto = responseUtil.failure();
        DataSourceDto dataSourceDto = new DataSourceDto();
        dataSourceDto.setDatabaseName(databaseName);
        DataSourceEntity dataSourceEntity = dataSourceService.query(dataSourceDto);
        if (dataSourceEntity == null) {
            responseDto.setResCode(ResponseEnum.CODE_MESSAGE_F9000.getCode());
            responseDto.setDescription("保存数据源出错");
            return responseDto;
        }
        BeanUtils.copyProperties(dataSourceEntity, dataSourceDto);
        return responseUtil.success(dataSourceDto);
    }

    @ApiOperation(value ="通过数据源标识获取数据源")
    @GetMapping("getDatabaseSourceById")
    public ResponseDto getDatabaseSourceById(@ApiParam(value = "数据源标识")Integer id) {
        ResponseDto responseDto = responseUtil.failure();
        DataSourceEntity dataSourceEntity = dataSourceService.queryById(id);
        if (dataSourceEntity == null) {
            responseDto.setResCode(ResponseEnum.CODE_MESSAGE_F9000.getCode());
            responseDto.setDescription("保存数据源出错");
            return responseDto;
        }
        DataSourceDto dataSourceDto = new DataSourceDto();
        BeanUtils.copyProperties(dataSourceEntity, dataSourceDto);
        return responseUtil.success(dataSourceDto);
    }

    @GetMapping("getAllDatabaseSource")
    public ResponseDto getAllDatabaseSource() {
        return responseUtil.success(dataSourceService.queryAll());
    }

    @PostMapping("getPageDatabaseSource")
    public ResponseDto getPageDatabaseSource(
            @RequestParam(defaultValue = "1") @ApiParam(value ="页码") String pageIndex,
            @RequestParam(defaultValue = "10") @ApiParam(value ="每页数量") String pageSize) {
        return responseUtil.success(dataSourceService.queryPage(pageIndex, pageSize));
    }

    @PostMapping("addDatabaseSource")
    public ResponseDto addDatabaseSource(
            @RequestBody @Validated DataSourceAo dataSourceAo, BindingResult bindingResult) {

        ResponseDto responseDto = responseUtil.failure();
        if (!responseUtil.validateParamFailure(responseDto, bindingResult)) {
            return responseDto;
        }

        InterfaceEntity databaseSourceEntity = dataSourceService.save(dataSourceAo);
        if (databaseSourceEntity == null) {
            responseDto.setResCode(ResponseEnum.CODE_MESSAGE_F9000.getCode());
            responseDto.setDescription("保存数据源出错");
            return responseDto;
        }

        DataSourceDto dataSourceDto = new DataSourceDto();
        BeanUtils.copyProperties(databaseSourceEntity, dataSourceDto);

        return responseUtil.success(dataSourceDto);
    }

    @DeleteMapping("delDatabaseSourceById")
    public ResponseDto deleteDatabaseSourceById(
            @NotNull @RequestParam(value = "id") @ApiParam(value = "数据源标识") Integer id) {
        DataSourceDto dataSourceDto = new DataSourceDto();
        dataSourceDto.setId(id);
        dataSourceService.delete(dataSourceDto);
        return responseUtil.success("删除成功");
    }


    @DeleteMapping("delDatabaseSourceByName")
    public ResponseDto deleteDatabaseSourceByName(
            @NotNull @RequestParam(value = "name") @ApiParam(value = "数据源名称") String name) {
        dataSourceService.deleteByName(name);
        return responseUtil.success("删除成功");
    }
}
