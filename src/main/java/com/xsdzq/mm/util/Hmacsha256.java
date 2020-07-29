package com.xsdzq.mm.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Hmacsha256 {
	public static String getHMACSHA256(String data, String key) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

	    SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");

	    sha256_HMAC.init(secret_key);

	    byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));

	    StringBuilder sb = new StringBuilder();

	    for (byte item : array) {

	        sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));

	    }

	    return sb.toString();
	}
	public static void main(String[] args) {
		String s = null;
		try {
			s = getHMACSHA256("test123456xsdzq_app1582704385617阿信https://saas-sj.oss-cn-shenzhen.aliyuncs.com/xsdzq.jpg","xsy68hu9l^nza39@dzh");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s);
	}
	
}
