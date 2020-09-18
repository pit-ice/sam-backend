/*
 * Copyright (c) SK C&C.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK C&C.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK C&C.
 */
package com.skgc.vrd.util.excel;

/**
 * 
 */
public class ExcelData {
	
	public ExcelData(String headerField, String method, Integer width) {
		this.headerField = headerField;
		this.method = method;
		this.width = width;
	}
	
	public ExcelData(String headerField, String method, Integer width, String enumMethod) {
		this.headerField = headerField;
		this.method = method;
		this.width = width;
		this.enumMethod = enumMethod;
	}
	
	public ExcelData(String headerField, String method, Integer width, String replaceBefore, String replaceAfter) {
		this.headerField = headerField;
		this.method = method;
		this.width = width;
		this.replaceBefore = replaceBefore;
		this.replaceAfter = replaceAfter;
	}
	
	private String headerField;
	private String method;
	private Integer width;
	private String enumMethod;
	private String replaceBefore;
	private String replaceAfter;
	
	public String getHeaderField() {
		return headerField;
	}
	public void setHeaderField(String headerField) {
		this.headerField = headerField;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getReplaceBefore() {
		return replaceBefore;
	}

	public void setReplaceBefore(String replaceBefore) {
		this.replaceBefore = replaceBefore;
	}

	public String getReplaceAfter() {
		return replaceAfter;
	}

	public void setReplaceAfter(String replaceAfter) {
		this.replaceAfter = replaceAfter;
	}

	public String getEnumMethod() {
		return enumMethod;
	}

	public void setEnumMethod(String enumMethod) {
		this.enumMethod = enumMethod;
	}
}
