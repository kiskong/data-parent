package com.cingk.datameta.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cingk.datameta.model.entity.DataTableEntity;

public interface ITableRepository extends IBaseCrudRepository<DataTableEntity, Integer> {

	@Query(value = "from DataTableEntity where databaseSourceId = ?1 and tabName in (?2)")
	List<DataTableEntity> queryAll(Integer dataSourceId, String[] tableNames);

	@Query(value = "from DataTableEntity where  databaseSourceId = ?1 and schemaName=?2 and tabName in (?3)")
	List<DataTableEntity> queryAll(Integer dataSourceId,String schemaName, String[] tableNames);

	@Query(value = "from DataTableEntity where databaseSourceId = ?1 and schemaName=?2")
	List<DataTableEntity> queryAll(Integer dataSourceId, String schemaName);

}
