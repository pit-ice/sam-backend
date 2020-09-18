package com.skgc.vrd.constants;

public final class AppConstants {

	public static final String EMPTY = ""; 
	public static final int NUMBER_ZERO = 0;
	public static final int NUMBER_TWO = 2;
	public static final int NUMBER_FIVE = 5;
	public static final int NUMBER_SIX = 6;
	public static final int NUMBER_FOUR = 4;
	public static final String QUESTION_MARK = "?";
	public static final String EQUALS_SIGN = "=";
	public static final String CHARACTER_UTF8 = "UTF-8";
	public static final String YN_Y = "Y";
	public static final String YN_N	= "N";
	public static final String DML_INSERT = "I";
	public static final String DML_UPDATE = "U";
	public static final String DML_DELETE = "D";
	public static final String NULL = null;

	public static final int NUMBER_MINUS_ONE = -1;
	public static final int NUMBER_MINUS_TWO = -2;
	public static final int NUMBER_ONE = 1;
	public static final int NUMBER_THREE = 3;
	public static final int NUMBER_TEN = 10;
	public static final int NUMBER_NINE = 9;
	public static final int NUMBER_EIGHT = 9;
	public static final int NUMBER_TWELVE = 12;
	public static final int NUMBER_FIFTEEN = 15;
	public static final int NUMBER_EIGHTEEN = 18;
	public static final String CONTENT_TYPE_TEXT_HTML_UTF_8 = "text/html;charset=UTF-8";
	public static final String COLON = ":";
	public static final String SLASH = "/";
	public static final String OKAY = "ok";
	public static final String UNDERLINE = "_";

	public static final String DBSTS_O = "O";
	public static final String DBSTS_D = "D";
	public static final String DBSTS_H = "H";
	
	/**
	 * <pre>
	 * Url 상수
	 * </pre>
	 */
	public class Url {
		public static final String HTTP_FRONT_PROPERTIES_KEY_NM = "front.server.url";
		public static final String RETURN_URL_PARAM_NM = "returnUrl";							/* 로그인 후 리턴 URL PARAM 명 */
		public static final String ADMIN_LOGIN_URI = "/web/user/login/getLogin.do";				/* 어드민 로그인 화면 URI */
		public static final String FRONT_LOGIN_URI = "/web/user/login/getLogin.do";				/* 프론트 로그인 화면 URI */
		public static final String FRONT_LOGIN_GATE_URI = "/web/user/login/getLoginGate.do";	/* 로그인 게이트(return url을 처리) */
		public static final String HTTP	= "http";												/* http */
		public static final String HTTPS = "https";												/* https */
		public static final String CORP_ACCOUNT_EMAIL_AUTH_URI = "/web/user/userJoin/modifyCorpEmailAuth.do";	/* 회사 계정 이메일 인증 URI */
		public static final String CORP_USER_EMAIL_AUTH_URI = "/web/user/userJoin/modifyCorpUserEmailAuth.do";	/* 회사 회원 이메일 인증 URI */
		public static final String NORM_USER_EMAIL_AUTH_URI = "/web/user/userJoin/modifyNormUserEmailAuth.do";			/* 개인 회원 이메일 인증 URI */ 
		public static final String PART_USER_EMAIL_AUTH_URI = "/web/user/userJoin/modifyPartnerEmailAuth.do";			/* 파트너 이메일 인증 URI */ 
		public static final String PART_INVATE_EMAIL_AUTH_URI = "/web/user/userJoin/modifyPartnersInviteEmailAuth.do";			/* 관계사 이메일 인증 URI */ 
		public static final String CORP_T2_EMAIL_AUTH_URI = "/web/user/userJoin/modifyT2EmailAuth.do";	/* 회사 계정 이메일 인증 URI */
	}
	

	
	/**
	 * 
	  * <pre>
	  * 클래스 설명
	  * </pre>
	 */
	public class Path {
		public static final String WEB_INF_NM = "WEB-INF";
		public static final String EMAIL_TEMPLATE_NM = "emailTemplate";
	}	
	

	/**
	 * 이메일
	 *
	 */
	public class Email{
		public static final String EMAIL_AUTH_KEY_NM = "emailAuthKey";						/* 이메일 인증 키 명 */
		public static final String EMAIL_AUTH_SEQ_NM = "emailAuthSeq";						/* 이메일 인증 번호 명 */
	}
	
	/**
	 * 벨로시티 파일 명
	 *
	 */
	public class Valocity{
		public static final String USER_EMAIL_AUTH_VM = "userEmailAuth.vm";												/* 유저 이메일 인증 VM 파일명 */
		public static final String CORPORATION_ACCOUNT_JOIN_REQUEST_VM = "corporationAccountJoinRequest.vm";			/* 기업 ACCOUNT 가입 승인 요청 VM 파일명 */
		public static final String CORPORATION_USER_JOIN_REQUEST_VM = "corporationUserJoinRequest.vm";					/* 기업 회원 가입 승인 요청 VM 파일명 */
		
