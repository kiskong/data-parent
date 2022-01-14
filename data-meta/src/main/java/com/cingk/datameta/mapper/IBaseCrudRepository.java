package com.cingk.datameta.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface IBaseCrudRepository<T, ID> extends CrudRepository<T, ID> {
    Page<T> findAll(Pageable pageable);
}
