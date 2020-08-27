package com.skgc.vrd.techsupport.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skgc.vrd.techsupport.model.BbsVO;
import com.skgc.vrd.techsupport.service.BbsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/api/techsupport")
public class BbsController {
	
	private BbsService bbsService;
	
	public BbsController(BbsService bbsService) {
		this.bbsService = bbsService;
	}

	@ApiOperation(value = "", authorizations = { 
			@Authorization(value="jwtToken") })
	@GetMapping("/bbs")
	public ResponseEntity<List<BbsVO>> findAllBbs(){
		List<BbsVO> bbsVO = bbsService.findAllBbs();
		return new ResponseEntity<List<BbsVO>>(bbsVO, HttpStatus.OK);
	}
	
//	@PostMapping("/bbs")
//	public ResponseEntity<List<BbsVO>> createBbs(){
//		List<BbsVO> bbsVO = bbsService.findAllBbs();
//		return new ResponseEntity<List<BbsVO>>(bbsVO, HttpStatus.OK);
//	}
}
