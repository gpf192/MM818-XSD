package com.xsdzq.mm.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xsdzq.mm.dao.UserRepository;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.User;
import com.xsdzq.mm.service.TokenService;

@Service("tokenServiceImpl")
public class TokenServiceImpl implements TokenService {

	@Value("${jwt.expiretime}")
	private int expiretime;
	@Value("${jwt.secret.key}")
	private String key;

	@Autowired
	UserRepository userRepository;

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

	@Override
	public UserEntity getUserEntity(String token) {
		// TODO Auto-generated method stub
		String clientId = JWT.decode(token).getAudience().get(0);
		UserEntity userEntity = userRepository.findByClientId(clientId);
		return userEntity;
	}

}
