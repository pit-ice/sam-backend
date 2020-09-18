package com.skgc.vrd.util.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.skgc.vrd.constants.AppConstants;
import com.skgc.vrd.util.view.ViewUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 엑셀 유틸
 * </pre>
 */
@Slf4j
public class ExcelUtil {
	
	private static final String VALUE_OBJECT_GETTER_GET_TEXT = "get";
	
	@Value("${sam-backend.upload.server.dir}")
	private static String uploadServerDir;
	
	/**
	 * <pre>
	 * 엑셀 다운로드 처리
	 * </pre>
	 *
	 * @param header
	 * @param width
	 * @param columnGetMethod
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public static void excelDownloadCalendar(HttpServletResponse response, String strYrMm, String fileName, String header, String width, String columnGetMethod, List<?> list) {

		//workbook 생성 
		HSSFWorkbook workbook = new HSSFWorkbook();
		//sheet 생성 
		HSSFSheet sheet = workbook.createSheet(fileName);
		//엑셀의 행 
		HSSFRow row = null;
		//엑셀의 셀 
		HSSFCell cell = null;
		
		HSSFFont headerFont = workbook.createFont();
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setWrapText(true);
		
		/* 엑셀 스타일 설정 */
		setExcelStyle(workbook, headerFont, headerStyle, contentStyle);	
		
		String [] headerArray = header.split(",");
		String [] widthArray = width.split(",");
		String [] columnArray = columnGetMethod.split(",");

		int rowCnt = 0;
		row = sheet.createRow((short)rowCnt);
		row.setHeight((short)300);
		
		rowCnt = rowCnt + 1;	
		sheet.addMergedRegion(new Region(0,(short)0,0,(short)6));  //제목		
		cell = row.createCell(0);
		cell.setCellValue(strYrMm);
		
		rowCnt = rowCnt + 1;		
		row = sheet.createRow((short)rowCnt);		
		
