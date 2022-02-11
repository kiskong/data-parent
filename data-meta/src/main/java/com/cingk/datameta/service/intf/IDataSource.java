package com.cingk.datameta.service.intf;


import com.cingk.datameta.model.ao.DataSourceAo;
import com.cingk.datameta.model.entity.DataSourceEntity;

import java.util.List;

public interface IDataSource extends IBaseService {

    void deleteByName(String name);
    DataSourceEntity queryById(Integer databaseSourceId);
    List<DataSourceEntity> queryPage(String pageIndex, String pageSize);
    DataSourceEntity save(DataSourceAo dataSourceAo);
}
