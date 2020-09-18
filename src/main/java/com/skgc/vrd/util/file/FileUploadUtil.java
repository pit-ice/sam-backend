package com.skgc.vrd.util.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.skgc.vrd.constants.AppConstants;
import com.skgc.vrd.sample.controller.FileUploadController;
import com.skgc.vrd.util.string.StringUtil;

import lombok.extern.slf4j.Slf4j;

import com.skgc.vrd.util.string.DateUtil;


/**
 * The Class FileUploadUtil.
 */
@Slf4j
public class FileUploadUtil {

	public static final int FILE_500KB_SIZE = 512000;
	public static final int FILE_1M_SIZE = 1048576;
	public static final int FILE_2M_SIZE = 2097152;
	public static final int FILE_3M_SIZE = 3145728;
	public static final int FILE_5M_SIZE = 5242880;
	public static final int FILE_10M_SIZE = 165188379;
	
	/* @Value("${sam-backend.file.base.path}") */
	private static String fileBasePath = "c:\\springboot\\work\\sam-backend\\temp";
	/*
	 * @Value("${sam-backend.file.url.path}")
	 */
	private static String fileUrlPath = "c:";
	
	/**
	 * 
	 * 개요: 파일 디렉토리 생성
	 * @Method Name : mkdir
	 * @history
	 * ---------------------------------------------------------------------------------
	 *  변경일                    작성자                    변경내용
	 * ---------------------------------------------------------------------------------
	 *  2017. 1. 12.     taihyun      최초 작성   
	 * ---------------------------------------------------------------------------------
	 * @param file
	 */
	private static void mkdir(File file) {
		if (!file.exists()) {
			// FileBean.createDir(dir, ignoreIfExitst);
			file.mkdirs();
			log.info("mkdir");
		}

	}
	/**
	 * 
	 * 개요: 파일 사이즈 제한 체크
	 * @Method Name : isFileSizeLimit
	 * @history
	 * ---------------------------------------------------------------------------------
	 *  변경일                    작성자                    변경내용
	 * ---------------------------------------------------------------------------------
	 *  2017. 1. 12.     taihyun      최초 작성   
	 * ---------------------------------------------------------------------------------
	 * @param multipartFile
	 * @param fileSizeLimit
	 * @return
	 */
    public static boolean isFileSizeLimit(MultipartFile multipartFile, int fileSizeLimit){
    	
    	boolean isSizeValidation = true;
    	
		if(fileSizeLimit < multipartFile.getSize())
			isSizeValidation = false;
		
		return isSizeValidation;
    }
	
	/**
	 * 
	 * 개요: 파일 이미지 여부
	 * @Method Name : isImageFile
	 * @history
	 * ---------------------------------------------------------------------------------
	 *  변경일                    작성자                    변경내용
	 * ---------------------------------------------------------------------------------
	 *  2017. 1. 12.     taihyun      최초 작성   
	 * ---------------------------------------------------------------------------------
	 * @param multipartFile
	 * @return
	 * @throws IOException 
	 */
	public static boolean isImageFile(MultipartFile multipartFile) throws IOException{
		
		boolean isImageFileValidation = true;
		
		/* 확장자 체크 */
		if(!isImgExt(multipartFile.getOriginalFilename())){
			isImageFileValidation = false;
			return isImageFileValidation;
		}
		
		/* ContentType 확인 */
		if (multipartFile.getContentType().indexOf("image") == -1){
			isImageFileValidation = false;
			return isImageFileValidation;
		}
		
		/* MIME 확인 */
		Tika tika = new Tika();
		String mimeType = tika.detect(multipartFile.getInputStream());
		if(!StringUtils.startsWith(mimeType, "image")){
			isImageFileValidation = false;
			return isImageFileValidation;
		}
		
		/* 파일명에 특수 문자 들어갔는지 확인 */
		if(!checkIsCorrectFileName(multipartFile.getOriginalFilename())){
			isImageFileValidation = false;
			return isImageFileValidation;
		}
		return isImageFileValidation;
	}
	
	/**
	 * 
	 * 개요: 파일 엑셀 여부
	 * @Method Name : isImageFile
	 * @history
	 * ---------------------------------------------------------------------------------
	 *  변경일                    작성자                    변경내용
	 * ---------------------------------------------------------------------------------
	 *  2017. 1. 12.     taihyun      최초 작성   
	 * ---------------------------------------------------------------------------------
	 * @param multipartFile
	 * @return
	 * @throws IOException 
	 */
	public static boolean isExcelFile(MultipartFile multipartFile) throws IOException{
		
		boolean isExcelFileValidation = true;
		
		/* 확장자 체큰 */
		if(!isExcelExt(multipartFile.getOriginalFilename())){
			isExcelFileValidation = false;
			return isExcelFileValidation;
		}
		
		/* MIME 확인 */
		Tika tika = new Tika();
		String mimeType = tika.detect(multipartFile.getInputStream());
		
		if(mimeType.indexOf("xml") == -1){
			isExcelFileValidation = false;
			return isExcelFileValidation;
		}
		
		return isExcelFileValidation;
	}	
	
