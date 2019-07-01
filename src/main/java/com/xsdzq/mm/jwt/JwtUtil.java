package com.xsdzq.mm.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xsdzq.mm.model.User;

public class JwtUtil {

	public String getToken(User user) {
		String token = "";
		token =JWT.create().withAudience(user.getId()).sign(Algorithm.HMAC256(user.getPassword()));
		return token;
	}

}
