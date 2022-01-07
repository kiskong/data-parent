package com.cingk.datameta.service.intf;

import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.ao.DatabaseSourceAo;

import java.util.List;

public interface IDatabaseSource {

    public void save(DatabaseSourceAo databaseSourceAo);
    public void delete(DatabaseSourceAo databaseSourceAo);
    public void modify(DatabaseSourceAo databaseSourceAo);
    public DatabaseSourceDto query(DatabaseSourceAo databaseSourceAo);
    public List<DatabaseSourceDto> queryAll();
}
