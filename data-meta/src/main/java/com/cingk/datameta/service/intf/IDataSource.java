package com.cingk.datameta.service.intf;


import com.cingk.datameta.model.ao.DataSourceAo;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.model.entity.DataSourceEntity;

import java.util.List;

public interface IDataSource extends IBaseService {

    void deleteByName(String name);
    DataSourceEntity queryById(Integer dataSourceId);
    DataSourceEntity queryByName(String databaseName);
    List<DataSourceEntity> queryPage(String pageIndex, String pageSize);
    DataSourceEntity save(DataSourceAo dataSourceAo);
    DataSourceEntity query(DataSourceDto dataSourceDto);

    DataSourceDto getDataSourceDtoById(Integer dataSourceId,DataSourceDto dataSourceDto);
    DataSourceDto getDataSourceDtoByName(String databaseName,DataSourceDto dataSourceDto);

    ResponseDto getDataSourceById(Integer dataSourceId, DataSourceDto dataSourceDto);
    ResponseDto getDataSourceByName(String databaseName, DataSourceDto dataSourceDto);


}
