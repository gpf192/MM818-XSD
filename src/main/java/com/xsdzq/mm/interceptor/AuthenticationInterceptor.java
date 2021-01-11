package com.xsdzq.mm.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.xsdzq.mm.exception.BusinessException;
import com.xsdzq.mm.model.User;
import com.xsdzq.mm.service.UserService;

public class AuthenticationInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);

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
				// 1 null 判断
				if (token == null) {
					throw new RuntimeException("no token,please login");
				}
				// 2.1 token 校验 解析Adudience
				String clientId;
				try {
					clientId = JWT.decode(token).getAudience().get(0);
				} catch (JWTDecodeException e) {
					// TODO: handle exception
					throw new RuntimeException("401");
				}
				// 2.2 token 校验 解析 verify
				Algorithm algorithm = Algorithm.HMAC512(key);
				JWTVerifier jwtVerifier = JWT.require(algorithm).build();
				try {
					jwtVerifier.verify(token);
				} catch (JWTVerificationException e) {
					// TODO: handle exception
					log.info(clientId + ": token校验异常 " + e.getMessage());
					throw new BusinessException("401");
				} catch (Exception e) {
					// TODO: handle exception
					throw new BusinessException("401");
				}
				// 3 查询用户验证真实性

				User user = userService.findByClientId(clientId);
				if (user == null) {
					throw new BusinessException("用户不存在，请重新登陆");
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
