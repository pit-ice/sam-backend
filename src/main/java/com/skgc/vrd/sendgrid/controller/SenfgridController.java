package com.skgc.vrd.sendgrid.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sendgrid.Response;
import com.skgc.vrd.sendgrid.model.SendGridMailVO;
import com.skgc.vrd.sendgrid.service.SendGridService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/api/send-mail")
public class SenfgridController {
	
	@Autowired
	private SendGridService sendgridService;
	
		/**
		 * 메일 단일 전송
		 * @param from
		 * @param to
		 * @param content
		 * @param subject
		 * @param filePathList
		 * @return
		 */
		@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
		@PostMapping("/send")
		public int sendMail(@Valid @RequestBody SendGridMailVO sendGridMail){
			
			Response result = sendgridService.sendMail(sendGridMail);
			return result.statusCode;
		}
		
		/**
		 * 메일 여러개 전송
		 * @param personalizationList
		 * @param from
		 * @param to
		 * @param content
		 * @param subject
		 * @param filePathList
		 * @return
		 */
		@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
		@PostMapping("/send-multiple")
		public int sendMails(@Valid @RequestBody SendGridMailVO sendGridMail){
			Response result = sendgridService.sendMail(sendGridMail);
			return result.statusCode;
		}
}
