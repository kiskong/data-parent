package com.cingk.datameta.mapper;

import com.cingk.datameta.model.entity.DatabaseSourceEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface IDatabaseSourceRepository extends IBaseCrudRepository<DatabaseSourceEntity,Integer> {
    //自定义查询
    //面向对象的查询方式，必须使用实体对象操作
    @Query(value ="from DatabaseSourceEntity where databaseName=?1")
    DatabaseSourceEntity getByName(String databaseName);


    //update,delete,insert 需要注解 @Transactional @Modifying
    @Transactional
    @Modifying
    @Query(value ="DELETE From DatabaseSourceEntity where databaseName=?1")
    void deleteByName(String databaseName);
}
