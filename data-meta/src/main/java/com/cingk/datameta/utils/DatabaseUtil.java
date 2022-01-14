package com.cingk.datameta.utils;

import com.cingk.datameta.constant.enums.DatabaseDriverEnum;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtil {

    public String getDriverByUrl(String url){

        boolean isMysql = url.contains("mysql");
        if(isMysql) return DatabaseDriverEnum.MYSQL.getValue("mysql");

        return "";
    }
}
