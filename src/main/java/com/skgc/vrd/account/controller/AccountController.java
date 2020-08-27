package com.skgc.vrd.account.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
 
import org.springframework.web.bind.annotation.RestController;

import com.skgc.vrd.account.model.UserEntity;
import com.skgc.vrd.account.service.UserDetailsServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	private UserDetailsServiceImpl userDetailsService;
	
	public AccountController(UserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@ApiOperation(value = "", authorizations = { 
			@Authorization(value="jwtToken") })
	@GetMapping("/users")
	public ResponseEntity<List<UserEntity>> findAllUsers(){
		List<UserEntity> user = userDetailsService.findByAll();
		return new ResponseEntity<List<UserEntity>>(user, HttpStatus.OK);
	}
	
	@GetMapping("/test")
	@ApiOperation(value = "", authorizations = { 
			@Authorization(value="jwtToken") })
	public ResponseEntity<String> test(){
		return new ResponseEntity<String>("hello", HttpStatus.OK);
	}
	
	
	
}
