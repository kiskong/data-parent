package com.cingk.datameta.service.impl;

import com.cingk.datameta.mapper.IDataSourceRepository;
import com.cingk.datameta.model.InterfaceEntity;
import com.cingk.datameta.model.ao.DataSourceAo;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.entity.DataSourceEntity;
import com.cingk.datameta.service.intf.IDataSource;
import com.cingk.datameta.utils.DatabaseUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataSourceService implements IDataSource {

    @Autowired
    private DatabaseUtil databaseUtil;
    @Autowired
    private IDataSourceRepository iDataSourceRepository;


    @Override
    public DataSourceEntity save(InterfaceEntity databaseSourceDto) {
        DataSourceEntity dataSourceEntity = new DataSourceEntity();
        BeanUtils.copyProperties(databaseSourceDto, dataSourceEntity);
        return iDataSourceRepository.save(dataSourceEntity);
    }

    @Override
    public void delete(InterfaceEntity databaseSourceDto) {
        DataSourceEntity dataSourceEntity = new DataSourceEntity();
        BeanUtils.copyProperties(databaseSourceDto, dataSourceEntity);
        iDataSourceRepository.delete(dataSourceEntity);
    }

    @Override
    public void deleteById(Integer id){
        iDataSourceRepository.deleteById(id);
    }

    @Override
    public void modify(InterfaceEntity databaseSourceDto) {
        DataSourceEntity dataSourceEntity = new DataSourceEntity();
        BeanUtils.copyProperties(databaseSourceDto, dataSourceEntity);
    }

    @Override
    public DataSourceEntity query(InterfaceEntity databaseSourceDto) {
        return iDataSourceRepository.getByName(((DataSourceDto)databaseSourceDto).getDatabaseName());
    }

    @Override
    public List<InterfaceEntity> queryAll() {
        Iterable<DataSourceEntity> databaseSourceEntityIterable = iDataSourceRepository.findAll();
        return Lists.newArrayList(databaseSourceEntityIterable);
    }

    @Override
    public DataSourceEntity queryById(Integer id){
        return iDataSourceRepository.findById(id).orElse(null);
    }

    /**
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<DataSourceEntity> queryPage(String pageIndex, String pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(Integer.parseInt(pageIndex), Integer.parseInt(pageSize),sort);
        Page<DataSourceEntity> databaseSourceEntityPage =  iDataSourceRepository.findAll(pageable);
        return databaseSourceEntityPage.stream().collect(Collectors.toList());
    }

    /**
     * 保存数据源
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
    public void deleteByName(String name) {
        iDataSourceRepository.deleteByName(name);
    }
}
