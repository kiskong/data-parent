package com.cingk.datameta.controller;

import com.cingk.datameta.model.ao.DatabaseSourceAo;
import com.cingk.datameta.model.dto.DatabaseSourceDto;
import com.cingk.datameta.service.impl.DatabaseSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseSourceController {

    @Autowired
    private DatabaseSourceService databaseSourceService;

    @GetMapping("/getDatabaseSource")
    public DatabaseSourceDto getDatabaseSource(){
        DatabaseSourceAo databaseSourceAo = new DatabaseSourceAo();
        databaseSourceAo.setDatabaseName("localhost_mysql");
//        databaseSourceAo.setDriver(DatabaseDriver.MYSQL.);
        return databaseSourceService.query(databaseSourceAo);
    }
}
