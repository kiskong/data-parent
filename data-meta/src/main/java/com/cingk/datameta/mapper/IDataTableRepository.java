package com.cingk.datameta.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cingk.datameta.model.entity.DataTableEntity;

public interface IDataTableRepository extends IBaseCrudRepository<DataTableEntity, Integer> {

	@Query(value = "from DataTableEntity where databaseSourceId = :dataSourceId and tabName in (:tableNames)")
	List<DataTableEntity> queryAll(Integer dataSourceId, String[] tableNames);

	@Query(value = "from DataTableEntity where  databaseSourceId = :dataSourceId and schemaName=:schemaName and tabName in (:tableNames)")
	List<DataTableEntity> queryAll(Integer dataSourceId,String schemaName, String[] tableNames);

	@Query(value = "from DataTableEntity where databaseSourceId = :dataSourceId and schemaName=:schemaName")
	List<DataTableEntity> queryAll(Integer dataSourceId, String schemaName);

}
