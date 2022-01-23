package com.cingk.datameta.service.impl;

import com.cingk.datameta.mapper.IDatabaseSourceRepository;
import com.cingk.datameta.model.InterfaceEntity;
import com.cingk.datameta.model.ao.DatabaseSourceAo;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.entity.DatabaseSourceEntity;
import com.cingk.datameta.service.intf.IDatabaseSource;
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
public class DatabaseSourceService implements IDatabaseSource {

    @Autowired
    private DatabaseUtil databaseUtil;
    @Autowired
    private IDatabaseSourceRepository iDatabaseSourceRepository;


    @Override
    public DatabaseSourceEntity save(InterfaceEntity databaseSourceDto) {
        DatabaseSourceEntity databaseSourceEntity = new DatabaseSourceEntity();
        BeanUtils.copyProperties(databaseSourceDto, databaseSourceEntity);
        return iDatabaseSourceRepository.save(databaseSourceEntity);
    }

    @Override
    public void delete(InterfaceEntity databaseSourceDto) {
        DatabaseSourceEntity databaseSourceEntity = new DatabaseSourceEntity();
        BeanUtils.copyProperties(databaseSourceDto, databaseSourceEntity);
        iDatabaseSourceRepository.delete(databaseSourceEntity);
    }

    @Override
    public void deleteById(Integer id){
        iDatabaseSourceRepository.deleteById(id);
    }

    @Override
    public void modify(InterfaceEntity databaseSourceDto) {
        DatabaseSourceEntity databaseSourceEntity = new DatabaseSourceEntity();
        BeanUtils.copyProperties(databaseSourceDto, databaseSourceEntity);
    }

    @Override
    public DatabaseSourceEntity query(InterfaceEntity databaseSourceDto) {
        return iDatabaseSourceRepository.findById(((DatabaseSourceDto)databaseSourceDto).getId()).orElse(null);
    }

    @Override
    public List<InterfaceEntity> queryAll() {
        Iterable<DatabaseSourceEntity> databaseSourceEntityIterable = iDatabaseSourceRepository.findAll();
        return Lists.newArrayList(databaseSourceEntityIterable);
    }

    public List<DatabaseSourceEntity> queryPage(String pageIndex, String pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(Integer.parseInt(pageIndex), Integer.parseInt(pageSize),sort);
        Page<DatabaseSourceEntity> databaseSourceEntityPage =  iDatabaseSourceRepository.findAll(pageable);
        return databaseSourceEntityPage.stream().collect(Collectors.toList());
    }

    /**
     * 保存数据源
     * @return
     */
    public DatabaseSourceEntity save(DatabaseSourceAo databaseSourceAo) {

        DatabaseSourceDto databaseSourceDto = new DatabaseSourceDto();
        BeanUtils.copyProperties(databaseSourceAo,databaseSourceDto);

        String driver = databaseUtil.getDriverByUrl(databaseSourceAo.getUrl());
        databaseSourceDto.setDriver(driver);
        databaseSourceDto.setCreateTime(Instant.now());

        return save(databaseSourceDto);
    }
}
