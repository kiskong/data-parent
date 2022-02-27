package com.cingk.datameta.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cingk.datameta.model.entity.TableEntity;

public interface ITableRepository extends IBaseCrudRepository<TableEntity, Integer> {

	@Query(value = "from TableEntity where databaseSourceId = ?1 and schemaName in (?2)")
	List<TableEntity> queryAll(Integer dataSourceId, String[] schemaName);

	@Query(value = "from TableEntity where  databaseSourceId = ?1 and schemaName=?2 and tabName in (?3)")
	List<TableEntity> queryAll(Integer dataSourceId,String schemaName, String[] tableNames);

	@Query(value = "from TableEntity where  databaseSourceId = ?1 and schemaName=?2 and id in (?3)")
	List<TableEntity> queryAll(Integer dataSourceId,String schemaName, Integer[] ids);

	@Query(value = "from TableEntity where databaseSourceId = ?1 and schemaName=?2")
	List<TableEntity> queryAll(Integer dataSourceId, String schemaName);

	@Query(value = "from TableEntity where  databaseSourceId = ?1 and schemaName=?2 and tabName = ?3")
	TableEntity findByName(Integer dataSourceId,String schemaName,String tableName);

	@Query(value = "from TableEntity where  databaseSourceId = ?1 and schemaName=?2 and id = ?3")
	TableEntity findById(Integer dataSourceId,String schemaName,Integer id);

}
