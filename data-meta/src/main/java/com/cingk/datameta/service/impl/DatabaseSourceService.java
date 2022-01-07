package com.cingk.datameta.service.impl;

import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.ao.DatabaseSourceAo;
import com.cingk.datameta.service.intf.IDatabaseSource;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseSourceService implements IDatabaseSource {


    @Override
    public void save(DatabaseSourceAo databaseSourceAo) {

    }

    @Override
    public void delete(DatabaseSourceAo databaseSourceAo) {

    }

    @Override
    public void modify(DatabaseSourceAo databaseSourceAo) {

    }

    @Override
    public DatabaseSourceDto query(DatabaseSourceAo databaseSourceAo) {
        DatabaseSourceDto databaseSourceDto = new DatabaseSourceDto();
        BeanUtils.copyProperties(databaseSourceAo, databaseSourceDto);
        return databaseSourceDto;
    }

    @Override
    public List<DatabaseSourceDto> queryAll() {
        return Lists.newArrayList();
    }
}
