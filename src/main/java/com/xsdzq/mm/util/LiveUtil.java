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
    int begin = s.length()/2-1;
    int end  = s.length()/2+2;
          StringBuilder sb = new StringBuilder(s);
          sb.replace(begin-1, end, str);
         return sb.toString();
}
public static String getUrl(User user, String uuid) {
	
	String liveUrl = "";
	String key  = "xsy68hu9l^nza39@dzh";
	long timestamp = System.currentTimeMillis();

	if("".equals(user.getClientId())) {
		//游客
		String httpUrl = "https://guestsaas.yundzh.com/#/xsdzq/home";//游客生产
		//， 游客只加密 timestamp、channel、source
		
		//加密的数据源={timestamp}+{channel}+{source}
		String data = timestamp+"xsdzq"+"xsdzq_app";
		String sign = null;
		try {
			sign = Hmacsha256.getHMACSHA256(data, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 liveUrl = httpUrl+"&timestamp="+timestamp+"&channel=xsdzq&source=xsdzq_app&sign="+sign;
		
	}else {
		//非游客
		String httpUrl = "https://saas.yundzh.com/api/auth/sync";//非游客生产
		String clientId = replace(user.getClientId());
		String nickname = URLEncoder.encode(clientId);
		String headimgurl = URLEncoder.encode("https://activity.e95399.com/live/yhtx.jpg");//头像位置，固定，需https并进行urlencode utf-8编码
		//加密的数据源={openid}+{source}+{timestamp}+{nickname}+{headimgurl}+{url}
		String data = uuid+"xsdzq_app"+timestamp+clientId+"https://activity.e95399.com/live/yhtx.jpg";
		String sign = null;
		try {
			sign = Hmacsha256.getHMACSHA256(data, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 liveUrl = httpUrl+"?openid="+uuid+"&nickname="+nickname+"&timestamp="+timestamp+"&channel=xsdzq&source=xsdzq_app&headimgurl="+headimgurl+"&sign="+sign;
		
	}	
	
	return liveUrl;
}
public static String getUrlPro(User user, String uuid) {
	String liveUrl = "";
	String key  = "xsy68hu9l^nza39@dzh";
	long timestamp = System.currentTimeMillis();

	if("".equals(user.getClientId())) {
		//游客
		String httpUrl = "https://guestsaas.yundzh.com/#/xsdzq/home";//游客生产
		
		//， 游客只加密 timestamp、channel、source
		
		//加密的数据源={timestamp}+{channel}+{source}+{url}
		String data = timestamp+"xsdzq"+"xsdzq_app"+user.getLiveUrl();
		String sign = null;
		try {
			sign = Hmacsha256.getHMACSHA256(data, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String urlPro = URLEncoder.encode(user.getLiveUrl());
		 liveUrl = httpUrl+"&timestamp="+timestamp+"&channel=xsdzq&source=xsdzq_app&sign="+sign+"&url="+urlPro;
		
	}else {
		//非游客
		String httpUrl = "https://saas.yundzh.com/api/auth/sync";//非游客生产
		String clientId = replace(user.getClientId());
		String nickname = URLEncoder.encode(clientId);//昵称
		String headimgurl = URLEncoder.encode("https://activity.e95399.com/live/yhtx.jpg");//头像位置，固定，需https并进行urlencode utf-8编码
		
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
		 liveUrl = httpUrl+"?openid="+uuid+"&nickname="+nickname+"&timestamp="+timestamp+"&channel=xsdzq&source=xsdzq_app&headimgurl="+headimgurl+"&sign="+sign+"&url="+urlPro;
		
	}
	
	return liveUrl;
}
}