	/**
	 * 
	 * 개요: 용량을 추출합니다.(MB까지는 잘됩니다.)
	 * @Method Name : fileSize
	 * @history
	 * ---------------------------------------------------------------------------------
	 *  변경일                    작성자                    변경내용
	 * ---------------------------------------------------------------------------------
	 *  2017. 4. 13.     taihyun      최초 작성   
	 * ---------------------------------------------------------------------------------
	 * @param multipartFile
	 * @return
	 */
	public static String fileSize(MultipartFile multipartFile){
		return FileUtils.byteCountToDisplaySize(multipartFile.getSize());
	}
	
	/**
	 * 
	 * 개요: 파일 업로드 기본 기능
	 * @Method Name : singleFileUpload
	 * @history
	 * ---------------------------------------------------------------------------------
	 *  변경일                    작성자                    변경내용
	 * ---------------------------------------------------------------------------------
	 *  2017. 1. 12.     taihyun      최초 작성   
	 * ---------------------------------------------------------------------------------
	 * @param multipartFile
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static Map<String, String> singleFileUpload(MultipartFile multipartFile, String filePath) throws IllegalStateException, IOException{

		Map<String, String> fileInfoMap = new HashMap<String, String>();
		
		String fileName = multipartFile.getOriginalFilename();	
		
		//String newfileName =  fileName.split("\\.")[0] +  AppConstants.UNDERLINE + System.currentTimeMillis() + "."+ fileName.split("\\.")[1];
		String newfileName =  StringUtil.getRandomTxt(7) +  AppConstants.UNDERLINE + System.currentTimeMillis() + "."+ fileName.substring(fileName.lastIndexOf( "." )+1);
		String fileFullPath = AppConstants.SLASH + fileBasePath + AppConstants.SLASH +filePath + AppConstants.SLASH + DateUtil.getCurrentDateAsStringOneMonth(Locale.KOREA, "-") + AppConstants.SLASH + newfileName;

		log.info("newfileName : " + newfileName);
		log.info("fileFullPath : " + fileFullPath);
		File file = new File(fileFullPath);
		
		log.info("file : " + file);
		mkdir(file);
		
		log.info("multipartFile : " + multipartFile);
		
        multipartFile.transferTo(file);
        
        /* 파일 원본 명 */
        fileInfoMap.put("originalFileName", fileName);
        /* 새로운 파일 명 */
        fileInfoMap.put("nowFileName", newfileName);
        /* 파일 풀 패스(url 제외) */
        fileInfoMap.put("fileFullPath", fileFullPath);
        /* 파일 url */
        fileInfoMap.put("fileUrlPath", fileUrlPath);
        
