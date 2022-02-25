package com.cingk.datameta.service.intf;


import java.util.List;

import com.cingk.datameta.model.ao.DataSourceAo;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.model.entity.DataSourceEntity;

public interface IDataSourceService extends IBaseService {

	void deleteByName(String name);

	DataSourceEntity queryById(Integer dataSourceId);

	DataSourceEntity queryByName(String databaseName);

	List<DataSourceEntity> queryPage(String pageIndex, String pageSize);

	DataSourceEntity save(DataSourceAo dataSourceAo);

	DataSourceEntity query(DataSourceDto dataSourceDto);

	DataSourceDto getDataSourceDtoById(Integer dataSourceId);

	DataSourceDto getDataSourceDtoByName(String databaseName);

	ResponseDto getDataSourceById(Integer dataSourceId);

	ResponseDto getDataSourceByName(String databaseName);


}
