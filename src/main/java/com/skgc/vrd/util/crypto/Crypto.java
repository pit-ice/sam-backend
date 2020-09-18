package com.skgc.vrd.util.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

    private  final static String IV   = "AIBRIL1234567890".substring(0,16); //16bit
    private static final String AES_CBC_PKCS5  = "AES/CBC/PKCS5Padding";

    /**
     * 
     * 개요: SHA256 암호화
     */
    public static String getSHA256(String str){
    	return getSHA(str, "SHA-256");
    }
    
    /**
     * 
     * 개요: SHA512 암호화
     */
    public static String getSHA512(String str){
    	return getSHA(str, "SHA-512");
    }
    
    private static String getSHA(String str, String shaType){
    	
    	String sha = ""; 
    	try{
    		MessageDigest sh = MessageDigest.getInstance(shaType); 
    		sh.update(str.getBytes()); 
    		byte byteData[] = sh.digest();
    		StringBuffer sb = new StringBuffer(); 
    		for(int i = 0 ; i < byteData.length ; i++){
    			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
    		}
    		sha = sb.toString();
    		
    	}catch(NoSuchAlgorithmException e){
    		e.printStackTrace(); 
    		sha = null; 
    	}
    	return sha;
    }
    
    /**
     * 
     * <pre>
     * AES 대칭키 함수를 이용한 암호화
     * MYSQL과 맞추기위해 padding값과 메소드 안에 내용이 조금 변경되었습니다.
     * SELECT HEX(AES_ENCRYPT('안녕하세요', 'AIBRIL1234567890'))
     * </pre>
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static String getEncrypt(String value) 
    {
	  String encrypted = null;
      try
      {
    	  SecretKeySpec skeySpec = new SecretKeySpec(IV.getBytes(), "AES");
          
          Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5);
          cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));

          encrypted = byteArrayToHex(cipher.doFinal(value.getBytes("utf-8")));
          //return encrypted;
          
          return encrypted.toUpperCase();
        }
        catch(Exception e){
        	throw new RuntimeException(e);
        }
    }
    
    /**
     * 
     * <pre>
     * AES 대칭키 함수를 이용한 복호화
     * MYSQL과 맞추기위해 padding값과 메소드 안에 내용이 조금 변경되었습니다.
	 * SELECT AES_DECRYPT(UNHEX('B73AAACB58B4144333DCC4675556A222'), 'AIBRIL1234567890')
     * </pre>
     *
     * @param value
     * @return
     */
	public static String getDecrypt(String value) 
	{
	  String decrypted = null;
      
	  try
      {
    	  SecretKeySpec skeySpec = new SecretKeySpec(IV.getBytes(), "AES");
          
          Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5);
          cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
          decrypted = new String(cipher.doFinal(hexToByteArray(value)), "utf-8");
          return decrypted;
        }
        catch(Exception e){
        	throw new RuntimeException(e);
        }
    }
    
    /**
     * 
     * <pre>
     * hex 를 byte 로
     * </pre>
     *
     * @param s
     * @return
     */
    private static byte[] hexToByteArray(String s) {
        byte[] retValue = null;
        if (s != null && s.length() != 0) {
            retValue = new byte[s.length() / 2];
            for (int i = 0; i < retValue.length; i++) {
                retValue[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
            }
        }
        return retValue;
    }
    /**
     *  
     * <pre>
     * byte 를 hex 로
     * </pre>
     *
     * @param buf
     * @return
     */
    private static String byteArrayToHex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        for (int i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
         
        return strbuf.toString();
    }
	
}
