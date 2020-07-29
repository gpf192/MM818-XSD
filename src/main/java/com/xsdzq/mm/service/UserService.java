package com.xsdzq.mm.service;

import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.ActivityNumber;
import com.xsdzq.mm.model.User;

public interface UserService {

	ActivityNumber login(User user);
	String loginHx(User paramUser);
	User getUserById(Long id);

	boolean hasSignAdviser(UserEntity userEntity);

	boolean hasNewFundAccount(UserEntity userEntity);

	UserEntity getUserByClientId(String clientId);

	void setUserInfo(UserEntity userEntity);

	User getUserById(String id);

	User findByClientId(String clientId);

	String loginLive(User paramUser);
}
