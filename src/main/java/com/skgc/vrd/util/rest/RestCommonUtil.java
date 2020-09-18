package com.skgc.vrd.util.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skgc.vrd.constants.AppConstants;
import com.skgc.vrd.util.rest.handler.PassResponseErrorHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestCommonUtil {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final int DEFAULT_CONNECT_TIME = 3000;						/* 연결 시간 3초 */
	private static final int DEFAULT_READ_TIMEOUT = 30000;						/* 조회 타임아웃 시간 30초 */
	
	@Autowired
	private PassResponseErrorHandler errorHandler;
	
	private static RestTemplate restTemplate;
	
	
	@PostConstruct
	public void initialize(){
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(DEFAULT_CONNECT_TIME);						
		requestFactory.setReadTimeout(DEFAULT_READ_TIMEOUT);
		requestFactory.setOutputStreaming(false);
		
		// custom message converter
		restTemplate = new RestTemplate(requestFactory);
		List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		messageConverters.remove(StringHttpMessageConverter.class);
		messageConverters.add(new ResourceHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		
		FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
		formHttpMessageConverter.addPartConverter(new MappingJackson2HttpMessageConverter());
		formHttpMessageConverter.addPartConverter(new ByteArrayHttpMessageConverter());
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		stringHttpMessageConverter.setWriteAcceptCharset(false);
		formHttpMessageConverter.addPartConverter(stringHttpMessageConverter);
		formHttpMessageConverter.addPartConverter(new ResourceHttpMessageConverter()); // This is hope driven programming

		formHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.MULTIPART_FORM_DATA, MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
		messageConverters.add(formHttpMessageConverter);

		restTemplate.setErrorHandler(errorHandler);
	}
	private static HttpHeaders defaultHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		headers.set(AppConstants.BackEnd.X_AIBRIL_CLIENT_ID, AppConstants.BackEnd.X_AIBRIL_CLIENT_ID_VALUE);
		headers.set(AppConstants.BackEnd.X_AIBRIL_CLIENT_SECRET, AppConstants.BackEnd.X_AIBRIL_CLIENT_SECRET_VALUE);
		
		return headers;
	}
	
	private static HttpHeaders defaultHeaders(String accessToken){
		HttpHeaders headers = defaultHeaders();
		if(accessToken != null){
			headers.set(HttpHeaders.AUTHORIZATION, accessToken);
		}
		return headers;
	}
	
	
	
	/**
	 * 개요: rest teamplate get api call
	 * @Method Name : get
	 * @history
	 * ---------------------------------------------------------------------------------
	 *  변경일                    작성자                    변경내용
	 * ---------------------------------------------------------------------------------
	 *  2017. 4. 18.     Young ki, Chun      최초 작성   
	 * ---------------------------------------------------------------------------------
	 * @param url
	 * @return
	 */
	public static ResponseEntity<String> get(String url){
		return get(url, null);
	}
	
	public static ResponseEntity<String> get(String url, String accessToken){
		// headers
		HttpHeaders headers = defaultHeaders(accessToken);
		// request entity
		RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, toUri(url));
		// exchange
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		
		// logging
		logging(requestEntity, responseEntity);
		
		return responseEntity;
	}
	
	public static ResponseEntity<String> Testget(String url, HttpHeaders headers) throws UnsupportedEncodingException{

		log.info("url : " + url);
		log.info("headers : " + headers);
		
		RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, toUri(url));
		// exchange
		ResponseEntity<byte[]> result = restTemplate.exchange(requestEntity, byte[].class);
		
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(new String(result.getBody(), "UTF-8"), result.getHeaders(), result.getStatusCode());
		
		// logging
		logging(requestEntity, responseEntity);
		
		return responseEntity;
	}
	
	
	
	/**
	 * 개요: rest teamplate post api call
	 * @Method Name : post
	 * @history
	 * ---------------------------------------------------------------------------------
	 *  변경일                    작성자                    변경내용
	 * ---------------------------------------------------------------------------------
	 *  2017. 4. 18.     Young ki, Chun      최초 작성   
	 * ---------------------------------------------------------------------------------
	 * @param url
	 * @param body
	 * @return
	 */
	public static ResponseEntity<String> post(String url, Object body){
		return post(url, null, body);
	}
	
	public static ResponseEntity<String> post(String url, String accessToken, Object body){
		
		log.debug("url : " + url);
		log.debug("body : " + body);
		
		// headers
		HttpHeaders headers = defaultHeaders(accessToken);
		// request entity
		RequestEntity<?> requestEntity = new RequestEntity<>(body, headers, HttpMethod.POST, toUri(url));
		// exchange
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		
		// logging
		logging(requestEntity, responseEntity);
		
		return responseEntity;
	}
	
	public static ResponseEntity<String> post1(String url, HttpHeaders headers, ByteArrayResource fileResource) throws UnsupportedEncodingException{
		
		log.debug("url : " + url);
		log.debug("fileResource : " + fileResource);
		log.debug("headers : " + headers);
		
		RequestEntity<?> requestEntity = new RequestEntity<>(fileResource, headers, HttpMethod.POST, toUri(url));
		
		// exchange
		ResponseEntity<String> result = restTemplate.exchange(requestEntity, String.class);
		// convert 
	//	ResponseEntity<String> responseEntity = new ResponseEntity<String>(new String(result.getBody(), "UTF-8"), result.getHeaders(), result.getStatusCode());
		
		// logging
		//logging(requestEntity, result);
		log.info(" result.getBody() : " + result.getBody());
		
		return result;
	}
	
	public static ResponseEntity<Object> Testpost(String url, HttpHeaders headers, String body) throws UnsupportedEncodingException{
		return Testpost(url, headers, body, null);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ResponseEntity<Object> Testpost(String url, HttpHeaders headers, String body, MultiValueMap<String, Object> data) throws UnsupportedEncodingException{

		log.debug("url : " + url);
		log.debug("body : " + body);
		log.debug("data : " + data);
		log.debug("headers : " + headers);
		
		RequestEntity<?> requestEntity = null;
		
		if(data == null){
			requestEntity = new RequestEntity<>(body, headers, HttpMethod.POST, toUri(url));
		}else{
			requestEntity = new RequestEntity<>(data, headers, HttpMethod.POST, toUri(url));
		}
	
		// exchange
		ResponseEntity<Object> responseEntity = (ResponseEntity)restTemplate.exchange(requestEntity, byte[].class);
		
		System.out.println("responseEntity.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION) : " + responseEntity.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION));
		
		/* CONTENT_DISPOSITION이 존재한다는건 파일로 응답이 왔다는 것 입니다. 이경우 body는 byte로 처리 아닐경우는 string으로 처리 합니다. */
		if(responseEntity.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION) == null 
				|| ((String)responseEntity.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION).get(0)).contains(".json")){
			responseEntity = new ResponseEntity<Object>(new String((byte[])responseEntity.getBody(), "UTF-8"), responseEntity.getHeaders(), responseEntity.getStatusCode());
			logging(requestEntity, (ResponseEntity)responseEntity);
		}
		
		return responseEntity;
	}
	
	
	
	/**
	 * 개요: rest template put api call
	 * @Method Name : put
	 * @history
	 * ---------------------------------------------------------------------------------
	 *  변경일                    작성자                    변경내용
	 * ---------------------------------------------------------------------------------
	 *  2017. 4. 18.     Young ki, Chun      최초 작성   
	 * ---------------------------------------------------------------------------------
	 * @param url
	 * @param body
	 * @return
	 */
	public static ResponseEntity<String> put(String url, Object body){
		return put(url, null, body);
	}
	public static ResponseEntity<String> put(String url, String accessToken, Object body){
		// headers
		HttpHeaders headers = defaultHeaders(accessToken);
		// request entity
		RequestEntity<?> requestEntity = new RequestEntity<>(body, headers, HttpMethod.PUT, toUri(url));
		// exchange
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		
		// logging
		logging(requestEntity, responseEntity);
		
		return responseEntity;
	}
	
	public static ResponseEntity<String> Testput(String url, HttpHeaders headers,  MultiValueMap<String, Object> data) throws UnsupportedEncodingException{
		
		log.info("url : " + url);
		log.info("data : " + data);
		log.info("headers : " + headers);
		RequestEntity<?> requestEntity = new RequestEntity<>(data, headers, HttpMethod.PUT, toUri(url));
		
		// 2017-09-18 austin
		restTemplate.setRequestFactory(MySimpleClientHttpRequestFactory());
		
		// exchange
		ResponseEntity<byte[]> result = restTemplate.exchange(requestEntity, byte[].class);
		
		// convert 
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(new String(result.getBody(), "UTF-8"), result.getHeaders(), result.getStatusCode());
		logging(requestEntity, responseEntity);
		
		return responseEntity;
	}
	
	private static SimpleClientHttpRequestFactory MySimpleClientHttpRequestFactory(){
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory(){
			@Override
			protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
				super.prepareConnection(connection, httpMethod);
				connection.setInstanceFollowRedirects(false);
			}
		};
		requestFactory.setOutputStreaming(false);
		return requestFactory;
	}
	
	
	
	/**
	 * 개요: rest template delete api call
	 * @Method Name : delete
	 * @history
	 * ---------------------------------------------------------------------------------
	 *  변경일                    작성자                    변경내용
	 * ---------------------------------------------------------------------------------
	 *  2017. 4. 18.     Young ki, Chun      최초 작성   
	 * ---------------------------------------------------------------------------------
	 * @param url
	 * @return
	 */
	public static ResponseEntity<String> delete(String url){
		return delete(url, null);
	}
	public static ResponseEntity<String> delete(String url, String accessToken){
		// headers
		HttpHeaders headers = defaultHeaders(accessToken);
		// request entity
		RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.DELETE, toUri(url));
		// exchange
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		
		// logging
		logging(requestEntity, responseEntity);
		
		return responseEntity;
	}
	
	public static ResponseEntity<String> nodefaultHeadersPost(String url, HttpHeaders httpHeaders, Object body){
		
		log.debug("url : " + url);
		log.debug("body : " + body);
		
		// headers
		// request entity
		RequestEntity<?> requestEntity = new RequestEntity<>(body, httpHeaders, HttpMethod.POST, toUri(url));
		// exchange
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		
		// logging
		logging(requestEntity, responseEntity);
		
		return responseEntity;
	}
	
	public static ResponseEntity<String> nodefaultHeadersDelete(String url, HttpHeaders httpHeaders, Object body){
		// headers
		// request entity
		RequestEntity<?> requestEntity = new RequestEntity<>(body, httpHeaders, HttpMethod.DELETE, toUri(url));
		// exchange
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		
		// logging
		logging(requestEntity, responseEntity);
		
		return responseEntity;
	}

	
	private static URI toUri(String url){
		try {
			return new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			// TODO: exception throws 해야 함.
			throw new RuntimeException(e);
		}
	}
	
	
	@Async
	private static synchronized void logging(RequestEntity<?> requestEntity, ResponseEntity<String> responseEntity){
		try {
			// logging
			log.info("================================> Rest API logging [ Start ]");
			log.info("|================================> Request [ " + requestEntity.getMethod().name() + " ] " + requestEntity.getUrl().toString());
			log.info("|================================> Request Header");
			HttpHeaders headers = requestEntity.getHeaders();
			Set<String> keySet = headers.keySet();
			for(String key : keySet){
				log.info("| " + key + " : " + headers.get(key).get(0));
			}
			log.info("| [Request body] : " + ((requestEntity.getType() != null) ?requestEntity.getType().toString() : "no Contents" ));
			// is debug enabled
			mapper.setVisibility(mapper.getVisibilityChecker().withFieldVisibility(Visibility.ANY));
			if(log.isDebugEnabled()){
				if(requestEntity.getBody() != null)
					log.info("body =>\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestEntity.getBody()));
			}
			
			log.info("|================================> Response");
			log.info("| Http Status : " + responseEntity.getStatusCodeValue() + " " + responseEntity.getStatusCode().name());
			log.info("|================================> Response Header");
			HttpHeaders responseHeaders = responseEntity.getHeaders();
			for(String key : responseHeaders.keySet()){
				log.info("| " + key + " : "  + responseHeaders.get(key).get(0));
			}
			log.info("| [Response body] ");
			// is debug enabled
			if(log.isDebugEnabled()){
				if(responseEntity.getBody() != null){
					log.info("body =>\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readValue(responseEntity.getBody(), Object.class)));
				}
			}
			log.info("================================> Rest API logging [ End ]");
		} catch (Exception e) {
			// Nothing to do (only print stackTrace)
			e.printStackTrace();
		}
		
	}

}
