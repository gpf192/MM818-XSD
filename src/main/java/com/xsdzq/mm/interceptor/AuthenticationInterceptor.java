package com.xsdzq.mm.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.xsdzq.mm.annotation.PassToken;
import com.xsdzq.mm.annotation.UserLoginToken;
import com.xsdzq.mm.model.User;
import com.xsdzq.mm.service.UserService;

public class AuthenticationInterceptor implements HandlerInterceptor {
	
	@Value("${jwt.secret.key}")
	private String key;

	@Autowired
	UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handlerObject)
			throws Exception {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization");
		// 如果不是映射到方法直接通过
		if (!(handlerObject instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handlerObject;
		Method method = handlerMethod.getMethod();
		if (method.isAnnotationPresent(PassToken.class)) {
			PassToken passToken = method.getAnnotation(PassToken.class);
			if (passToken.required()) {
				return true;
			}
		}
		if (method.isAnnotationPresent(UserLoginToken.class)) {
			UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
			if (userLoginToken.required()) {
				// 执行认证
				if (token == null) {
					throw new RuntimeException("no token,please login");
				}
				String clientId;
				try {
					clientId = JWT.decode(token).getAudience().get(0);

				} catch (JWTDecodeException e) {
					// TODO: handle exception
					throw new RuntimeException("401");
				}
				User user = userService.findByClientId(clientId);
				if (user == null) {
					throw new RuntimeException("用户不存在，请重新登陆");
				}
				Algorithm algorithm = Algorithm.HMAC256(key);
				JWTVerifier jwtVerifier =JWT.require(algorithm).build();
				try {
					jwtVerifier.verify(token);
				} catch (JWTVerificationException e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
					throw new RuntimeException("401");
				}
				return true;

			}

		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
