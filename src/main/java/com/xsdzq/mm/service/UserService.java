package com.xsdzq.mm.service;

import com.xsdzq.mm.model.User;

public interface UserService {

	User getUserById(Long id);

	User getUserById(String id);

}
