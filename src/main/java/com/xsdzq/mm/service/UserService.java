package com.xsdzq.mm.service;

import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.User;

public interface UserService {

	void login(User user);

	User getUserById(Long id);
	
	
	UserEntity getUserByClientId(String clientId);

	void setUserInfo(UserEntity userEntity);

	User getUserById(String id);

	User findByClientId(String clientId);
	
	void addEveryLoginPrizeNumber(UserEntity userEntity);

}
