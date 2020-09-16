package com.skgc.vrd.sendgrid.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.tika.Tika;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sendgrid.Attachments;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.skgc.vrd.sendgrid.model.EmailTemplate;
import com.skgc.vrd.sendgrid.model.SendGridConfig;
import com.skgc.vrd.sendgrid.model.SendGridMailVO;

@Service
public class SendGridServiceImpl implements SendGridService {

	
		@Autowired
		private SendGridConfig config; /* mail 관련 설정 파일 */
	
		private final Logger log = LoggerFactory.getLogger(this.getClass());
		
		@Override
		public ResponseEntity<?> sendMail(SendGridMailVO sendGridMail) {
			Response response = new Response();
			try {
				/* 템플릿 사용여부 구분 */
				if(sendGridMail.getEmailTemplateId() != null && !"".equals(sendGridMail.getEmailTemplateId()) ) {
						String content = this.loadEmailTemplate(sendGridMail.getEmailTemplateId() , sendGridMail.getEmailValueMap());
						sendGridMail.setContent(content);
				}
				
				/* 다중발송 및 단일 발송 구분 */
				List<Personalization> perList = sendGridMail.getPersonalizations();
				if(perList!= null && perList.size() > 0) {
					response = this.sendMails(sendGridMail.getPersonalizations(), sendGridMail.getFrom(),  sendGridMail.getTo(), sendGridMail.getContent(), sendGridMail.getSubject(), sendGridMail.getFilePathList());
				}else {
					response = this.sendMail(sendGridMail.getFrom(), sendGridMail.getTo(), sendGridMail.getContent(), sendGridMail.getSubject(), sendGridMail.getFilePathList());
				}
			} catch (ResourceNotFoundException e) {
				e.printStackTrace();
				return new ResponseEntity<> ("존재하지 않는 리소스입니다. 템플릿 아이디를 확인해 주십시오.", HttpStatus.NOT_FOUND);
			} catch (NullPointerException e) {
				e.printStackTrace();
				return new ResponseEntity<> ("필수값이 존재 하지 않습니다.", HttpStatus.BAD_REQUEST);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
			}
			if(response.statusCode == 202) {
				return new ResponseEntity<> (HttpStatus.CREATED);
			}else {
				return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
			}
		}
		
		/**
		 * Sendgrid 메일 여러개 전송 인자 세팅 및 메일 발송 호출
		 * @throws IOException 
		 */
		@Override
		public Response sendMails(List<Personalization> personalizationList, Email from, Email to, String content, String subject, List<String> filePathList) throws IOException {
			Content contents = new Content( "text/html charset=UTF-8", content);
			Response responce = new Response();
			Mail mail = new Mail(from, subject, to, contents);
			/* 첨부 파일 추가 */
			this.mailAttachFiles(mail, filePathList);

			/* 대량 메일 전송시을 대비한 분할 전송 */ 
			for (int i=0; i < personalizationList.size(); i++) {
				Personalization per = personalizationList.get(i);
				mail.addPersonalization(per);
					
				if((i + 1) % config.getMaxSend() == 0 || (i+1) == personalizationList.size() ) {
						responce =  this.send(mail);
						try {
							TimeUnit.SECONDS.sleep(config.getBreakSecond());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mail = new Mail(from, subject , to, contents);
						this.mailAttachFiles(mail, filePathList);
				}	
			}
			return responce;
		}
		
		/**
		 * Sendgrid 메일 단일 전송 인자 세팅 및 메일 발송 호출
		 * @throws IOException 
		 */
		@Override
		public Response sendMail(Email from, Email to, String content, String subject, List<String> filePathList) throws IOException {
			Content contents = new Content( "text/html charset=UTF-8", content);
			Mail mail = new Mail(from, subject, to, contents);
			//첨부 파일 추가
			this.mailAttachFiles(mail, filePathList);
			Response responce =  this.send(mail);
			
			return responce;
		}
		
		/**
		 * Sendgrid 메일 실제 발송
		 * @param mail
		 * @return
		 * @throws IOException 
		 */
		private Response send(Mail mail ) throws IOException {
			SendGrid sg = new SendGrid(config.getApikey());
			Request request = new Request();
			request.method = Method.POST;
			request.endpoint = "mail/send";
			Response response = new Response();
			request.body = mail.build();
			response = sg.api(request);
				
			log.info("---------------------------------------------------------");
			log.info("Title : " + mail.getSubject());
			log.info("statusCode : "+response.statusCode);
			log.info("body : "+response.body);
			log.info("---------------------------------------------------------");
			return response;
		}
		
		/**
		 * 첨부 파일 존재시 파일을 String으로 변환하여 첨부
		 */
		private Mail mailAttachFiles(Mail mail, List<String> filePathList ){
			/* 첨부 파일 존재시 */
			int index = 0;
			if(filePathList != null && filePathList.size() > 0) {
				for(String filePath : filePathList) {
					if(filePath!= null && !"".equals(filePath)){
						Attachments attachments = new Attachments();
						Tika tika = new Tika();
						try {
							/* 허용된 파일타입인지 체크 */
							if(this.isPermittedMimeType(filePath)) { 
								File file = new File(filePath);
								attachments.setContent(this.fileDecodeBase64(file));
								attachments.setType(Files.probeContentType(Paths.get(filePath)));
								attachments.setType(tika.detect(file));
								attachments.setFilename(file.getName());
								attachments.setDisposition("attachment");
								attachments.setContentId("%"+"112kjkhkas11asa"+ (index++) +"%" );
							}
						} catch (IOException e) {
							e.printStackTrace();
						}		        
						mail.addAttachments(attachments);
					}
				}
			}
			return mail;
		}
		
		/**
		 * 파일이 유효타입인지 체크
		 * @param filePath
		 * @param validMimeTypes
		 * @return
		 * @throws IOException
		 */
		private boolean isPermittedMimeType(String filePath) throws IOException {
			Path source = Paths.get(filePath);
			String mimeType = Files.probeContentType(source);
			for (String validMimeType : config.getValidMimeTypes()) {
				if (mimeType.startsWith(validMimeType)) {
					return true;
				}
			}
			return false;
		}
		
		/**
		 * 파일을 Base64 방식으로 Decode
		 * @param file
		 * @return
		 * @throws FileNotFoundException
		 * @throws IOException
		 */
		private String fileDecodeBase64(File file) throws FileNotFoundException, IOException {
			byte[] fileData = null;
			fileData = org.apache.commons.io.IOUtils.toByteArray(new FileInputStream(file));
			Encoder encoder = Base64.getEncoder();
			String encodedContents= encoder.encodeToString(fileData);
			return encodedContents;
		}
		
		/**
		 * 전달된 템플릿 아이디가 있을 경우 본문 내용대신 템플릿 세팅
		 * @param emailTemplateId
		 * @param emailValueMap
		 * @return
		 * @throws ResourceNotFoundException
		 * @throws ParseErrorException
		 * @throws Exception
		 */
		public String loadEmailTemplate(String emailTemplateId , HashMap<String, Object> emailValueMap) throws ResourceNotFoundException, ParseErrorException, Exception {
			EmailTemplate emailTemplate = new EmailTemplate();
			emailTemplate.addAttribute("emailValue", emailValueMap);
			String htmlContents = emailTemplate.getTemplate(emailTemplateId+".vm");
			return htmlContents;
		}
}