        log.info("sss" + file);
        return fileInfoMap;
	}
	
	/* 파일 삭제 */
	public static void fileDelete(String filePath){
		File f = new File(filePath);
		f.delete();
	}
	
	/* 첨부 파일 명 확장자 체크 */
	 public static boolean isAllowedFileExtension (String str) {
		  String allowPattern = ".+\\.(rar|ppt|ppt.|doc|doc.|zip|xls|xls.|pdf|txt|jpg|gif|bmp|png)$";
		  boolean result = false;
		  
		  Pattern p = Pattern.compile(allowPattern);
	      Matcher m = p.matcher(str);
	      result = m.matches();
		  
		  return result;
	}
	 
	/* 이미지 확장자 체크 */
	public static boolean isImgExt (String fileName) {
		
		boolean result = false;
		
		String[] chkImgExtArr = {"jpg", "gif", "bmp", "png", "jpeg"};
		String ext = fileName.substring(fileName.lastIndexOf( "." )+1);
		  
		for(String chkImgExt : chkImgExtArr){
			if(chkImgExt.equals(ext.toLowerCase())){
				result = true;
				break;
			}
		}
		
		return result;
	}	
	
	/* 엑셀 확장자 체크 */
	public static boolean isExcelExt (String fileName) {
		
		boolean result = false;
		
		String[] chkExcelExtArr = {"xls", "xlsm", "xlsb", "xltx", "xltm", "xlt", "xlm", "xlw", "xlsx"};
		String ext = fileName.substring(fileName.lastIndexOf( "." )+1);
		  
		for(String chkExcelExt : chkExcelExtArr){
			if(chkExcelExt.equals(ext.toLowerCase())){
				result = true;
				break;
			}
		}
		
		return result;
	}		
	
	/* 블로그 첨부 파일 명 확장자 체크 */
	 public static boolean isTechBlogAllowedFileExtension (String fileName) {
		 boolean result = false;
		 
	  	String[] chkFileExtArr = {"pdf","jpg","jpg","gif","bmp","png"};
		String ext = fileName.substring(fileName.lastIndexOf( "." )+1);
		  
		for(String chkFileExt : chkFileExtArr){
			if(chkFileExt.equals(ext.toLowerCase())){
				result = true;
				break;
			}
		}
		
		return result;
	}
	 
	 /* 블로그 첨부 파일 명 확장자 체크 */
	 public static boolean isForumAllowedFileExtension (String fileName) {
		 boolean result = false;
		 
	  	String[] chkFileExtArr = {"bmp","gif","jpg","jpeg","png","xls","xlsx","ppt","pptx","doc","docx","pdf"};
		String ext = fileName.substring(fileName.lastIndexOf( "." )+1);
		  
		for(String chkFileExt : chkFileExtArr){
			if(chkFileExt.equals(ext.toLowerCase())){
				result = true;
				break;
			}
		}
		
		return result;
	 }
	 
	 /* 블로그 첨부 파일 명 확장자 체크 bmp, gif, jpg, jpeg, png, xls, xlsx, ppt, pptx, doc, docx, pdf */
	 public static boolean isForumMimeType (MultipartFile multipartFile) throws IOException {
		 
		 boolean isValidation = false;
		 
		/* MIME 확인 */
		Tika tika = new Tika();
		
		String mimeType = tika.detect(multipartFile.getInputStream());

		if(StringUtils.startsWith(mimeType, "image") || mimeType.indexOf("xml") > -1 
				|| mimeType.indexOf("msoffice") > -1 || mimeType.indexOf("pdf") > -1){
			isValidation = true;
		}

		return isValidation;
	}	 	 
	 
	/* 첨부파일 블랙 리스트 확장자 체크 */
	public static boolean isBlackListAttcExt(String fileName){
		
		boolean result = false;
		 
		String[] chkAttcExtArr = {"bat", "cmd", "com", "cpl", "exe", "js", "scr", "vbs","jsp","ph","inc","lib"};
		String ext = fileName.substring(fileName.lastIndexOf( "." )+1);
		
		for(String chkAttcExt : chkAttcExtArr){
			if(chkAttcExt.equals(ext.toLowerCase())){
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public static void main(String[] args){
		System.out.println(FileUtils.byteCountToDisplaySize(250484331));
	}
	
	/**
	 * 확장자를 제외한 파일명에 허용되지 않은 문자가 포함되어 있는지 체크
	 * @param fileName
	 * @return
	 */
	public static boolean checkIsCorrectFileName(String fileName){
		boolean result = false;
		if(fileName != null && !fileName.isEmpty()){
			String checkText= fileName.substring(0, fileName.lastIndexOf("."));
			result = !checkText.matches("([./\\%;])");
		}
		return result;
	}
//	/**
//	 * 
//	 * 개요: 파일 업로드 기본 기능
//	 * @Method Name : singleFileUpload
//	 * @history
//	 * ---------------------------------------------------------------------------------
//	 *  변경일                    작성자                    변경내용
//	 * ---------------------------------------------------------------------------------
//	 *  2017. 1. 12.     taihyun      최초 작성   
//	 * ---------------------------------------------------------------------------------
//	 * @param multipartFile
//	 * @param filePath
//	 * @param fileName
//	 * @return
//	 * @throws Exception 
//	 */
//	public static String singleFileUpload(MultipartFile multipartFile, String filePath) throws Exception{
//
//		String fileName = multipartFile.getOriginalFilename();		
//		
//		String newfileName =  fileName.split("\\.")[0] +  AppConstants.UNDERLINE + System.currentTimeMillis() + "."+ fileName.split("\\.")[1];
//		
//		String serviceFolder = AppConstants.SLASH + filePath + AppConstants.SLASH + DateUtil.getCurrentDateAsStringOneMonth(Locale.KOREA, "-") + AppConstants.SLASH;
//		
//		String fileUploadFullPath = SystemPropertyFactory.getProperty("web.server.upload.path") + serviceFolder;
//		
//		String fileTempFullPath = SystemPropertyFactory.getProperty("was.server.upload.path") + serviceFolder;
//		
//		File file = new File(fileTempFullPath + newfileName); 
//		
//		mkdir(file);
//		
//		/* 파일 업로드 */
//        multipartFile.transferTo(file);
//        
//        /* 업로드 된 파일을 SFTP로 웹서버로 올립니다. */
//        SFTPUtil sftpUtil = new SFTPUtil();
//
//        sftpUtil.upload(fileUploadFullPath, new File(fileTempFullPath + newfileName));
//        sftpUtil.disconnection();
//        
//        return newfileName;
//	}	
	
}