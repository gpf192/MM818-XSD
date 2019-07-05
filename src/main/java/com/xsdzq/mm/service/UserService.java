package com.xsdzq.mm.service;

import com.xsdzq.mm.model.User;

public interface UserService {

	User getUserById(Long id);

	void setUser(User user);

	User getUserById(String id);
	
	User findByClientId(String clientId);

}
