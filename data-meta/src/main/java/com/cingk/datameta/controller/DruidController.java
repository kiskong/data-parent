package com.cingk.datameta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cingk.datameta.model.dto.ResponseDto;
import com.cingk.datameta.service.DruidService;
import com.cingk.datameta.utils.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-02-15
 */
@RestController("/")
public class DruidController extends BaseRequestController{
	@Autowired
	public ResponseUtil responseUtil;

	@Autowired
	public DruidService druidService;


	@Operation(description = "druid new transaction test")
	@GetMapping("/getDruidTest")
	public ResponseDto getData(){
		druidService.getData();

		return responseUtil.success("成功");
	}

}
