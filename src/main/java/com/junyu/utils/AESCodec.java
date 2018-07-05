package com.junyu.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *  AES 加解密
 *  15位加密成24位，后16位加密成20位,后16位加密成20位,后16位加密成26位
 */
public class AESCodec {
	
	private static String key = "ewtdV^*JjmI((Ikl";
	
	/**
	 * 加密
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String aesEncrypt(String str){
		if(str == null || str.equals("")){
			return "";
		}
		return aesEncrypt(str, key);
	}

	/**
	 * 加密
	 * @param str 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String aesEncrypt(String str, String key) {
		if (str == null || key == null)
			return null;
		try{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
			byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
			return new BASE64Encoder().encode(bytes);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 解密
	 * @param str
	 * @return
	 */
	public static String aesDecrypt(String str){
		if(StringUtils.isBlank(str)){
			return str;
		}
		return aesDecrypt(str, key);
	}

	/**
	 * 解密
	 * @param str
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String aesDecrypt(String str, String key){
		if (str == null || key == null)
			return null;
		try{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(key.getBytes("utf-8"), "AES"));
			byte[] bytes = new BASE64Decoder().decodeBuffer(str);
			bytes = cipher.doFinal(bytes);
			return new String(bytes, "utf-8");
		}catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String args[]) {
		System.out.println(AESCodec.aesEncrypt("111111"));
		System.out.println(AESCodec.aesDecrypt("mPxbBC3SWx4Y2dLrK/k3fg=="));
	}
}
