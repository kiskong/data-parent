package com.cingk.datameta.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.NumberUtil;
import com.cingk.datameta.model.IDataTableColumnEntity;
import com.cingk.datameta.model.dto.DataSourceDto;
import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.model.entity.DataTableColumnEntity;
import com.cingk.datameta.model.entity.DataTableEntity;
import com.cingk.datameta.service.intf.IColumnService;
import com.cingk.datameta.service.intf.IDataSourceService;
import com.cingk.datameta.service.intf.IDataTable;
import com.cingk.datameta.service.intf.IDataTableColumn;
import com.cingk.datameta.utils.DataTableColumnUtil;
import com.cingk.datameta.utils.DataTableUtil;
import com.cingk.datameta.utils.ResponseUtil;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ColumnController {


    private IDataSourceService dataSourceService;
    private DataTableColumnUtil columnUtil;
    private DataTableUtil tableUtil;
    public ResponseUtil responseUtil;

    @Autowired
    public void setResponseUtil(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }

    @Autowired
    public void setDataSourceService(IDataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @Autowired
    public void setColumnUtil(DataTableColumnUtil columnUtil) {
        this.columnUtil = columnUtil;
    }

    @Autowired
    public void setTableUtil(DataTableUtil tableUtil) {
        this.tableUtil = tableUtil;
    }

    @Operation(summary = "采集目标数据源指定模式名下的所有表字段信息")
    @GetMapping("getColumnBySchema")
    public ResponseDto getColumnBySchema(
            @Parameter(description = "数据源标识", required = true) @NotNull Integer dataSourceId,
            @Parameter(description = "模式名称", required = true) @NotNull String schemaName) {
        ResponseDto responseDto = dataSourceService.getDataSourceById(dataSourceId);
        if (!responseDto.successed()) {
            return responseDto;
        }

        DataSourceDto dataSourceDto = (DataSourceDto) responseDto.getData();
        //查询所有表信息
        IDataTable tableService = tableUtil.getDataTableService(dataSourceDto);
        List<DataTableEntity> tables = tableService.getSrcAllTablesWithSchema(dataSourceDto, schemaName);

        //忽略已存在的表
        List<DataTableEntity> existsTable = tableService.getLocalAllTables(dataSourceId,schemaName,tables.toArray(new String[tables.size()]));

        //新增的表
        List<DataTableEntity> newTables =
        existsTable.stream().filter(
                d -> !tables.stream().anyMatch(
                        t -> NumberUtil.isGreater(new BigDecimal(t.getId()),new BigDecimal(d.getId())) )).collect(Collectors.toList());

        //保存所有表信息
        List<DataTableEntity> dbTables = tableService.saveAllTables(newTables);

        //查询所有表字段信息
        List<String> tableNames = tables.stream().map(DataTableEntity::getTabName).collect(Collectors.toList());
        IColumnService columnService = columnUtil.getColumnService(dataSourceDto);
        List<IDataTableColumnEntity> columns = columnService.getTableColumn(dataSourceDto, schemaName, tableNames);
        //保存所有字段信息

        List<DataTableColumnEntity> columnEntityList = Lists.newArrayList();
        columns.stream().forEach(columnEntity -> {
            DataTableColumnEntity dataTableColumnEntity = new DataTableColumnEntity();
            dataTableColumnEntity.setTabName(columnEntity.getTabName());
            dataTableColumnEntity.setColDecimal(columnEntity.getColDecimal());
            dataTableColumnEntity.setColType(columnEntity.getColType());
            dataTableColumnEntity.setColLength(columnEntity.getColLength());
            dataTableColumnEntity.setColId(columnEntity.getColId());
            dataTableColumnEntity.setColName(columnEntity.getColName());

            //已保存的tabId标识
            DataTableEntity datatableEntity =
                    dbTables.stream().filter(d -> d.getTabName().equals(columnEntity.getTabName()))
                            .findFirst()
                            .orElse(null);

            dataTableColumnEntity.setTabId(datatableEntity.getId());
            columnEntityList.add(dataTableColumnEntity);
        });

        columnService.saveTableColumn(columnEntityList);
        return responseUtil.success("采集表字段成功",tableNames);
    }

    public ResponseDto getColumnByTableName(Integer dataSourceId, String schemaName, String tableName) {
        return null;
    }

    public ResponseDto getColumnByTableNames(Integer dataSourceId, String schemaName, List<String> tableName) {
        return null;
    }
}
