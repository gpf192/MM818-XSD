package com.xsdzq.mm.util;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AES {
	private  Cipher encryptCipher = null;

	   private  Cipher decryptCipher = null;

	    public AES() {
		super();
		// TODO Auto-generated constructor stub
	}

		/**
	     * aes加密初始化类
	     * @param password 加密密码
	     * @throws Exception
	     */
	    public AES(String password) throws Exception {
	        byte[] raw = password.getBytes("UTF-8");
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	        encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        IvParameterSpec iv = new IvParameterSpec(raw);//
	        encryptCipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	        decryptCipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	    }

	    /**
	     * aes加密
	     * @param content
	     * @return
	     * @throws Exception
	     */
	    public String encryptAES(String content) throws Exception {
	        byte[] encrypted = encryptCipher.doFinal(content.getBytes("UTF-8"));
	        return Base64.encodeBase64String(encrypted);
	    }

	    /**
	     * aes解密
	     * @param content
	     * @return
	     * @throws Exception
	     */
	    public String decryptAES(String content) throws Exception {
	        byte[] encrypted1 = Base64.decodeBase64(content);
	        byte[] original = decryptCipher.doFinal(encrypted1);
	        String originalString = new String(original, "UTF-8");
	        return originalString;
	    }

}
