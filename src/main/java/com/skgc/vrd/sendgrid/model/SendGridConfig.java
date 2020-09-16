package com.skgc.vrd.sendgrid.model;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="sendgrid")
public class SendGridConfig {
	
	private String apikey;					/* API Key */
	private List<String> validMimeTypes;   	/* 이메일 첨부 파일 허용 확장자 */
	private int maxSend;					/* 한번에 최대 전송가능한 메일 수 */
	private int breakSecond; 				/* 한번 최대 전송 후 sleep second */
}
