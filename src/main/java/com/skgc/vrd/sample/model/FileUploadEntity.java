package com.skgc.vrd.sample.model;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadEntity {

	private MultipartFile attcFileData1;			/* 첨부파일1 */
	private MultipartFile[] attcFileData2;			/* 첨부파일1 */
	private String attcFile1;						/* 첨부파일1 URL */
	private String name;					/* 첨부파일명1 */
	private String size;					/* 첨부파일 사이즈1 */

	public MultipartFile getAttcFileData1() {
		return attcFileData1;
	}
	public void setAttcFileData1(MultipartFile attcFileData1) {
		this.attcFileData1 = attcFileData1;
	}
	public MultipartFile[] getAttcFileData2() {
		return attcFileData2;
	}
	public void setAttcFileData2(MultipartFile[] attcFileData2) {
		this.attcFileData2 = attcFileData2;
	}
	public String getAttcFile1() {
		return attcFile1;
	}
	public void setAttcFile1(String attcFile1) {
		this.attcFile1 = attcFile1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	
	
}
