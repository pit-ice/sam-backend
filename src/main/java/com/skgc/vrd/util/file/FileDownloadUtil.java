package com.skgc.vrd.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.skgc.vrd.sample.controller.FileUploadController;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class FileDownloadUtil extends AbstractView {

	public FileDownloadUtil() {
		setContentType("application/octet-stream;charset=utf-8");
	}

	@Override
	public void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 다운로드 받을 때 저장할 파일명    
		String fileName = (String) model.get("fileName");    
		// 다운로드 받을 파일경로    
		String downloadFile = (String) model.get("filePath");
		
		File file = new File(downloadFile);
		
		response.setContentType(getContentType());
		response.setContentLength((int) file.length());

		String userAgent = request.getHeader("User-Agent");
		
        if(userAgent.contains("MSIE") || userAgent.contains("Trident")){             
        	fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
        	
        }else if(userAgent.contains("Chrome")){
        	StringBuffer sb = new StringBuffer(); 
        	for (int i = 0; i < fileName.length(); i++) { 
        		char c = fileName.charAt(i); 
        		
        		if (c > '~') { 
        			sb.append(URLEncoder.encode("" + c, "UTF-8")); 
        		} else {
        			sb.append(c);
        		} 
        	} 
        	
        	fileName = sb.toString();

        } else {               
        	fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }

		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;

		log.info("sssss" + file);
		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException ex) {
			}
		}

		out.flush();
	}
}