		public static final String CORPORATION_ACCOUNT_STATUS_COMPLETE_VM = "corporationAccountStatusComplete.vm";		/* 기업 ACCOUNT 가입 승인 완료 VM 파일명 */
		public static final String CORPORATION_ACCOUNT_STATUS_REJECT_VM = "corporationAccountStatusReject.vm";			/* 기업 ACCOUNT 반려 요청 VM 파일명 */

		
		public static final String CORPORATION_USER_STATUS_COMPLETE_VM = "corporationUserStatusComplete.vm";			/* 기업 회원 가입 승인 완료 VM 파일명 */
		public static final String CORPORATION_USER_STATUS_REJECT_VM = "corporationUserStatusReject.vm";				/* 기업 회원 반려 요청 VM 파일명 */
		
		public static final String NORM_USER_STATUS_COMPLETE_VM = "normUserStatusComplete.vm";							/* 개인 회원 가입 승인 완료 VM 파일명 */
		
		public static final String USER_FIND_ID_VM = "userFindId.vm";													/* 유저 아이디 찾기 VM 파일명 */
		public static final String USER_FIND_PWD_VM = "userFindPwd.vm";													/* 유저 패스워드 찾기 VM 파일명 */	
		
		public static final String ADMIN_JOIN_CONFIRM_EMAIL = "adminJoinConfirmEmail.vm";								/* 어드민 가입 확인 VM 파일명 */
		
		public static final String ADMIN_INQUIRY_EMAIL = "adminInquiryEmail.vm";										/* 문의하기 답변 VM 파일명 */
		public static final String USER_INQUIRY_CONFIRME_MAIL = "userInquiryConfirmEmail.vm";							/* 유저 문의  확인  VM 파일명 */
		
		
		/* 회원 탈퇴 승인 */
		public static final String MEMBER_APPROVE_WITHDRAWAL_MAIL = "memberApproveWithdrawal.vm"; /* 회원 탈퇴 승인 */
		public static final String MEMBER_FORCED_WITHDRAWAL_MAIL = "memberForcedWithdrawal.vm"; /* 회원 강제 탈퇴 */
		public static final String MEMBER_APPLY_WITHDRAWAL_MAIL = "memberWithdrawalApplyEmail.vm"; /* 회원 신청 안내 */
		public static final String MEMBER_APPLY_WITHDRAWAL_MANAGER_MAIL = "memberWithdrawalApplyManagerEmail.vm"; /* 회원 탈퇴 신청 담당자 안내 */
		
	}
	
	
	/**
	 * <pre>
	 * Url 상수
	 * </pre>
	 */
	public class BackEnd {
		public static final String RESULT_OK = "200";								
		public static final String CREATED = "201";							
		public static final String ACCEPTED = "202";						
		public static final String NOCONTENT = "204";		
		public static final String ALREADYCREATED = "205";	
		public static final String NOUPDATE = "304";							
		public static final String BADREQUEST = "400";					
		public static final String UNAUTHORIZED = "401";						
		public static final String FORBIDDEN = "403";						
		public static final String NOTFOUND = "404";					
		public static final String INTERNALSERVERERROR = "500";			
		public static final String NOTIMPLEMENTED = "501";					
		public static final String BADGATEWAY = "502";				
		public static final String SERVICEUNAVAILABLE = "503";				
		public static final String GATEWAYTIMEOUT = "504";
		
		// backend custom header
		public static final String X_AIBRIL_CLIENT_ID = "X-Aibril-Client-Id";
		public static final String X_AIBRIL_CLIENT_SECRET = "X-Aibril-Client-Secret";
		public static final String X_AIBRIL_REFRESH_TOKEN = "X-Aibril-Refresh-Token";
		public static final String X_AIBRIL_RESPONSE_CODE = "X-Aibril-Response-Code";
		public static final String X_AIBRIL_TRANSACTION_ID = "X-Aibril-Transaction-Id";
		public static final String X_AIBRIL_OPERATION_TIME = "X-Aibril-Operation-Time";
		public static final String X_AIBRIL_RESPONSE_TIME = "X-Aibril-Response-Time";
		
		
		// client id, client secret
		public static final String X_AIBRIL_CLIENT_ID_VALUE = "aibril";
		public static final String X_AIBRIL_CLIENT_SECRET_VALUE = "f9e311ab4df2a691d7f2440b0ae6da606b4434b936622cc4966197d18c52f4c4";
		
	}
	
	/**
	 * <pre>
	 * 문의
	 * </pre>
	 */
	public class Inquiry {
		public static final String TYPE_NORMAL = "NR";						/* 일반 문의 */
		public static final String TYPE_TECHNOLOGY = "TC";					/* 기술 문의 */			
		public static final String TYPE_BUSINESS = "BZ";					/* 파트너 문의 */
		public static final String TYPE_BILL = "BL";						/* 요금 문의 */
		public static final String TYPE_SOLUTION = "SO";					/* 솔루션 문의 */
		public static final String TYPE_SOLUTION_INTRODUCTION = "SD";		/* 솔루션 도입 문의 */
		
	}	
    

	/**
	 * <pre>
	 * 공지에서 사용하는 코드
	 * </pre>
	 */
    public class Notice {
    	public static final String TYPE_GUIDE = "N";				/* 안내 */
    	public static final String TYPE_CHECK = "C";				/* 점검 */
    	public static final String TYPE_EMERGENCY = "E";			/* 긴급 */
    	
    }
   
}
