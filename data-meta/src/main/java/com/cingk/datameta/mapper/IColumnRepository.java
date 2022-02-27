package com.cingk.datameta.mapper;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cingk.datameta.model.entity.ColumnEntity;

public interface IColumnRepository extends IBaseCrudRepository<ColumnEntity, Integer> {

	@Query(value = "from ColumnEntity where tabId in (?1)")
	List<ColumnEntity> getLocalTableColumn(List<Integer> tabId);
}
