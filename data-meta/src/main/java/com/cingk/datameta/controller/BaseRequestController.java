package com.cingk.datameta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cingk.datameta.utils.ResponseUtil;

@Component
public class BaseRequestController {

    public ResponseUtil responseUtil;

    @Autowired
    public void setResponseUtil(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }
}
