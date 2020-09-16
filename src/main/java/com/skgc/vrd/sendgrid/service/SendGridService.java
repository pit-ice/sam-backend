package com.skgc.vrd.sendgrid.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sendgrid.Email;
import com.sendgrid.Personalization;
import com.sendgrid.Response;
import com.skgc.vrd.sendgrid.model.SendGridMailVO;

public interface SendGridService {

	/**
	 * Sendgrid 메일 전송
	 * @param sendGridMail
	 * @return
	 */
	public ResponseEntity<?> sendMail(SendGridMailVO sendGridMail);

	/**
	 * send grid 메일 전송
	 * @param from (발신자 이메일 / 이름)
	 * @param to (수신자 이메일 / 이름)
	 * @param content 메일내용
	 * @param subject 메일제목
	 * @param filePath 첨부파일 경로 - (옵션)
	 * @return
	 */
	public Response sendMail(Email from, Email to, String content, String subject, List<String> filePathList) throws IOException;
	
	/**
	 * send grid 메일 전송
	 * @param personalizationList (수신자 메일이름 / 메일제목 / 개인화 문자 및 대채문자 ) - List 형식
	 * @param from (발신자 이메일 / 이름)
	 * @param to (수신자 이메일 / 이름)
	 * @param content 메일내용
	 * @param subject 메일제목
	 * @param filePath 첨부파일 경로 - (옵션)
	 * @return
	 */
	public Response sendMails(List<Personalization> personalizationList, Email from, Email to, String content, String subject, List<String> filePathList)  throws IOException;
}
