package com.xsdzq.mm.service;

import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.User;

public interface TokenService {

	public static String XSDZQSUBJECT = "xsdzq";

	String getToken(User user);

	UserEntity getUserEntity(String token);

}
