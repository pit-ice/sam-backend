package com.skgc.vrd.sample.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.skgc.vrd.account.model.MessageResponse;
import com.skgc.vrd.constants.AppConstants;
import com.skgc.vrd.sample.model.FileUploadEntity;
import com.skgc.vrd.util.excel.ExcelUtil;
import com.skgc.vrd.util.file.FileDownloadUtil;
import com.skgc.vrd.util.file.FileUploadUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/sample")
public class FileUploadController {

	/* @Value("${sam-backend.file.path}") */
	private static String filePath = "upload";

	@PostMapping("/file")
	public ResponseEntity<?> file(@ModelAttribute FileUploadEntity uploadFiles) {

		log.info("aaaaaaaaaaa" + uploadFiles);
		log.info("aaaaaaaaaaa" + uploadFiles.getName());
		log.info("aaaaaaaaaaa" + uploadFiles.getSize());
		log.info("uploadFiles : " + uploadFiles.getAttcFileData1());
		log.info("uploadFiles : " + uploadFiles.getAttcFileData1().getOriginalFilename());
		// 2017.9.7 첨부파일 기능 추가
		/* 첨부파일 1 */
		/* 첨부파일 1 */
		/*
		 * if(uploadFiles.getAttcFileData1() != null){ 첨부파일 2, 확장자 체크 (B001) .bmp, .gif,
		 * .jpg, .jpeg, .png, .xls, .xlsx, .ppt, .pptx, .doc, .docx, .pdf만 사용 가능합니다.
		 * if(!FileUploadUtil.isForumAllowedFileExtension(uploadFiles.getAttcFileData1()
		 * .getOriginalFilename())){
		 * log.info("uploadFiles.getAttcFileData1().getOriginalFilename() : " +
		 * uploadFiles.getAttcFileData1().getOriginalFilename()); 첨부파일 1, 제한을 넘을 경우
		 * (B002) }else
		 * if(!FileUploadUtil.isFileSizeLimit(uploadFiles.getAttcFileData1(),
		 * FileUploadUtil.FILE_2M_SIZE)){
		 * 
		 * 첨부파일2, mime타입 체크 } else try {
		 * if(!FileUploadUtil.isForumMimeType(uploadFiles.getAttcFileData1())){
		 * 
		 * } } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */
		if (uploadFiles.getAttcFileData1() != null) {
			Map<String, String> attcFile1Map = new HashMap<String, String>();
			/* 첨부파일1 정보 */
			try {
				attcFile1Map = (Map<String, String>) FileUploadUtil.singleFileUpload(uploadFiles.getAttcFileData1(),
						filePath);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			uploadFiles.setAttcFile1(attcFile1Map.get("fileFullPath"));
			uploadFiles.setName(attcFile1Map.get("originalFileName"));
			uploadFiles.setSize(FileUploadUtil.fileSize(uploadFiles.getAttcFileData1()));
		}

		return ResponseEntity.ok(new MessageResponse("File upload successfully!"));

	}
	
	@PostMapping("/multfile")
	public ResponseEntity<?> multfile(@RequestParam(value="attcFileData1", required=false) MultipartFile[] attcFileData1) {
		
		/* 파일이 존재하면 파일 업로드  */
		if(attcFileData1 != null){
		
			for(int i=0; i < attcFileData1.length; i++){
				
				/* 첨부파일1 정보 */
				try {
					
					FileUploadUtil.singleFileUpload(attcFileData1[i], filePath);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				
			}
		}
		

		 

		return ResponseEntity.ok(new MessageResponse("File upload successfully!"));

	}

	@GetMapping("/filedown")
	public void filedown(@ModelAttribute FileUploadEntity uploadFiles, HttpServletRequest request,
			HttpServletResponse response) {
		FileDownloadUtil fileDownloadUtil = new FileDownloadUtil();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("fileName", "aa");
		model.put("filePath",
				"http://postfiles.pstatic.net/MjAxNzA0MjhfMjgx/MDAxNDkzMzcxMTA0OTY0.oaRPyvsTK5dg0jJcGeNvIjawMbYfY-gKgj5AalMyKaIg.Uj0rzkxqTBEFXqaSj305WHIsILQSi3BrlxNdst3AJAQg.PNG.qhfk545/%EB%A0%88%EC%A0%84%EB%93%9C.png?type=w966");

		try {
			fileDownloadUtil.renderMergedOutputModel(model, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@PostMapping("/excel")
	public void excel(@ModelAttribute FileUploadEntity uploadFiles) {

		log.info("aaaaaaaaaaa" + uploadFiles);
		log.info("aaaaaaaaaaa" + uploadFiles.getName());
		log.info("aaaaaaaaaaa" + uploadFiles.getSize());
		log.info("uploadFiles : " + uploadFiles.getAttcFileData1());
		log.info("uploadFiles : " + uploadFiles.getAttcFileData1().getOriginalFilename());

		List<String[]> excelList = ExcelUtil.getExcelList(uploadFiles.getAttcFileData1(), AppConstants.NUMBER_ONE);

		for (int i = 0; i < excelList.size(); i++) {
			System.out.println(excelList.toString());
		}

		System.out.println(excelList);

	}

}
