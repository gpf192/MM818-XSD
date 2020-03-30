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
	
}
public static String getUrl(User user, String uuid) {
	String httpUrl = "https://betasaas.yundzh.com/api/auth/sync";
	String nickname = URLEncoder.encode(user.getClientName());
	String headimgurl = URLEncoder.encode("https://activity.e95399.com/live/yhtx.jpg");//头像位置，固定，需https并进行urlencode utf-8编码
	String key  = "xsy68hu9l^nza39@dzh";
	long timestamp = System.currentTimeMillis();
	//加密的数据源={openid}+{source}+{timestamp}+{nickname}+{headimgurl}+{url}
	String data = uuid+"xsdzq_app"+timestamp+user.getClientName()+"https://activity.e95399.com/live/yhtx.jpg";
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
}
