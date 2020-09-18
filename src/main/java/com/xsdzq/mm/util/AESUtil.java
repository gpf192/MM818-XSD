package com.xsdzq.mm.util;

import java.net.URLEncoder;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESUtil {

	public static String AES_KEY = "xsdzqlicaijie888"; // 16位
	public static String AES_IV = "iq9nApwQfg9OFXVp"; // 16位
	public static String AES_NO_PADDING = "AES/CBC/NOPadding"; // js 和java同时采用无模式

	public static void main(String args[]) throws Exception {
		String uid = getUuid();
		String string = "0KwA7dVpy1vAhUSjNfQSEVrJBNltx/HGsWkP/9sjsrfpiTC0dToq8fCsnnMzeTfPeB/KIWkH4kFqaUV4rP7p2qke6VXcijnVirx3M2r8wzyIwVGxK5HRtDazmuseo1JOMipFUIW9WcQyeU9CbVm4g52LsAca3eOZld2jeALVVdpzoFo1sgvR2Jq5P4IldlIt+NXvbL21e2SSKXebfkn6GODVJH1N4dF1nzayM/6buNx65V/jWu1KObBPq2XRoDkJBWJJJYGvDH8TMqXRJV1dNl3m/HcPFpAMvx/+a4Shab2lXsbhym8PpWSuo4GKfuIsITb/d6penTtzgB7CDlL83T1wMTjTYWETNTbLMCRzno8ZLnskDz4smwvaLfuFPHkKDfnu7pSo7ETbCvVTf8Jb3Udlh8393Zn+8E4mVL2iVnoWXcejqM9WuCXgcKa100XTAxEzse6djoVXHEuxQaKu1U5M2nLS9dptybYIwoTbsVFz3BRGLkdr4G9xCi6AYgScFf9qOvc+Ywi4rX/Hj9FwMfNYEuOLIzkuCpgxHoxdnCLarK0E7Ie6ppfpv1cYPD8cgYU8t3TIrOOe6SuF/LIC/o6JLPLeWoJ19jWbbRHatWk=";
		String str = "JZ7vj9X2jv0p2ZBW4iXW0XFcgM8s/5xKhuPvxZEZnIwpKfNLF8uurF2TY38P5AfJxaeUIg2gveZg3thXDKNOrN22h0OHGNbY8eyQ0p7kxBbus5LOPdBwiMd8aCh0RUnPhJzuFWNo7xaXJ5ZBMAOjkpD0CinY2mAAkCw4bQpwwQAXIfK83JkefFw6ITEhidnEIdSyYKd3W1QfiEDwU2wmMdsV5vMEWklltwnT8w3Nqm5/mrWUzBHU3TorTWE36JhpTwPrWmcoyKM+X7K4xYCa0w/Hzcw46s36PxlO5YSuO1wF0siA5BKUfWVRyynq1NWi/h74rjM2kMmG6Lkn6z2T21Vc7xbPi8bCIcxHd4a8lTgJSv3bgFm1gsXvc2j7+lXGMVPrDVjlB3QVVmq/XSS87jDhQmZLtJAXzoZADPl3junzVRZivMREKeHsoe3On0937D3p4XOflwglo96zWOQvcc6mbeF8Sg7qebGBuNR7KZpgTzJVZbYyBNIFwiVwK7sREvdLdKg4sM8vTFFAI+BBZlB7NZo/c7nw9/+MtZqZ5xkE/cbLst+ROaJqUxNuJN1J";
		String str2 = "0KwA7dVpy1vAhUSjNfQSEVrJBNltx/HGsWkP/9sjsrfpiTC0dToq8fCsnnMzeTfPeB/KIWkH4kFqaUV4rP7p2qke6VXcijnVirx3M2r8wzyIwVGxK5HRtDazmuseo1JOMipFUIW9WcQyeU9CbVm4g52LsAca3eOZld2jeALVVdpzoFo1sgvR2Jq5P4IldlIt+NXvbL21e2SSKXebfkn6GODVJH1N4dF1nzayM/6buNx65V/jWu1KObBPq2XRoDkJBWJJJYGvDH8TMqXRJV1dNl3m/HcPFpAMvx/+a4Shab0EHFsm7Zu+fVwgOuMjToiZWM70WYw79J4jTnq9kDt0MX9v0CuZ9RyfLrEUIuhMp/l+uU4kapTX1kUzzV0wUiNO6xUlbfzfv/sUMxlfYO6JPjPABQuE2VBvfe7xk3LwNgEIIaZeU0ifMYh3lD8Hx1eVSHD3PTx4ReJxozc7pHJrQFJ/SWaRzTNo0QGadBYkn0H/Oll1GGuGQi1M1BBlII3rWHLn6OlpPHqhdKsJwWa+SLmDpo4FhkFkZyOxDz5LShkpuZzdEB7/XNl/t5nvkEmNdgzQC2YWgdxWiDHMo76wO96XDwXkVr2ozO4jIHjOf1s=";
		System.out.println(uid);
		System.out.println(encryptAES(uid)+"-");
		System.out.println(decryptAES(str2));
		//System.out.println(decryptAES("Dv4qpFnTebgZRNH3Lk3B+1I48P77lgdrH97JzG2J+WtatGzhAMY2C/wa8thGrGDXHWpQgLecM/wAnPsY/Vm6qdB/+B0EFzM6A/V8+x6TDU3T7YFC695dJsXRRWlBPChDjj2TLEqkTwHN6ptK8s5HmQO0rw0ylbccl2z4QLcwk4X/pf4SIPqElHUdJ+LA/CRHAaiE8TZaWU2n/LuQZYTM3zGcPD47IIUWmE1Bio79JM/JArqjdLulaHD3tif/rcSbb2DUqAAocDxT4jvujquqTrV0q5f8PgY/ysXak6upPOaxXgGOSBx+8nQtoPgw1QqHJD/EGalPJc40cC7X3QQGWsHbbhBOtc6TUxJwwD8oCEjwExV4NLRpQuv4HjXpECZQulmqK27oQmszyfiZ7X+kzLqmkHEJdy3zGmRZn/7gbJs"));
		String charString ="MA;IIP=112.64.119.251;IPORT=63078;LIP=192.168.43.1;MAC=20DA224188E8;IMEI=f96a3039bbf33bd8;RMPN=13800000000;UMPN=18621749310;ICCID=89860115831000299322;OSV=ANDROID10;IMSI=f96a3039bbf33bd8@xsdzqapp;6.0.0.2";
		System.out.println("charString: " + charString.length());
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
	
	public static String getUuid()
		    throws Exception
		  {
		    try
		    {
		      return UUID.randomUUID().toString().replace("-", "").toLowerCase();
		    }
		    catch (Exception e)
		    {
		      e.printStackTrace();
		    }
		    return null;
		  }

	  public static String escapeExprSpecialWord(String keyword)
	  {
	    if (keyword != "")
	    {
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
