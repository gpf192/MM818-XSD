package com.xsdzq.mm.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESUtil {

	public static String AES_KEY = "xsdzqlicaijie888"; // 16位
	public static String AES_IV = "iq9nApwQfg9OFXVp"; // 16位
	public static String AES_NO_PADDING = "AES/CBC/NOPadding"; // js 和java同时采用无模式

	public static void main(String args[]) throws Exception {
		System.out.println(encryptAES("123456"));
		System.out.println(decryptAES(encryptAES("123456")));
		System.out.println(decryptAES("rVdbrlCDyPBk+CdAHogmbOvn8dPt857R00yoDH9UdVAiYSikRGl1BVKB251OsD9gcmyWOS8u2ae+BbGVKd4pWrEAxyhdVnezDHjXfaFcY1jZ9gCyX97b9caKOe8zdx/mbI86WnYYQZSVQjTFOV7VeCWFsoWXwF+3I7tD3esTMGc4PGnUxZWW4oS0MHSrAjFfLKyYh65WB1uQxJRaZo3xh2BGxFuYxNR0FjPUVLhsnKbfxeP8eI7/Fuf1OFU7l9Z6"));
	}

	public static String encryptAES(String data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance(AES_NO_PADDING); // 参数分别代表 算法名称/加密模式/数据填充方式
			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = data.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keyspec = new SecretKeySpec(AES_KEY.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(AES_IV.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);
			return Base64.encodeBase64String(encrypted);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decryptAES(String data) throws Exception {
		try {
			byte[] encrypted = Base64.decodeBase64(data);// new BASE64Decoder().decodeBuffer(data);
			Cipher cipher = Cipher.getInstance(AES_NO_PADDING);
			SecretKeySpec keyspec = new SecretKeySpec(AES_KEY.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(AES_IV.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted);
			String originalString = new String(original);
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
