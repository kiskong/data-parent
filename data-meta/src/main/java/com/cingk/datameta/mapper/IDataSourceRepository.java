package com.cingk.datameta.mapper;

import com.cingk.datameta.model.entity.DataSourceEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IDataSourceRepository extends IBaseCrudRepository<DataSourceEntity,Integer> {
    //自定义查询
    //面向对象的查询方式，必须使用实体对象操作
    @Query(value ="from DataSourceEntity where databaseName=?1")
    DataSourceEntity getByName(String databaseName);


    //update,delete,insert 需要注解 @Modifying
    @Modifying
    @Query(value ="DELETE From DataSourceEntity where databaseName=?1")
    void deleteByName(String databaseName);
}
