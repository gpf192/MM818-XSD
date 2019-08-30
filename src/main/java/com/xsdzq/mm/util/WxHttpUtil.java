package com.xsdzq.mm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WxHttpUtil {
 public static WxReturnParam  post(String appid) throws Exception {
	 String postUrl = "http://10.150.52.58:7001/api/commonfunc";//生产  http://10.150.52.58:7001/api/commonfunc
	 String key = "uiopq12345nmkoq3"; //128bit 生产待定  uiopq12345nmkoq3
     AES aes = new AES(key);
     JSONObject pa =new JSONObject();
     pa.put("functionCode","1032" );
     pa.put("originalid", appid);  
     
     String content = JSON.toJSONString(pa);
     String params = aes.encryptAES(content);//参数加密
     
	 byte[] requestBytes = params.getBytes("utf-8"); // 将参数转为二进制流
     HttpClient httpClient = new HttpClient();// 客户端实例化
     PostMethod postMethod = new PostMethod(postUrl);
     //设置请求头Authorization
     //postMethod.setRequestHeader("Authorization", "Basic " + authorization);
     // 设置请求头  Content-Type
     postMethod.setRequestHeader("Content-Type", "application/json");
     InputStream inputStream = new ByteArrayInputStream(requestBytes, 0,
             requestBytes.length);
     RequestEntity requestEntity = new InputStreamRequestEntity(inputStream,
             requestBytes.length, "application/json; charset=utf-8"); // 请求体
     postMethod.setRequestEntity(requestEntity);
     httpClient.executeMethod(postMethod);// 执行请求
     InputStream soapResponseStream = postMethod.getResponseBodyAsStream();// 获取返回的流
     byte[] datas = null;
     try {
    	
         datas =  readInputStream(soapResponseStream);// 从输入流中读取数据
     } catch (Exception e) {
         e.printStackTrace();
     }
     String result = new String(datas, "UTF-8");// 将二进制流转为String
     // 打印返回结果
     System.out.println("___________________________________________");
      System.out.println("返回结果******************************************"+result);
      String decryptResult = aes.decryptAES(result);
      System.out.println("返回结果解密后******************************************"+decryptResult);
      
      JSONObject objResult = JSONObject.parseObject(decryptResult);
      String errorCode = objResult.getString("errorCode");
      String errorMsg = objResult.getString("errorMsg");
      WxReturnParam wp = new WxReturnParam();
      wp.setErrorCode(errorCode);
      wp.setErrorMsg(errorMsg);
      if("success".equals(errorCode)) {
    	  String ticketJson = objResult.getString("result");
    	  JSONObject objTicket = JSONObject.parseObject(ticketJson);
          String jsapiticket = objTicket.getString("jsapiticket");
          wp.setTicket(jsapiticket);
      }
      return wp;
 }
	private static byte[] readInputStream(InputStream inputStream)throws IOException {
		// TODO Auto-generated method stub
		try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int num = inputStream.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = inputStream.read(buffer);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

	}
}
