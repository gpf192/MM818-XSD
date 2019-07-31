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
		System.out.println(decryptAES("Dv4qpFnTebgZRNH3Lk3B+1I48P77lgdrH97JzG2J+WtatGzhAMY2C/wa8thGrGDXHWpQgLecM/wAnPsY/Vm6qdB/+B0EFzM6A/V8+x6TDU3T7YFC695dJsXRRWlBPChDjj2TLEqkTwHN6ptK8s5HmQO0rw0ylbccl2z4QLcwk4X/pf4SIPqElHUdJ+LA/CRHAaiE8TZaWU2n/LuQZYTM3zGcPD47IIUWmE1Bio79JM/JArqjdLulaHD3tif/rcSbb2DUqAAocDxT4jvujquqTrV0q5f8PgY/ysXak6upPOaxXgGOSBx+8nQtoPgw1QqHJD/EGalPJc40cC7X3QQGWsHbbhBOtc6TUxJwwD8oCEjwExV4NLRpQuv4HjXpECZQulmqK27oQmszyfiZ7X+kzLqmkHEJdy3zGmRZn/7gbJs"));
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
