package com.skgc.vrd.sendgrid.model;

import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailTemplate {

	private static final Logger log = LoggerFactory.getLogger(EmailTemplate.class);
	
	public static final String CHARACTER_UTF8 = "UTF-8"; // character set utf-8
	public static final String EMAIL_TEMPLATE_URL = "com/skgc/vrd/sendgrid/templates/";  // 이메일 템플릿 경로
	
	/* 생성자 객체를 초기화 시켜주고, 객체 생성 초기값을 세팅합니다. */
	public EmailTemplate(){
		this.velocityContext = new VelocityContext();
		addAttribute("templateUrl", EMAIL_TEMPLATE_URL);
		addAttribute("StringUtils", new StringUtils());
		
	}
	
	@Autowired
	/* velocity 세팅을 위한 객체 */
	private VelocityEngine velocityEngine;
	/* velocityContext(vm에 obj를 전달해줄 객체) */
	private VelocityContext velocityContext;
	/* 응답 template를 String으로 생성해줄 객체  */
	private StringWriter stringWriter;
	/* 최종 template를 생성해줄 객체  */
	private Template template;
	
	/**
	 * vm에 전달할 객체를 기억 합니다.
	 *
	 * @param key
	 * @param value
	 */
	public void addAttribute(String key, Object value){
		this.velocityContext.put(key, value);
	}

	/**
	 * 최종 Template 결과를 리턴 합니다.
	 *
	 * @return
	 * @throws Exception  
	 * @throws ParseErrorException 
	 * @throws ResourceNotFoundException 
	 */
	public String getTemplate(String templateNm) throws ResourceNotFoundException, ParseErrorException, Exception {
		
		this.velocityEngine = new VelocityEngine();
		this.stringWriter = new StringWriter();
		/* velocityEngin 경로를 세팅 한다. */
		this.velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, 	"classpath"	);
		this.velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		
		this.velocityEngine.setProperty(VelocityEngine.INPUT_ENCODING, CHARACTER_UTF8);

		/* velocity.log를 남기지 않는다. */
		this.velocityEngine.setProperty(VelocityEngine.RUNTIME_LOG, "");

		/* velocityEngine 시작 */
		this.velocityEngine.init();
		
		/* 현재 템플릿 경로의 로그 */
		log.warn("***** templatePathFileNm ***** : " + velocityEngine.getProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH) + templateNm);
		/* vm명 세팅 */
		this.template = velocityEngine.getTemplate(EMAIL_TEMPLATE_URL+ templateNm );

		/* 응답을 위한 템플릿과 사용자 요청 데이터를 merge 한다. */
		this.template.merge(this.velocityContext, this.stringWriter);

		return this.stringWriter.toString();
	}
	
}