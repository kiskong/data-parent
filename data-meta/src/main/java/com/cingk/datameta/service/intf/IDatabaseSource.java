package com.cingk.datameta.service.intf;

import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.entity.DatabaseSourceEntity;

import java.util.List;

public interface IDatabaseSource {

    DatabaseSourceEntity save(DatabaseSourceDto databaseSourceDto);

    void delete(DatabaseSourceDto databaseSourceDto);

    void modify(DatabaseSourceDto databaseSourceDto);

    DatabaseSourceEntity query(DatabaseSourceDto databaseSourceDto);

    List<DatabaseSourceEntity> queryAll();

}
