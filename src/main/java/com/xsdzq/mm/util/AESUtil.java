package com.xsdzq.mm.util;

import java.net.URLEncoder;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESUtil {

	// 测试环境
	public static String AES_KEY = "xsdzqlicaijie888xsdzqlicaijie888"; // 32位
	public static String AES_IV = "iq9nApwQfg9OFXVp"; // 16位
	
	// 生产环境
	// public static String AES_KEY = "2021KmhQXsdZq6662021KmhQXsdZq666"; // 32位
	// public static String AES_IV = "rtyZXIv2021Stock"; // 16位

	public static String AES_NO_PADDING = "AES/CBC/NOPadding"; // js 和java同时采用无模式

	public static String ALGORITHM = "AES/CBC/PKCS5Padding";

	public static void main(String args[]) throws Exception {

		String keyWord = "love";
		String data = "4rymjloT8ix+drsw6X4S6A==";

		//System.out.println(encryptAES256(keyWord));
		System.out.println(decryptAES256(data));

		// System.out.println(decryptAES("Dv4qpFnTebgZRNH3Lk3B+1I48P77lgdrH97JzG2J+WtatGzhAMY2C/wa8thGrGDXHWpQgLecM/wAnPsY/Vm6qdB/+B0EFzM6A/V8+x6TDU3T7YFC695dJsXRRWlBPChDjj2TLEqkTwHN6ptK8s5HmQO0rw0ylbccl2z4QLcwk4X/pf4SIPqElHUdJ+LA/CRHAaiE8TZaWU2n/LuQZYTM3zGcPD47IIUWmE1Bio79JM/JArqjdLulaHD3tif/rcSbb2DUqAAocDxT4jvujquqTrV0q5f8PgY/ysXak6upPOaxXgGOSBx+8nQtoPgw1QqHJD/EGalPJc40cC7X3QQGWsHbbhBOtc6TUxJwwD8oCEjwExV4NLRpQuv4HjXpECZQulmqK27oQmszyfiZ7X+kzLqmkHEJdy3zGmRZn/7gbJs"));
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
			return URLEncoder.encode(Base64.encodeBase64String(encrypted));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decryptCommonAES(String data) throws Exception {
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

	public static String decryptAES(String data) throws Exception {
		String AES_KEY_LIVE = "xsdzqlicaijie888"; // 16位
		String AES_IV_LIVE = "iq9nApwQfg9OFXVp"; // 16位
		try {
			byte[] encrypted = Base64.decodeBase64(data);// new BASE64Decoder().decodeBuffer(data);
			Cipher cipher = Cipher.getInstance(AES_NO_PADDING);
			SecretKeySpec keyspec = new SecretKeySpec(AES_KEY_LIVE.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(AES_IV_LIVE.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			byte[] original = cipher.doFinal(encrypted);
			String originalString = new String(original);
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String encryptAES256(String data) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			int blockSize = cipher.getBlockSize();
			String keyString = "qwertyuiop12345xsdzq8888866666qq";
			String ivString = "qwertyuiop12345x";
			byte[] dataBytes = data.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keySpec = new SecretKeySpec(keyString.getBytes(), "AES");

			cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ivString.getBytes()));
			byte[] encData = cipher.doFinal(plaintext);
			System.out.println(encData);
			
			return URLEncoder.encode(Base64.encodeBase64String(encData));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;

	}

	public static String decryptAES256(String data) {
		try {
			byte[] encrypted = Base64.decodeBase64(data);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(AES_IV.getBytes());

			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivspec);
			byte[] original = cipher.doFinal(encrypted);
			String originalString = new String(original);
			
			return originalString;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}

	public static String getUuid() throws Exception {
		try {
			return UUID.randomUUID().toString().replace("-", "").toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String escapeExprSpecialWord(String keyword) {
		if (keyword != "") {
			String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
			for (String key : fbsArr) {
				if (keyword.contains(key)) {
					keyword = keyword.replace(key, "\\" + key);
				}
			}
		}
		return keyword;
	}
}
