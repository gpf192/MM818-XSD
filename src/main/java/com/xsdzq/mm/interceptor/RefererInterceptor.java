package com.xsdzq.mm.interceptor;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xsdzq.mm.properties.RefererProperties;

public class RefererInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory.getLogger(RefererInterceptor.class);

	@Autowired
	private RefererProperties refererProperties;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		String referer = request.getHeader("referer");
		String host = request.getServerName();
		String configUrl = refererProperties.getUrl();
		log.info("referer: " + referer);
		log.info("host: " + host);
		if (referer == null) {
			// 状态置为404
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

		URL requestUrl = null;
		try {
			requestUrl = new URL(referer);
			log.info("request host: " + requestUrl.getHost());
			if (configUrl == null) {
				return true;
			} else {
				String[] urlArray = configUrl.split(",");
				for (String urlString : urlArray) {
					if (urlString.equals(requestUrl.getHost())) {
						return true;
					}
				}
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

	}

}
