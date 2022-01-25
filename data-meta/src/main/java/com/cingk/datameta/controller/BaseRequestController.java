package com.cingk.datameta.controller;

import com.cingk.datameta.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseRequestController {
    @Autowired
    public ResponseUtil responseUtil;
}
