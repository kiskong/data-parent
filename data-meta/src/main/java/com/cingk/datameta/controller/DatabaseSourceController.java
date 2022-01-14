package com.cingk.datameta.controller;

import com.cingk.datameta.constant.enums.ResponseEnum;
import com.cingk.datameta.model.ao.DatabaseSourceAo;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.dto.ResponseBodyDto;
import com.cingk.datameta.model.entity.DatabaseSourceEntity;
import com.cingk.datameta.service.impl.DatabaseSourceService;
import com.cingk.datameta.utils.ResponseUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class DatabaseSourceController {

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private DatabaseSourceService databaseSourceService;

    @GetMapping("getDatabaseSource")
    public ResponseBodyDto getDatabaseSource(String databaseName) {
        ResponseBodyDto responseBodyDto = responseUtil.failure();
        DatabaseSourceDto databaseSourceDto = new DatabaseSourceDto();
        databaseSourceDto.setDatabaseName(databaseName);
        DatabaseSourceEntity databaseSourceEntity = databaseSourceService.query(databaseSourceDto);
        if(ObjectUtils.NULL.equals(databaseSourceEntity) ){
            responseBodyDto.setResCode(ResponseEnum.CODE_MESSAGE_F1001.getCode());
            responseBodyDto.setDescription("保存数据源出错");
            return responseBodyDto;
        }

        BeanUtils.copyProperties(databaseSourceEntity, databaseSourceDto);
        return responseUtil.success(databaseSourceDto);
    }

    @GetMapping("getAllDatabaseSource")
    public ResponseBodyDto getAllDatabaseSource() {
       List<DatabaseSourceEntity> databaseSourceEntities =  databaseSourceService.queryAll();
       return responseUtil.success(databaseSourceEntities);
    }

    @PostMapping("getPageDatabaseSource")
    public ResponseBodyDto getPageDatabaseSource(@RequestParam(defaultValue = "1") String pageIndex,
                                                 @RequestParam(defaultValue = "10") String pageSize) {
        return responseUtil.success(databaseSourceService.queryPage(pageIndex,pageSize));
    }

    @PostMapping("addDatabaseSource")
    public ResponseBodyDto addDatabaseSource(
            @RequestBody @Validated DatabaseSourceAo databaseSourceAo, BindingResult bindingResult) {

        ResponseBodyDto responseBodyDto = responseUtil.failure();
        if (!responseUtil.validateParamFailure(responseBodyDto, bindingResult)) {
            return responseBodyDto;
        }

        DatabaseSourceEntity databaseSourceEntity = databaseSourceService.save(databaseSourceAo);
        if(ObjectUtils.NULL.equals(databaseSourceEntity) ){
            responseBodyDto.setResCode(ResponseEnum.CODE_MESSAGE_F1001.getCode());
            responseBodyDto.setDescription("保存数据源出错");
            return responseBodyDto;
        }

        DatabaseSourceDto databaseSourceDto = new DatabaseSourceDto();
        BeanUtils.copyProperties(databaseSourceEntity, databaseSourceDto);

        return responseUtil.success(databaseSourceDto);
    }
}
