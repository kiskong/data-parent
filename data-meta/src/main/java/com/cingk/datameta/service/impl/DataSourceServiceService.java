package com.cingk.datameta.service.impl;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cingk.datameta.mapper.IDataSourceRepository;
import com.cingk.datameta.model.InterfaceEntity;
import com.cingk.datameta.model.ao.DataSourceAo;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.model.entity.DataSourceEntity;
import com.cingk.datameta.service.intf.IDataSourceService;
import com.cingk.datameta.utils.DatabaseUtil;
import com.cingk.datameta.utils.ResponseUtil;
import java.time.Instant;

@Service
public class DataSourceServiceService implements IDataSourceService {

    private DatabaseUtil databaseUtil;
    private IDataSourceRepository iDataSourceRepository;
    private ResponseUtil responseUtil;


    @Override
    public DataSourceDto getDataSourceDtoById(Integer dataSourceId) {
        DataSourceDto dataSourceDto = new DataSourceDto();
        DataSourceEntity dataSourceEntity = queryById(dataSourceId);
        if (dataSourceEntity == null) return null;
        BeanUtils.copyProperties(dataSourceEntity, dataSourceDto);
        return dataSourceDto;
    }

    @Override
    public DataSourceDto getDataSourceDtoByName(String databaseName) {
        DataSourceDto dataSourceDto = new DataSourceDto();
        DataSourceEntity dataSourceEntity = queryByName(databaseName);
        if (dataSourceEntity == null) return null;
        BeanUtils.copyProperties(dataSourceEntity, dataSourceDto);
        return dataSourceDto;
    }


    @Override
    public DataSourceEntity save(InterfaceEntity dataSourceDto) {
        DataSourceEntity dataSourceEntity = new DataSourceEntity();
        BeanUtils.copyProperties(dataSourceDto, dataSourceEntity);
        return iDataSourceRepository.save(dataSourceEntity);
    }

    @Override
    public void delete(InterfaceEntity dataSourceDto) {
        DataSourceEntity dataSourceEntity = new DataSourceEntity();
        BeanUtils.copyProperties(dataSourceDto, dataSourceEntity);
        iDataSourceRepository.delete(dataSourceEntity);
    }

    @Override
    public void deleteById(Integer id) {
        iDataSourceRepository.deleteById(id);
    }

    @Override
    public void modify(InterfaceEntity dataSourceDto) {
        DataSourceEntity dataSourceEntity = new DataSourceEntity();
        BeanUtils.copyProperties(dataSourceDto, dataSourceEntity);
    }

    @Override
    public DataSourceEntity query(InterfaceEntity dataSourceDto) {
        return iDataSourceRepository.getByName(((DataSourceDto) dataSourceDto).getDatabaseName());
    }

    @Override
    public List<InterfaceEntity> queryAll() {
        Iterable<DataSourceEntity> databaseSourceEntityIterable = iDataSourceRepository.findAll();
        return Lists.newArrayList(databaseSourceEntityIterable);
    }

    @Override
    public DataSourceEntity queryById(Integer id) {
        return iDataSourceRepository.findById(id).orElse(null);
    }

    @Override
    public DataSourceEntity queryByName(String databaseName) {
        return iDataSourceRepository.getByName(databaseName);
    }

    /**
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<DataSourceEntity> queryPage(String pageIndex, String pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(Integer.parseInt(pageIndex), Integer.parseInt(pageSize), sort);
        Page<DataSourceEntity> databaseSourceEntityPage = iDataSourceRepository.findAll(pageable);
        return databaseSourceEntityPage.stream().collect(Collectors.toList());
    }

    /**
     * 保存数据源
     *
     * @return
     */
    @Override
    public DataSourceEntity save(DataSourceAo dataSourceAo) {
        DataSourceDto dataSourceDto = new DataSourceDto();
        BeanUtils.copyProperties(dataSourceAo, dataSourceDto);
        String driver = databaseUtil.getDriverByUrl(dataSourceAo.getUrl());
        dataSourceDto.setDriver(driver);
        dataSourceDto.setCreateTime(Instant.now());
        return save(dataSourceDto);
    }

    @Override
    public DataSourceEntity query(DataSourceDto dataSourceDto) {
        return queryById(dataSourceDto.getId());
    }


    @Override
    public void deleteByName(String name) {
        iDataSourceRepository.deleteByName(name);
    }

    @Override
    public ResponseDto getDataSourceById(Integer dataSourceId) {
        DataSourceDto dataSourceDto = getDataSourceDtoById(dataSourceId);
        if (dataSourceDto != null) {
            return responseUtil.success("获取数据源成功",dataSourceDto);
        }
        return responseUtil.failure( "数据源不存在");
    }

    @Override
    public ResponseDto getDataSourceByName(String dataSourceName) {
        DataSourceDto dataSourceDto = getDataSourceDtoByName(dataSourceName);
        if (dataSourceDto != null) {
            return responseUtil.success("获取数据源成功",dataSourceDto);
        }
        return responseUtil.failure("数据源不存在");
    }

    @Autowired
    public void setDatabaseUtil(DatabaseUtil databaseUtil) {
        this.databaseUtil = databaseUtil;
    }
    @Autowired
    public void setiDataSourceRepository(IDataSourceRepository iDataSourceRepository) {
        this.iDataSourceRepository = iDataSourceRepository;
    }
    @Autowired
    public void setResponseUtil(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }
}
