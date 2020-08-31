package com.skgc.vrd.vrd.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skgc.vrd.vrd.model.InputData;
import com.skgc.vrd.vrd.repository.DataDesignRepository;
import com.skgc.vrd.vrd.service.DataDesignService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;



@RestController
@RequestMapping("/api/vrd/data")
public class DataDesignController {

	@Autowired
	@Qualifier("dataDesignRepository")
	private DataDesignRepository dataDesignRepository;
	
//	public DataDesignController(DataDesignRepository dataDesignRepository) {
//		this.dataDesignRepository = dataDesignRepository;
//	}
	
	@PostMapping
	@ApiOperation(value = "", authorizations = { 
			@Authorization(value="jwtToken") })
	public ResponseEntity<InputData> setInputData(@RequestBody InputData data) {
		
		
		return new ResponseEntity<InputData>(dataDesignRepository.save(data), HttpStatus.OK);
		 
	}
	
	@GetMapping
	@ApiOperation(value = "", authorizations = { 
			@Authorization(value="jwtToken") })
	public ResponseEntity<List<InputData>> findAllInputData(@RequestBody InputData data) {
		
		return new ResponseEntity<List<InputData>> (dataDesignRepository.findAll(), HttpStatus.OK);
		 
	}
	
	@Autowired
	private DataDesignService dataDesignService;
	
	@PostMapping("/json")
	@ApiOperation(value = "", authorizations = { 
			@Authorization(value="jwtToken") })
	public ResponseEntity<InputData> insertInputData(@RequestBody InputData data) {
		
		
		return new ResponseEntity<InputData>(dataDesignService.insertInputData(data), HttpStatus.OK);
		 
	}
	
	@GetMapping("/json")
	@ApiOperation(value = "", authorizations = { 
			@Authorization(value="jwtToken") })
	public ResponseEntity<List<InputData>> insertInputData(@RequestBody String name) {
		
		
		return new ResponseEntity<List<InputData>>(dataDesignService.getInputDataList(name), HttpStatus.OK);
		 
	}
	
}
