package com.runtai.newdexintong.comment.utils.des;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DESUtil {

//	public static String encryptKey = "appceshi";
	public static String encryptKey = "rtds2013";
	/**
	 * DES加密
	 * @param encryptString	需要加密的字符串
	 * @return
	 * @throws Exception
	 */
    public static String encryptDES(String encryptString) throws Exception {  
    	IvParameterSpec zeroIv = new IvParameterSpec(encryptKey.getBytes("UTF-8"));   
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");  
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");  
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);  
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());  
        return Base64.encode(encryptedData);
    } 
    
    /**
     * 解密
     * @param decryptString	需要解密的字符串
     * @return
     * @throws Exception
     */
    public static String decryptDES(String decryptString) throws Exception {  
		byte[] byteMi = Base64.decode(decryptString);
		IvParameterSpec zeroIv = new IvParameterSpec(encryptKey.getBytes("UTF-8"));  
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");  
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");  
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);  
        byte decryptedData[] = cipher.doFinal(byteMi);  
        return new String(decryptedData);  
    } 
    
    /**
     * 解密
     * @param decryptString	需要解密的字符串
     * @return
     * @throws Exception
     */
    public static String decryptDES(String encryptKey, String decryptString) throws Exception {  
    	byte[] byteMi = Base64.decode(decryptString);
    	IvParameterSpec zeroIv = new IvParameterSpec(encryptKey.getBytes("UTF-8"));  
    	SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");  
    	Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");  
    	cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);  
    	byte decryptedData[] = cipher.doFinal(byteMi);  
    	return new String(decryptedData);  
    } 
}
