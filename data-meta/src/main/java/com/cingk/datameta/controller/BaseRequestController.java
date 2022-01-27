package com.cingk.datameta.controller;

import com.cingk.datameta.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseRequestController {
    @Autowired
    public ResponseUtil responseUtil;
}
