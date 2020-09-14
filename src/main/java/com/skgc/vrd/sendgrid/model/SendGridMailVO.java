package com.skgc.vrd.sendgrid.model;

import java.util.List;

import com.sendgrid.Email;
import com.sendgrid.Personalization;

import lombok.Data;

@Data
public class SendGridMailVO {
	
	private List<Personalization> personalizations; 		/* 메일 정보 */
	private String subject;									/* 메일제목 */
	private String content;									/* 메일내용 */
	private Email from;										/* 발신자 정보 */
	private Email to;										/* 수신자 정보 */
	private List<String> filePathList;						/* 첨부 파일 경로 */
}
