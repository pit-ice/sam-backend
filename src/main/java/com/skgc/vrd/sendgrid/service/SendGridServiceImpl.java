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
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.skgc.vrd.sendgrid.model.SendGridMailVO;

@Service
public class SendGridServiceImpl implements SendGridService {


		private final Logger log = LoggerFactory.getLogger(this.getClass());
		
		/* 대량 메일 전송시 한번에 최대 전송할 갯수 */
		private final Integer MAXIMUM_NUMBER_OF_EMAIL_SENT = 500;
		
		/* 메인 전송전 SLEEP 되는 초 */
		private final Integer BREAK_SECONDS_BEFORE_SENDING_EMAIL = 30; 
		
		/* Sendgird Key (SK에서 키 전달시 변경)  */
		private  final static String SENDGRID_API_KEY = "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";		
		/* 첨부 가능한 파일 타입 */
		private String[] validMimeTypes = {"image", "application/pdf" };
		
		@Override
		public Response sendMail(SendGridMailVO sendGridMail) {
			
			List<Personalization> perList = sendGridMail.getPersonalizations();
			if(perList!= null && perList.size() > 0) {
				return this.sendMails(sendGridMail.getPersonalizations(), sendGridMail.getFrom(),  sendGridMail.getTo(), sendGridMail.getContent(), sendGridMail.getSubject(), sendGridMail.getFilePathList());
			}else {
				return this.sendMail(sendGridMail.getFrom(), sendGridMail.getTo(), sendGridMail.getContent(), sendGridMail.getSubject(), sendGridMail.getFilePathList());
			}
		}
		
		/**
		 * Sendgrid 메일 여러개 전송 인자 세팅 및 메일 발송 호출
		 */
		@Override
		public Response sendMails(List<Personalization> personalizationList, Email from, Email to, String content, String subject, List<String> filePathList) {
			Content contents = new Content( "text/html charset=UTF-8", content);
			Response responce = new Response();
			Mail mail = new Mail(from, subject, to, contents);
			//첨부 파일 추가
			this.mailAttachFiles(mail, filePathList);

			//대량 메일 전송시을 대비한 분할 전송 
			for (int i=0; i < personalizationList.size(); i++) {
				Personalization per = personalizationList.get(i);
				mail.addPersonalization(per);
					
				if((i + 1) % MAXIMUM_NUMBER_OF_EMAIL_SENT == 0 || (i+1) == personalizationList.size() ) {
						responce =  this.send(mail);
						try {
							TimeUnit.SECONDS.sleep(BREAK_SECONDS_BEFORE_SENDING_EMAIL);
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
		 */
		@Override
		public Response sendMail(Email from, Email to, String content, String subject, List<String> filePathList) {
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
		 */
		private Response send(Mail mail ) {
			SendGrid sg = new SendGrid(SENDGRID_API_KEY);
			Request request = new Request();
			request.method = Method.POST;
			request.endpoint = "mail/send";
			Response response = new Response();
			try {
				request.body = mail.build();
				response = sg.api(request);
				
				log.info("---------------------------------------------------------");
				log.info("Title : " + mail.getSubject());
				log.info("statusCode : "+response.statusCode);
				log.info("body : "+response.body);
				log.info("---------------------------------------------------------");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
							//허용된 파일타입인지 체크
							if(this.isPermittedMimeType(filePath,validMimeTypes )) { 
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
		private boolean isPermittedMimeType(String filePath, String[] validMimeTypes) throws IOException {
			Path source = Paths.get(filePath);
			String mimeType = Files.probeContentType(source);
			for (String validMimeType : validMimeTypes) {
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
}
