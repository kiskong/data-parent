package com.cingk.datameta.controller;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.InterfaceEntity;
import com.cingk.datameta.model.ao.DatabaseSourceAo;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.model.entity.DatabaseSourceEntity;
import com.cingk.datameta.service.impl.DatabaseSourceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
public class DatabaseSourceController extends BaseRequestController {

    @Autowired
    private DatabaseSourceService databaseSourceService;

    @ApiOperation(value ="通过数据源名称获取数据源")
    @GetMapping("getDatabaseSourceByName")
    public ResponseDto getDatabaseSourceByName(String databaseName) {
        ResponseDto responseDto = responseUtil.failure();
        DatabaseSourceDto databaseSourceDto = new DatabaseSourceDto();
        databaseSourceDto.setDatabaseName(databaseName);
        DatabaseSourceEntity databaseSourceEntity = databaseSourceService.query(databaseSourceDto);
        if (databaseSourceEntity == null) {
            responseDto.setResCode(ResponseEnum.CODE_MESSAGE_F9000.getCode());
            responseDto.setDescription("保存数据源出错");
            return responseDto;
        }

        BeanUtils.copyProperties(databaseSourceEntity, databaseSourceDto);
        return responseUtil.success(databaseSourceDto);
    }
    @ApiOperation(value ="通过数据源标识获取数据源")
    @GetMapping("getDatabaseSourceById")
    public ResponseDto getDatabaseSourceById(Integer id) {
        ResponseDto responseDto = responseUtil.failure();
        DatabaseSourceEntity databaseSourceEntity = databaseSourceService.queryById(id);
        if (databaseSourceEntity == null) {
            responseDto.setResCode(ResponseEnum.CODE_MESSAGE_F9000.getCode());
            responseDto.setDescription("保存数据源出错");
            return responseDto;
        }
        DatabaseSourceDto databaseSourceDto = new DatabaseSourceDto();
        BeanUtils.copyProperties(databaseSourceEntity, databaseSourceDto);
        return responseUtil.success(databaseSourceDto);
    }

    @GetMapping("getAllDatabaseSource")
    public ResponseDto getAllDatabaseSource() {
        return responseUtil.success(databaseSourceService.queryAll());
    }

    @PostMapping("getPageDatabaseSource")
    public ResponseDto getPageDatabaseSource(@RequestParam(defaultValue = "1") String pageIndex,
                                             @RequestParam(defaultValue = "10") String pageSize) {
        return responseUtil.success(databaseSourceService.queryPage(pageIndex, pageSize));
    }

    @PostMapping("addDatabaseSource")
    public ResponseDto addDatabaseSource(
            @RequestBody @Validated DatabaseSourceAo databaseSourceAo, BindingResult bindingResult) {

        ResponseDto responseDto = responseUtil.failure();
        if (!responseUtil.validateParamFailure(responseDto, bindingResult)) {
            return responseDto;
        }

        InterfaceEntity databaseSourceEntity = databaseSourceService.save(databaseSourceAo);
        if (databaseSourceEntity == null) {
            responseDto.setResCode(ResponseEnum.CODE_MESSAGE_F9000.getCode());
            responseDto.setDescription("保存数据源出错");
            return responseDto;
        }

        DatabaseSourceDto databaseSourceDto = new DatabaseSourceDto();
        BeanUtils.copyProperties(databaseSourceEntity, databaseSourceDto);

        return responseUtil.success(databaseSourceDto);
    }

    @DeleteMapping("delDatabaseSource")
    public ResponseDto deleteDatabaseSource(@NotNull @RequestParam(value = "id") Integer id) {
        DatabaseSourceDto databaseSourceDto = new DatabaseSourceDto();
        databaseSourceDto.setId(id);
        databaseSourceService.delete(databaseSourceDto);
        return responseUtil.success("删除成功");
    }
}
