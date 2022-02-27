package com.cingk.datameta.mapper;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cingk.datameta.model.entity.DataTableColumnEntity;

public interface IColumnRepository extends IBaseCrudRepository<DataTableColumnEntity, Integer> {

	@Query(value = "from DataTableColumnEntity where tabId in (?1)")
	List<DataTableColumnEntity> getLocalTableColumn(List<Integer> tabId);
}