		//엑셀 타이틀 넣기
		for ( int i = 0; i < headerArray.length; i++ ) {
			cell = row.createCell(i);
			cell.setCellValue(headerArray[i]);
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, Integer.parseInt(widthArray[i]));
			
		}

		if(list !=null && list.size()>0){

			Object voObj = null;
			Class<?> voClass = null;
			Method getMethod = null;
			String value;
			String methodNm;

			for( int i = 0; i < list.size(); i++ ) {
				
				voObj = list.get(i);
				voClass = voObj.getClass();

				rowCnt = rowCnt+1;
				row = sheet.createRow(rowCnt);
				
				
				if( rowCnt%2 == 0){					
					row.setHeight((short)2000);
				}else{					
					row.setHeight((short)300);				}
				
				
				for ( int j = 0; j < columnArray.length; j++ ) {
					try {						
						if(voObj instanceof Map){														
							value = (String)((HashMap<String, String>)voObj).get((String)columnArray[j].toUpperCase());
						}else{
							/* 컬럼명 한자리인건 없으니.. 고려 안합니다. */
							methodNm = VALUE_OBJECT_GETTER_GET_TEXT+ String.valueOf(((String)columnArray[j]).charAt(0)).toUpperCase()+columnArray[j].substring(1);
							getMethod = voClass.getMethod(methodNm);
														
							//value = (String)getMethod.invoke(voObj);
							value = getMethod.invoke(voObj) == null ? AppConstants.EMPTY : getMethod.invoke(voObj).toString();
						}
						
						cell = row.createCell(j);
						cell.setCellValue(StringUtils.defaultString(value,"1"));
						cell.setCellStyle(contentStyle);
						
						

					} catch (Exception e) {
						row.createCell(j).setCellValue("");
					}
				}				
			}	
		}
		
		try {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} catch (Exception ex) {	
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control","no-cache");
		response.setContentType("application/octet-stream");		
		response.setHeader("Content-Disposition", "attachment;filename="+fileName+"_"+"day"+".xls;");

		OutputStream os = null;

		try {
			os = response.getOutputStream();
			workbook.write(os);
			os.flush();										
		} catch (IOException e) {

		} finally {					
			if ( os != null ) {
				try { os.close(); } catch (Exception e) {}
			}
		}
	}
	
	/**
	 * <pre>
	 * 엑셀 다운로드 처리 - 다중리스트 생성
	 * </pre>
	 *
	 * @param header
	 * @param width
	 * @param columnGetMethod
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public static void excelDownloadMultList(HttpServletResponse response, String fileName, String[] titleArr ,String[] headerArr, String[] withArr, String[] colArr, List reqList) {

		//workbook 생성 
		HSSFWorkbook workbook = new HSSFWorkbook();
		//sheet 생성 
		HSSFSheet sheet = workbook.createSheet(fileName);
		//엑셀의 행 
		HSSFRow row = null;
		//엑셀의 셀 
		HSSFCell cell = null;
		
		HSSFFont headerFont = workbook.createFont();
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		
		/* 엑셀 스타일 설정 */
		setExcelStyle(workbook, headerFont, headerStyle, contentStyle);	
		
		int rowCnt = 0;
		row = sheet.createRow((short)rowCnt);
		
		List<?> list = null;
		
		for(int z=0; z<reqList.size(); z++){
			
			if(z>0){
				
				rowCnt = rowCnt+2;
				row = sheet.createRow(rowCnt);
			}
        	
        	String [] headerArray = headerArr[z].split(",");      
        	String [] widthArray = withArr[z].split(",");    
        	String [] columnArray = colArr[z].split(",");      
	
			//엑셀 타이틀 넣기
        	
        	if(titleArr[z]!=null && !titleArr[z].equals("")){
				cell = row.createCell(0);
				cell.setCellValue(titleArr[z]);
				cell.setCellStyle(contentStyle);			
				rowCnt = rowCnt+1;
				row = sheet.createRow(rowCnt);
			}
			for ( int i = 0; i < headerArray.length; i++ ) {				
				cell = row.createCell(i);
				cell.setCellValue(headerArray[i]);
				cell.setCellStyle(headerStyle);
				sheet.setColumnWidth(i, Integer.parseInt(widthArray[i]));
			}		

			list = new ArrayList();
			list = (List<?>)reqList.get(z);
			
			if(list !=null && list.size()>0){

				Object voObj = null;
				Class<?> voClass = null;
				Method getMethod = null;
				String value;
				String methodNm;

				for( int i = 0; i < list.size(); i++ ) {
					
					voObj = list.get(i);
					voClass = voObj.getClass();

					rowCnt = rowCnt+1;
					row = sheet.createRow(rowCnt);
					
					for ( int j = 0; j < columnArray.length; j++ ) {
						try {						
							if(voObj instanceof Map){														
								value = (String)((HashMap<String, String>)voObj).get((String)columnArray[j].toUpperCase());
							}else{
								/* 컬럼명 한자리인건 없으니.. 고려 안합니다. */
								methodNm = VALUE_OBJECT_GETTER_GET_TEXT+ String.valueOf(((String)columnArray[j]).charAt(0)).toUpperCase()+columnArray[j].substring(1);
								getMethod = voClass.getMethod(methodNm);
															
								//value = (String)getMethod.invoke(voObj);
								value = getMethod.invoke(voObj) == null ? AppConstants.EMPTY : getMethod.invoke(voObj).toString();
							}

							cell = row.createCell(j);
							cell.setCellValue(StringUtils.defaultString(value,"1"));
							cell.setCellStyle(contentStyle);

						} catch (Exception e) {
							row.createCell(j).setCellValue("");
						}
					}	
				}
			}
		}
		
		try {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} catch (Exception ex) {	
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control","no-cache");
		response.setContentType("application/octet-stream");		
		response.setHeader("Content-Disposition", "attachment;filename="+fileName+"_"+"day"+".xls;");

		OutputStream os = null;

		try {
			os = response.getOutputStream();
			workbook.write(os);
			os.flush();										
		} catch (IOException e) {

		} finally {					
			if ( os != null ) {
				try { os.close(); } catch (Exception e) {}
			}
		}

	}
	
	/**
	 * <pre>
	 * 엑셀 다운로드 처리
	 * </pre>
	 *
	 * @param header
	 * @param width
	 * @param columnGetMethod
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public static void excelDownload(HttpServletResponse response, String fileName, String header, String width, String columnGetMethod, List<?> list) {

		//workbook 생성 
		HSSFWorkbook workbook = new HSSFWorkbook();
		//sheet 생성 
		HSSFSheet sheet = workbook.createSheet(fileName);
		//엑셀의 행 
		HSSFRow row = null;
		//엑셀의 셀 
		HSSFCell cell = null;
		
		HSSFFont headerFont = workbook.createFont();
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		
		/* 엑셀 스타일 설정 */
		setExcelStyle(workbook, headerFont, headerStyle, contentStyle);	
		
		String [] headerArray = header.split(",");
		String [] widthArray = width.split(",");
		String [] columnArray = columnGetMethod.split(",");

		int rowCnt = 0;
		row = sheet.createRow((short)rowCnt);

		//엑셀 타이틀 넣기
		for ( int i = 0; i < headerArray.length; i++ ) {
			cell = row.createCell(i);
			cell.setCellValue(headerArray[i]);
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, Integer.parseInt(widthArray[i]));
		}

		if(list !=null && list.size()>0){

			Object voObj = null;
			Class<?> voClass = null;
			Method getMethod = null;
			String value;
			String methodNm;

			for( int i = 0; i < list.size(); i++ ) {
				
				voObj = list.get(i);
				voClass = voObj.getClass();

				rowCnt = rowCnt+1;
				row = sheet.createRow(rowCnt);
				
				for ( int j = 0; j < columnArray.length; j++ ) {
					try {						
						if(voObj instanceof Map){														
							value = (String)((HashMap<String, String>)voObj).get((String)columnArray[j].toUpperCase());
						}else{
							/* 컬럼명 한자리인건 없으니.. 고려 안합니다. */
							methodNm = VALUE_OBJECT_GETTER_GET_TEXT+ String.valueOf(((String)columnArray[j]).charAt(0)).toUpperCase()+columnArray[j].substring(1);
							getMethod = voClass.getMethod(methodNm);
														
							//value = (String)getMethod.invoke(voObj);
							value = getMethod.invoke(voObj) == null ? AppConstants.EMPTY : getMethod.invoke(voObj).toString();
						}

						cell = row.createCell(j);
						cell.setCellValue(ViewUtil.replaceTextToHtml(StringUtils.defaultString(value,"1")));
						cell.setCellStyle(contentStyle);

					} catch (Exception e) {
						row.createCell(j).setCellValue("");
					}
				}				
			}	
		}
 		
		try {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} catch (Exception ex) {	
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control","no-cache");
		response.setContentType("application/octet-stream");		
		response.setHeader("Content-Disposition", "attachment;filename="+fileName+"_"+"day"+".xls;");

		OutputStream os = null;

		try {
			os = response.getOutputStream();
			workbook.write(os);
			os.flush();										
		} catch (IOException e) {

		} finally {					
			if ( os != null ) {
				try { os.close(); } catch (Exception e) {}
			}
		}

	}
	
	/**
	 * 개요: 엑셀 다운로드 처리
	 * 처리내용 :
	 *   1) 
	 *   2)
	 *   3)
	 * @Method Name : excelDownload
	 * @history
	 * ---------------------------------------------------------------------------------
	 *  변경일                    작성자                    변경내용
	 * ---------------------------------------------------------------------------------
	 *  2018. 1. 22.     Su min, Lee      최초 작성   
	 * ---------------------------------------------------------------------------------
	 * @param response
	 * @param fileName
	 * @param excelDataList
	 * @param list
	 */
	public static void excelDownload(HttpServletResponse response, String fileName, List<ExcelData> excelDataList, List<?> list) {

		//workbook 생성 
		HSSFWorkbook workbook = new HSSFWorkbook();
		//sheet 생성 
		HSSFSheet sheet = workbook.createSheet(fileName);
		//엑셀의 행 
		HSSFRow row = null;
		//엑셀의 셀 
		HSSFCell cell = null;
		
		HSSFFont headerFont = workbook.createFont();
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		
		/* 엑셀 스타일 설정 */
		setExcelStyle(workbook, headerFont, headerStyle, contentStyle);	

		int rowCnt = 0;
		row = sheet.createRow((short)rowCnt);
		
		for (int i = 0; i < excelDataList.size(); i++) {
			ExcelData excelData = excelDataList.get(i);
			cell = row.createCell(i);
			cell.setCellValue(excelData.getHeaderField());
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, excelData.getWidth());
		}

		if(list !=null && list.size()>0){

			Object voObj = null;
			Class<?> voClass = null;
			Method getMethod = null;
			Object value;
			String methodNm;
			
			for (int i = 0; i < list.size(); i++) {
				
				voObj = list.get(i);
				voClass = voObj.getClass();
				
				rowCnt++;
				row = sheet.createRow(rowCnt);
				
				for (int j = 0; j < excelDataList.size(); j++) {
					ExcelData excelData = excelDataList.get(j);
					try {					

						cell = row.createCell(j);
						/* 컬럼명 한자리인건 없으니.. 고려 안합니다. */
						methodNm = VALUE_OBJECT_GETTER_GET_TEXT + String.valueOf((excelData.getMethod()).charAt(0)).toUpperCase() + excelData.getMethod().substring(1);
						getMethod = voClass.getMethod(methodNm);
													
						//value = (String)getMethod.invoke(voObj);
						value = getMethod.invoke(voObj) == null ? AppConstants.EMPTY : getMethod.invoke(voObj);
						
						if (value instanceof Integer) {
							cell.setCellValue(((Integer) value).doubleValue());
						} else if (value instanceof Double) {
							cell.setCellValue(((Double) value).doubleValue());
						} else if (value instanceof Enum) {
							
							if (StringUtils.isNotEmpty(excelData.getEnumMethod())) {
								String enumMethodNm = VALUE_OBJECT_GETTER_GET_TEXT + String.valueOf((excelData.getEnumMethod()).charAt(0)).toUpperCase() + excelData.getEnumMethod().substring(1);
								Method enumGetMethod = value.getClass().getMethod(enumMethodNm);
								value = enumGetMethod.invoke(value) == null ? AppConstants.EMPTY : enumGetMethod.invoke(value);
							}

							cell.setCellValue(ViewUtil.replaceTextToHtml(ObjectUtils.toString(value)));
							
						} else {
							if (StringUtils.isNotEmpty(excelData.getReplaceBefore()) && StringUtils.isNotEmpty(excelData.getReplaceAfter())) {
								String[] beforeStrs = excelData.getReplaceBefore().split(",");
								String[] afterStrs = excelData.getReplaceAfter().split(",");
								
								if (beforeStrs.length == afterStrs.length) {
									for (int k = 0; k < beforeStrs.length; k++) {
										if (StringUtils.equals(ObjectUtils.toString(value), beforeStrs[k])) {
											value = afterStrs[k];
											break;
										}
									}
								}
							}
							System.out.println("value"+value);
							cell.setCellValue(ViewUtil.replaceTextToHtml(ObjectUtils.toString(value)));
						}

					} catch (Exception e) {
						row.createCell(j).setCellValue("");
					} finally {
						cell.setCellStyle(contentStyle);
					}
				}
				
			}
		}
		try {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} catch (Exception ex) {	
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control","no-cache");
		response.setContentType("application/octet-stream");		
		response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xls;");

		OutputStream os = null;

		try {
			os = response.getOutputStream();
			workbook.write(os);
			os.flush();										
		} catch (IOException e) {

		} finally {					
			if ( os != null ) {
				try { os.close(); } catch (Exception e) {}
			}
		}

	}
    /**
     * <pre>
     * 엑셀 파일 읽어서 ArrayList 로 리턴
     * </pre>
     *
     * @param excelFile
     * @return
     */
    public static ArrayList<String[]> getExcelList(String excelFile) {
    	return (ArrayList<String[]>)getExcelList(excelFile, AppConstants.NUMBER_ONE);
    }
    
    /**
     * <pre>
     * 엑셀 파일 읽어서 ArrayList 로 리턴
     * </pre>
     *
     * @param excelFile
     * @return
     */
    public static ArrayList<String[]> getExcelList(String excelFile, int startRow) {

    	startRow = startRow < AppConstants.NUMBER_ZERO ? AppConstants.NUMBER_ZERO : startRow;
    	
    	excelFile = uploadServerDir + excelFile;
    	
    	System.out.println("String ext = excelFile.substring(excelFile.lastIndexOf() + 1).toLowerCase() :" + excelFile.substring(excelFile.lastIndexOf(".") + 1).toLowerCase());
    	
        ArrayList<String[]> excelDataList = new ArrayList<String[]>();
        try {
            String ext = excelFile.substring(excelFile.lastIndexOf(".") + 1).toLowerCase();
            
            if ( "xlsx".equals(ext) ) { // 2007 버전
                XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));
                XSSFSheet sheet = wb.getSheetAt(0);
                int iTotalRows = sheet.getPhysicalNumberOfRows();
                XSSFRow row = sheet.getRow(startRow);
                int iCellCount = row.getPhysicalNumberOfCells();
                XSSFCell cell;
                String[] cellData;      
                FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();    
                DecimalFormat df = new DecimalFormat();
                
                for ( int i = startRow ; i <= iTotalRows ; i++ ) {
                    row = sheet.getRow(i);
                    cellData = new String[iCellCount + 1];
                    List<String> list = new ArrayList<String>();
                    for ( int j = 0 ; j < iCellCount ; j++ ) {
                        String str = "";
                       
                        if ( row.getCell(j) != null ) {
                            cell = row.getCell(j);
                            if ( cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC ) {
                            	if (HSSFDateUtil.isCellDateFormatted(cell)){
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
                                    str = formatter.format(cell.getDateCellValue());
                               } else{
                            	   double ddata = cell.getNumericCellValue();   
                            	   str = df.format(ddata).replace(",","");
                            	   //str = Integer.toString((int) cell.getNumericCellValue());
                               }
                            }else if ( cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA ) {
                                str = cell.getCellFormula();
                            }else if ( cell.getCellType() == XSSFCell.CELL_TYPE_STRING ) {
                                str = cell.getStringCellValue();
                            }
                        }
                        list.add(str);
                    }
                    for ( int k = 0 ; k < list.size() ; k++ ) {
                        cellData[k] = list.get(k);
                    }
                    excelDataList.add(cellData);
                }
            }else { // 2003 버전
                HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(excelFile)));
                HSSFSheet sheet = wb.getSheetAt(0);
                int iTotalRows = sheet.getPhysicalNumberOfRows();
                HSSFRow row = sheet.getRow(startRow);
                int iCellCount = row.getPhysicalNumberOfCells();
                HSSFCell cell;
                String[] cellData;                 
                FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();  
                DecimalFormat df = new DecimalFormat();

                for ( int i = startRow ; i <= iTotalRows ; i++ ) {
                    row = sheet.getRow(i);
                    cellData = new String[iCellCount + 1];
                    List<String> list = new ArrayList<String>();
                    for ( int j = 0 ; j < iCellCount ; j++ ) {
                        String str = "";
                        if ( row.getCell(j) != null ) {
                            cell = row.getCell(j);
                            if ( cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC ) {
                            	if (HSSFDateUtil.isCellDateFormatted(cell)){
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
                                    str = formatter.format(cell.getDateCellValue());
                               } else{
                            	   double ddata = cell.getNumericCellValue();   
                            	   str = df.format(ddata).replace(",","");
                            	   //str = Integer.toString((int) cell.getNumericCellValue());
                               }
                            }else if ( cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA ) {
                                str = cell.getCellFormula();
                            }else if ( cell.getCellType() == XSSFCell.CELL_TYPE_STRING ) {
                                str = cell.getStringCellValue();
                            }
                        }
                        list.add(str);
                    }
                    for ( int k = 0 ; k < list.size() ; k++ ) {
                        cellData[k] = list.get(k);
                    }
                    excelDataList.add(cellData);
                }
            }
        }
        catch ( Exception e ) {
        }

        return excelDataList;
    }    
    
    /**
     * 
     * <pre>
     * 엑셀 스타일
     * </pre>
     *
     * @param workbook
     * @param headerFont
     * @param headerStyle
     * @param contentStyle
     */
	private static void setExcelStyle(HSSFWorkbook workbook, HSSFFont headerFont, HSSFCellStyle headerStyle, HSSFCellStyle contentStyle){
		//header Style--------------------------------------------------	
		headerFont.setBoldweight((short)Font.BOLDWEIGHT_BOLD);
		headerStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setFont(headerFont);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//---------------------------------------------------------------

		//content Style--------------------------------------------------	
		contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		//---------------------------------------------------------------	
	}	
    
    /**
     * <pre>
     * 엑셀 파일 읽어서 ArrayList 로 리턴
     * </pre>
     *
     * @param excelFile
     * @return
     */
	public static ArrayList<String[]> getExcelList(MultipartFile multipartFile, int startRow) {

    	startRow = startRow < AppConstants.NUMBER_ZERO ? AppConstants.NUMBER_ZERO : startRow;
    	
    	String excelFile = multipartFile.getOriginalFilename();
    	
        ArrayList<String[]> excelDataList = new ArrayList<String[]>();
        try {
            String ext = excelFile.substring(excelFile.lastIndexOf(".") + 1).toLowerCase();
            
            if ( "xlsx".equals(ext) ) { // 2007 버전
                XSSFWorkbook wb = new XSSFWorkbook(multipartFile.getInputStream());
                XSSFSheet sheet = wb.getSheetAt(0);
                int iTotalRows = sheet.getPhysicalNumberOfRows();
                XSSFRow row = sheet.getRow(startRow);
                int iCellCount = sheet.getRow(AppConstants.NUMBER_ZERO).getPhysicalNumberOfCells();
                XSSFCell cell;
                String[] cellData;      
                FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();    
                DecimalFormat df = new DecimalFormat();
                
                System.out.println("iCellCount : " + iCellCount);
                
                
                for ( int i = startRow ; i <= iTotalRows ; i++ ) {
                    row = sheet.getRow(i);
                    cellData = new String[iCellCount];
                    List<String> list = new ArrayList<String>();
                    for ( int j = 0 ; j < iCellCount ; j++ ) {
                        String str = "";
                       
                        if ( row.getCell(j) != null ) {
                            cell = row.getCell(j);
                            if ( cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC ) {
                            	if (HSSFDateUtil.isCellDateFormatted(cell)){
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
                                    str = formatter.format(cell.getDateCellValue());
                               } else{
                            	   double ddata = cell.getNumericCellValue();   
                            	   str = df.format(ddata).replace(",","");
                            	   //str = Integer.toString((int) cell.getNumericCellValue());
                               }
                            }else if ( cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA ) {
                                str = cell.getCellFormula();
                            }else if ( cell.getCellType() == XSSFCell.CELL_TYPE_STRING ) {
                                str = cell.getStringCellValue();
                            }
                        }else{
                        	str = AppConstants.EMPTY;
                        }
                        
                        list.add(str);
                    }
                    for ( int k = 0 ; k < list.size() ; k++ ) {
                        cellData[k] = list.get(k);
                    }
                    excelDataList.add(cellData);
                }
            }else { // 2003 버전
                HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(excelFile)));
                HSSFSheet sheet = wb.getSheetAt(0);
                int iTotalRows = sheet.getPhysicalNumberOfRows();
                HSSFRow row = sheet.getRow(startRow);
                int iCellCount = row.getPhysicalNumberOfCells();
                HSSFCell cell;
                String[] cellData;                 
                FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();  
                DecimalFormat df = new DecimalFormat();

                for ( int i = startRow ; i <= iTotalRows ; i++ ) {
                    row = sheet.getRow(i);
                    cellData = new String[iCellCount];
                    List<String> list = new ArrayList<String>();
                    for ( int j = 0 ; j < iCellCount ; j++ ) {
                        String str = "";
                        if ( row.getCell(j) != null ) {
                            cell = row.getCell(j);
                            if ( cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC ) {
                            	if (HSSFDateUtil.isCellDateFormatted(cell)){
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
                                    str = formatter.format(cell.getDateCellValue());
                               } else{
                            	   double ddata = cell.getNumericCellValue();   
                            	   str = df.format(ddata).replace(",","");
                            	   //str = Integer.toString((int) cell.getNumericCellValue());
                               }
                            }else if ( cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA ) {
                                str = cell.getCellFormula();
                            }else if ( cell.getCellType() == XSSFCell.CELL_TYPE_STRING ) {
                                str = cell.getStringCellValue();
                            }
                        }else{
                        	str = AppConstants.EMPTY;
                        }
                        list.add(str);
                    }
                    for ( int k = 0 ; k < list.size() ; k++ ) {
                        cellData[k] = list.get(k);
                    }
                    excelDataList.add(cellData);
                }
            }
        }
        catch ( Exception e ) {
        }

        return excelDataList;
    }    
	
}
