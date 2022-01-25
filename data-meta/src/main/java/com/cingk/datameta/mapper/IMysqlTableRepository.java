package com.cingk.datameta.mapper;


import com.cingk.datameta.model.entity.MysqlTableEntity;

public interface IMysqlTableRepository extends IBaseCrudRepository<MysqlTableEntity,Integer> {

//    @Query(value ="select * from information_schema.tables", nativeQuery = true)
//    Iterable<MysqlTableEntity> findAll();
}
