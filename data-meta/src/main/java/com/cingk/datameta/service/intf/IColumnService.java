package com.cingk.datameta.service.intf;

import java.util.List;

import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.model.dto.DatabaseTableDto;

public interface IColumnService {
    List<IDataTableColumnEntity> getTableColumn(DatabaseSourceDto databaseSourceDto, String sql,Class resultClass);
    List<IDataTableColumnEntity> getTableColumn(DatabaseTableDto databaseTableDto, String sql,Class resultClass);
    List<IDataTableColumnEntity> getTableColumn(DatabaseTableDto databaseTableDto);

}
