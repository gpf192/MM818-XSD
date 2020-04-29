package com.xsdzq.mm.util;

import java.net.URLEncoder;

import com.xsdzq.mm.model.User;

public class LiveUtil {
public static void main(String[] args) {
	//AESUtil.getUuid();
	try {
		System.out.println(AESUtil.getUuid().substring(0, 20));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 String str = "****";
	 String s = "1234567891123456";
    int begin = s.length()/2-1;
    int end  = s.length()/2+2;
          StringBuilder sb = new StringBuilder(s);
          sb.replace(begin-1, end, str);
          System.out.println(begin);
          System.out.println("========"+sb.toString());
     
	
}
public static String replace(String s) {
	 String str = "****";
	 //String s = "1234567891123456";
    int begin = s.length()/2-1;
    int end  = s.length()/2+2;
          StringBuilder sb = new StringBuilder(s);
          sb.replace(begin-1, end, str);
         return sb.toString();
}
public static String getUrl(User user, String uuid) {
	//String httpUrl = "https://betasaas.yundzh.com/api/auth/sync";//非游客测试
	String httpUrl = "https://saas.yundzh.com/api/auth/sync";//非游客生产
	
	String clientId = replace(user.getClientId());
	String nickname = URLEncoder.encode(clientId);
	String headimgurl = URLEncoder.encode("https://activity.e95399.com/live/yhtx.jpg");//头像位置，固定，需https并进行urlencode utf-8编码
	String key  = "xsy68hu9l^nza39@dzh";
	long timestamp = System.currentTimeMillis();
	//加密的数据源={openid}+{source}+{timestamp}+{nickname}+{headimgurl}+{url}
	String data = uuid+"xsdzq_app"+timestamp+clientId+"https://activity.e95399.com/live/yhtx.jpg";
	String sign = null;
	try {
		sign = Hmacsha256.getHMACSHA256(data, key);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	String liveUrl = httpUrl+"?openid="+uuid+"&nickname="+nickname+"&timestamp="+timestamp+"&channel=xsdzq&source=xsdzq_app&headimgurl="+headimgurl+"&sign="+sign;
	
	return liveUrl;
}
public static String getUrlPro(User user, String uuid) {
	//String httpUrl = "https://betasaas.yundzh.com/api/auth/sync";//非游客测试
	String httpUrl = "https://saas.yundzh.com/api/auth/sync";//非游客生产
	
	String clientId = replace(user.getClientId());
	String nickname = URLEncoder.encode(clientId);
	String headimgurl = URLEncoder.encode("https://activity.e95399.com/live/yhtx.jpg");//头像位置，固定，需https并进行urlencode utf-8编码
	String key  = "xsy68hu9l^nza39@dzh";
	long timestamp = System.currentTimeMillis();
	//加密的数据源={openid}+{source}+{timestamp}+{nickname}+{headimgurl}+{url}
	String data = uuid+"xsdzq_app"+timestamp+clientId+"https://activity.e95399.com/live/yhtx.jpg"+user.getLiveUrl();
	String sign = null;
	try {
		sign = Hmacsha256.getHMACSHA256(data, key);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String urlPro = URLEncoder.encode(user.getLiveUrl());
	String liveUrl = httpUrl+"?openid="+uuid+"&nickname="+nickname+"&timestamp="+timestamp+"&channel=xsdzq&source=xsdzq_app&headimgurl="+headimgurl+"&sign="+sign+"&url="+urlPro;
	
	return liveUrl;
}
}
