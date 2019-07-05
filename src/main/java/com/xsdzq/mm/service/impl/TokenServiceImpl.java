package com.xsdzq.mm.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xsdzq.mm.model.User;
import com.xsdzq.mm.service.TokenService;

@Service("tokenServiceImpl")
public class TokenServiceImpl implements TokenService {

	@Value("${jwt.expiretime}")
	private int expiretime;
	@Value("${jwt.secret.key}")
	private String key;

	@Override
	public String getToken(User user) {
		// TODO Auto-generated method stub
		Algorithm algorithm = Algorithm.HMAC256(key);
		long nowMillis = System.currentTimeMillis();
		long exprieMillis = nowMillis + expiretime;
		Date now = new Date(nowMillis);
		Date exprieDate = new Date(exprieMillis);

		String token = "";
		token = JWT.create().withSubject(XSDZQSUBJECT).withAudience(user.getClientId()).withIssuedAt(now)
				.withExpiresAt(exprieDate).sign(algorithm);
		return token;
	}

}
