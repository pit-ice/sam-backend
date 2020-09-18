
package com.skgc.vrd.util.view;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class ViewUtil {

	/**
	 * <pre>
	 * 줄바꿈 BR 태그로 변환 처리
	 * </pre>
	 *
	 * @param str
	 * @return
	 */
	public static String replaceLFToBR(String str) {
		if (str == null || str.trim().equals("")) {
			return "";
		}		
		return str.replaceAll("\n", "<br>");
	}

	/**
	 * <pre>
	 * text 형태를  html 형태로 변환
	 * </pre>
	 *
	 * @param str
	 * @return
	 */	
	public static String replaceTextToHtml(String str) {
		if (str == null || str.trim().equals("")) {
			return "";
		}

		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&amp;", "&");
		//공백은 유지 시킨다.
		//str = str.replaceAll("&nbsp;", " ");
		str = str.replaceAll("&apos;", "\'");
		str = str.replaceAll("&quot;", "\"");

		return str;
	}	
	
	/**
	 * <pre>
	 * text 형태를  html 형태로 변환 및 스크립트 등 문제 요소 변경 처리
	 * </pre>
	 *
	 * @param str
	 * @return
	 */
	public static String replaceTextToHtmlXss(String str) {
		if (str == null || str.trim().equals("")) {
			return "";
		}

		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&amp;", "&");
		//공백은 유지 시킨다.
		//str = str.replaceAll("&nbsp;", " ");
		str = str.replaceAll("&apos;", "\'");
		str = str.replaceAll("&quot;", "\"");

		Pattern p;

		//링크 새창
		p = Pattern.compile("(<a href[^>]+)>", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("$1 target=_blank>");

		//자바스크립트 금지
		p = Pattern.compile("javascript", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xjavascript");

		//on 으로 시작되는 스크립트 금지
		p = Pattern.compile("(<[^>]+)(\\son\\w+)", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("$1 onX");

		//스크립트 금지
		p = Pattern.compile("<script", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xscript");

		//아이프레임 금지
		p = Pattern.compile("<iframe", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xiframe");

		//오브젝트 금지
		p = Pattern.compile("<object", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xobject");

		//스타일 금지
		p = Pattern.compile("<style", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xstyle");

		//애플릿 금지
		p = Pattern.compile("<applet", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xapplet");

		//메타 금지
		p = Pattern.compile("<meta", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xmeta");
		
		//alert 
		p = Pattern.compile("alert", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xalert");		
		
		//onblur
		p = Pattern.compile("onblur", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonblur");		
		
		//onchange
		p = Pattern.compile("onchange", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonchange");		
		
		//onclick
		p = Pattern.compile("onclick", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonclick");		
		
		//ondblclick
		p = Pattern.compile("ondblclick", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xondblclick");		
		
		//onerror
		p = Pattern.compile("onerror", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonerror");		
		
		//onfocus
		p = Pattern.compile("onfocus", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonfocus");		
		
		//onload
		p = Pattern.compile("onload", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonload");		
		
		//onmouse
		p = Pattern.compile("onmouse", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonmouse");		
		
		//onscroll
		p = Pattern.compile("onscroll", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonscroll");		
		
		//onsubmit
		p = Pattern.compile("onsubmit", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonsubmit");		
		
		//onunload
		p = Pattern.compile("onunload", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonunload");				

		return str;	
	}
	/**
	 * <pre>
	 * text 형태를  html 형태로 변환 및 스크립트 등 문제 요소 변경 처리(관리자용)
	 * </pre>
	 *
	 * @param str
	 * @return
	 */
	public static String replaceTextToHtmlXssAdmin(String str) {
		if (str == null || str.trim().equals("")) {
			return "";
		}
		
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&amp;", "&");
		//공백은 유지 시킨다.
		//str = str.replaceAll("&nbsp;", " ");
		str = str.replaceAll("&apos;", "\'");
		str = str.replaceAll("&quot;", "\"");
		
		Pattern p;
		
		//링크 새창
		p = Pattern.compile("(<a href[^>]+)>", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("$1 target=_blank>");
		
		//자바스크립트 금지
		p = Pattern.compile("javascript", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xjavascript");
		
		//on 으로 시작되는 스크립트 금지
		p = Pattern.compile("(<[^>]+)(\\son\\w+)", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("$1 onX");
		
		//스크립트 금지
		p = Pattern.compile("<script", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xscript");
		
		//아이프레임 금지
	//	p = Pattern.compile("<iframe", Pattern.CASE_INSENSITIVE);
	//	str = p.matcher(str).replaceAll("<Xiframe");
		
		//오브젝트 금지
		p = Pattern.compile("<object", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xobject");
		
		//스타일 금지
		p = Pattern.compile("<style", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xstyle");
		
		//애플릿 금지
		p = Pattern.compile("<applet", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xapplet");
		
		//메타 금지
		p = Pattern.compile("<meta", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xmeta");
		
		//alert 
		p = Pattern.compile("alert", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xalert");		
		
		//onblur
		p = Pattern.compile("onblur", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonblur");		
		
		//onchange
		p = Pattern.compile("onchange", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonchange");		
		
		//onclick
		p = Pattern.compile("onclick", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonclick");		
		
		//ondblclick
		p = Pattern.compile("ondblclick", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xondblclick");		
		
		//onerror
		p = Pattern.compile("onerror", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonerror");		
		
		//onfocus
		p = Pattern.compile("onfocus", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonfocus");		
		
		//onload
		p = Pattern.compile("onload", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonload");		
		
		//onmouse
		p = Pattern.compile("onmouse", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonmouse");		
		
		//onscroll
		p = Pattern.compile("onscroll", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonscroll");		
		
		//onsubmit
		p = Pattern.compile("onsubmit", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonsubmit");		
		
		//onunload
		p = Pattern.compile("onunload", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xonunload");				
		
		return str;	
	}
	
	/**
	 * <pre>
	 * text 형태를  html 형태로 변환 및 스크립트 등 문제 요소 변경 처리
	 * </pre>
	 *
	 * @param str
	 * @return
	 */
	public static String replaceHtmlToTextXss(String str) {
		
		if (str == null || str.trim().equals("")) {
			return "";
		}

		Pattern p;

		//링크 새창
		p = Pattern.compile("(<a href[^>]+)>", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("$1 target=_blank>");

		//자바스크립트 금지
		p = Pattern.compile("javascript", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("Xjavascript");

		//on 으로 시작되는 스크립트 금지
		p = Pattern.compile("(<[^>]+)(\\son\\w+)", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("$1 onX");

		//스크립트 금지
		p = Pattern.compile("<script", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xscript");

		//아이프레임 금지
		p = Pattern.compile("<iframe", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xiframe");

		//오브젝트 금지
		p = Pattern.compile("<object", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xobject");

		//스타일 금지
		p = Pattern.compile("<style", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xstyle");

		//애플릿 금지
		p = Pattern.compile("<applet", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xapplet");

		//메타 금지
		p = Pattern.compile("<meta", Pattern.CASE_INSENSITIVE);
		str = p.matcher(str).replaceAll("<Xmeta");

		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("&", "&amp;");
		//공백은 유지 시킨다.
		//str = str.replaceAll("&nbsp;", " ");
		str = str.replaceAll("\'", "&apos;");
		str = str.replaceAll("\"", "&quot;");		
		
		return str;	
	}	
	

	/**
	 * <pre>
	 * 3자리 콤마
	 * </pre>
	 *
	 * @param int
	 * @return String
	 */
	public static String makeMoneyType(int v) 
	{
		String s = String.format("%, d", v);
		return s; 
	}
	
	/**
	 * 폰 번호 하이픈 추가
	 * @param phoneNumber
	 * @return
	 */
	public static String getPhoneHyphen(String phoneNumber){
		
		if(phoneNumber == null)		return null;
		
		/* 핸드폰 번호가 11자리일 경우 */
		if(phoneNumber.length() == 11){
			phoneNumber = phoneNumber.substring(0,3) +"-"+ phoneNumber.substring(3,7) +"-"+ phoneNumber.substring(7);
		/* 핸드폰 자리가 10자리인 경우 */
		}else if(phoneNumber.length() == 10){
			phoneNumber = phoneNumber.substring(0,3) +"-"+ phoneNumber.substring(3,6) +"-"+ phoneNumber.substring(6);
		}
		
		/* 최종적으로 11자리 or 10자리가 아닐경우 정상적인 핸드폰 번호가 아닐수도 있다는 판단으로 하이픈 처리 안하고 그냥 리턴 합니다. */
		/* 11자리 or 10자리일경우 하이픈 처리하여 리턴 */
		
		return phoneNumber;
	}
	
	/**
	 * 전화번호 하이픈 추가
	 * @param phoneNumber
	 * @return
	 */
	public static String getTelHyphen(String phoneNumber){
		
		if(phoneNumber == null)		return null;
		
		/* 핸드폰 번호가 11자리일 경우 */
		if(phoneNumber.length() == 11){
			phoneNumber = phoneNumber.substring(0,3) +"-"+ phoneNumber.substring(3,7) +"-"+ phoneNumber.substring(7);
			/* 핸드폰 자리가 10자리인 경우 */
		}else if(phoneNumber.length() == 10 && "02".equals(phoneNumber.substring(0,2))){
			phoneNumber = phoneNumber.substring(0,2) +"-"+ phoneNumber.substring(2,6) +"-"+ phoneNumber.substring(6);
		}else if(phoneNumber.length() == 9 && "02".equals(phoneNumber.substring(0,2))){
			phoneNumber = phoneNumber.substring(0,2) +"-"+ phoneNumber.substring(2,5) +"-"+ phoneNumber.substring(5);
		}else if(phoneNumber.length() == 10 && !"02".equals(phoneNumber.substring(0,2))){
			phoneNumber = phoneNumber.substring(0,3) +"-"+ phoneNumber.substring(3,6) +"-"+ phoneNumber.substring(6);
		}
		
		/* 최종적으로 11자리 or 10자리가 아닐경우 정상적인 핸드폰 번호가 아닐수도 있다는 판단으로 하이픈 처리 안하고 그냥 리턴 합니다. */
		/* 11자리 or 10자리일경우 하이픈 처리하여 리턴 */
		
		return phoneNumber;
	}
	
	public static String ellipsis(String text, int length){
	    String ellipsisString = "...";
	    String outputString = text;
	 
	    if(text.length()>0 && length>0){
	        if(text.length()>length){
	            outputString = text.substring(0, length);
	            outputString += ellipsisString;
	        }
	    }
	    return outputString;
	}
	
	/**
	 * <pre>
	 * 형변환
	 * </pre>
	 *
	 * @param int
	 * @return String
	 */
	public static String parseDoubleString(String amt) 
	{
		if(amt == null)
			return "0";
		
		 DecimalFormat df=new DecimalFormat("#.#########################");
		 return df.format(Double.parseDouble(amt));
	}	
	
	/**
	 * <pre>
	 * 형변환
	 * </pre>
	 *
	 * @param int
	 * @return String
	 */
	public static String parseDoublePattenString(String amtArrTxt) 
	{
		if(amtArrTxt == null)
			return "0";
		
		String result = "";
		
		DecimalFormat df=new DecimalFormat("#.#########################");
		 
		String[] amtArr = amtArrTxt.split(",");
		 
		for(int i=0; i < amtArr.length; i++){
			if(i != 0)
				result = result + ',';
			 
			result = result + df.format(Double.parseDouble(amtArr[i]));
		}
		 
		return result;
				 
	}		
	/**
	 * <pre>
	 * 형변환 e-mail주소 마스킹 처리 앞의 3글자만 
	 * </pre>
	 *
	 * @param String
	 * @return String
	 */
	public static String maskingEmailAddress(String userEmailAddress) 
	{
		String returnString = "";
		if(userEmailAddress == null)		return null;
		
		if("탈퇴한 회원".equals(userEmailAddress)){
			return userEmailAddress;
		}
		if(userEmailAddress.indexOf('@')<0){
			if(userEmailAddress.length() > 3){
				returnString = userEmailAddress.substring(0,3);
				for(int i = 3 ; i < userEmailAddress.length() ; i++){
					returnString += "*";
				}
				return returnString;
			}else if(userEmailAddress.length() > 0){
				/* 아이디가 3글자 이하인 경우 */	
				returnString = userEmailAddress.substring(0,1);
				for(int i = 1 ; i < userEmailAddress.length() ; i++){
					returnString =  returnString + "*";
				}
				return returnString;
			}
		}
		String[] emailArr = userEmailAddress.split("@");
		
		/* 아이디가 3글자 초과인 경우  */
		if(emailArr[0].length() > 3){
			returnString = emailArr[0].substring(0,3);
			for(int i = 3 ; i < emailArr[0].length() ; i++){
				returnString += "*";
			}
			returnString +=  "@"+emailArr[1];
		}else if(emailArr[0].length() > 0){
		/* 아이디가 3글자 이하인 경우 */	
			returnString = emailArr[0].substring(0,1);
			for(int i = 1 ; i < emailArr[0].length() ; i++){
				returnString =  returnString + "*";
			}
			returnString +=  "@"+emailArr[1];
		}
		return returnString;
	}	
	
	/**
	 * <pre>
	 * 형변환 이름 마스킹 처리 (풀네임)
	 * </pre>
	 *
	 * @param String
	 * @return String
	 */
	public static String maskingUserFullName(String userName) 
	{
		if(userName == null)		return null;
		String returnName = "";
		if("탈퇴한 회원".equals(userName) || "휴면회원".equals(userName)){
			return userName;
		}
		
		/* 아이디가 3글자 이상인 경우  */
		if(userName.length() >= 3){
			returnName = userName.substring(0,1);
			for(int i = 1 ; i < userName.length()-1 ; i++){
				returnName += "*";
			}
			returnName += userName.substring((userName.length()-1),userName.length());
		}else if(userName.length() > 0){
			/* 아이디가 2글자 이하인 경우 */	
			returnName = userName.substring(0,1);
			for(int i = 1 ; i < userName.length() ; i++){
				returnName += "*";
			}
		}
		return returnName;
	}	
	
	/**
	 * <pre>
	 * 형변환 이름 마스킹 처리 (First 네임)
	 * </pre>
	 *
	 * @param String
	 * @return String
	 */
	public static String maskingUserFirstName(String userName) 
	{
		if(userName == null || "".equals(userName))		return null;
		if("탈퇴한 회원".equals(userName)  || "휴면회원".equals(userName)){
			return userName;
		}
		String returnName = "";
		/* 이름이 1글자 초과인 경우  */
		if(userName.length() > 1){
			for(int i = 0 ; i < userName.length()-1 ; i++){
				returnName += "*";
			}
			returnName += userName.substring((userName.length()-1),userName.length());
		}else{
			/* 아이디가 외자 경우 */	
			returnName = "*";
		}
		return returnName;
	}	

	/**
	 * <pre>
	 * 형변환 핸드폰번호 마스킹 처리 
	 * </pre>
	 *
	 * @param String
	 * @return String
	 */
	public static String maskingMobilePhoneNumber(String phoneNumber) 
	{
		if(phoneNumber == null || "".equals(phoneNumber) ||  phoneNumber.length() < 1)		return null;
		String returnNumber = "";
		/* 핸드폰 번호가 11자리 이상일 경우 */
		if(phoneNumber.length() >= 11){
			returnNumber = phoneNumber.substring(0,3);
			for(int i = 3 ; i < phoneNumber.length()-4 ; i++){
				returnNumber += "*";
			}
			returnNumber += phoneNumber.substring(phoneNumber.length()-4);
		/* 핸드폰 자리가 10자리인 경우 */
		}else if(phoneNumber.length() == 10){
			returnNumber = phoneNumber.substring(0,3) +"-***-"+ phoneNumber.substring(6);
		}else if(phoneNumber.length() >= 7){
			returnNumber = phoneNumber.substring(0,2);
			for(int i = 2 ; i < phoneNumber.length()-4 ; i++){
				returnNumber += "*";
			}
			returnNumber += phoneNumber.substring(phoneNumber.length()-4);
		}else if(phoneNumber.length() > 4){
			for(int i =  0; i < phoneNumber.length()-4 ; i++){
				returnNumber += "*";
			}
			returnNumber += phoneNumber.substring(phoneNumber.length()-4);
		}else{
			for(int i =  0; i < phoneNumber.length()-1 ; i++){
				returnNumber += "*";
			}
			returnNumber += phoneNumber.substring(phoneNumber.length()-1);
		}
		
		/* 최종적으로 11자리 or 10자리가 아닐경우 정상적인 핸드폰 번호가 아닐수도 있다는 판단으로 하이픈 처리 안하고 그냥 리턴 합니다. */
		/* 11자리 or 10자리일경우 하이픈 처리하여 리턴 */
		
		return returnNumber;
	}	
	
	/**
	 * <pre>
	 * 다음에디터로 빈값으로 입력된 데이타 거르기용
	 * </pre>
	 *
	 * @param String
	 * @return String
	 */
	public static String replaceBlankEditorText(String str){
		if (str == null ||"".equals(str.trim())) {
			return "";
		}
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("&nbsp;", " ");
		str = str.replaceAll("&apos;", "\'");
		str = str.replaceAll("&quot;", "\"");

		str = str.replaceAll("<br>", "");
		str = str.replaceAll("<p>", "");
		str = str.replaceAll("</p>", "");
		
		return str;
	}
	
	/**
	 * <pre>
	 * 프론트 forum 작성자 아이콘 노출용
	 * </pre>
	 *
	 * @param String
	 * @return String
	 */
	public static String forumCreuserIconHtml(String creuser, String replyusers){

		/* 아스키코드로 변환후 나머지를 구합니다 */
		String iconHtml;
		int iconNum;
		/* 어드민이면 하드코딩 */
		if("[aibril]".equals(creuser)){
			
			iconHtml = "<a href=\"#none\" class=\"idTips\">"
					 +		"<div class=\"circle0\"><img src=\"/resources/images/adminico.png\"></div>"
					 +		"<span class=\"none\" id=\"circle0\">관리자</span>"
					 +	"</a>";
		}else{
		
			iconNum = (int)creuser.charAt(0) % 12;
			iconHtml = "<a href=\"#none\" class=\"idTips\">"
							+ 	"<div class=\"circle"+ (iconNum+1) +"\">"+creuser.charAt(0)+"</div>"
							+ 	"<span>"+creuser+"</span>"
							+ "</a>";
		}

		
		if(!StringUtils.isEmpty(replyusers)){
			
			String[] replyuserArr = null;
			replyuserArr = replyusers.split(",");
			
			int rootInt = replyuserArr.length > 2 ?  2 : replyuserArr.length;

			for(int i=0; i < rootInt; i++){
				
				if("[aibril]".equals(replyuserArr[i])){
				
					iconHtml = iconHtml
							 +  "<a href=\"#none\" class=\"idTips\">"
							 +		"<div class=\"circle0\"><img src=\"/resources/images/adminico.png\"></div>"
							 +		"<span class=\"none\" id=\"circle0\">관리자</span>"
							 +	"</a>";
					
				}else{

					iconNum = (int)replyuserArr[i].charAt(0) % 12;
					iconHtml = iconHtml
							 +	"<a href=\"#none\" class=\"idTips\">"
							 + 		"<div class=\"circle"+ (iconNum+1) +"\">"+replyuserArr[i].charAt(0)+"</div>"
							 + 		"<span>"+replyuserArr[i]+"</span>"
							 + 	"</a>";
				}
			}
		}
	
		return iconHtml;
	}	

}
