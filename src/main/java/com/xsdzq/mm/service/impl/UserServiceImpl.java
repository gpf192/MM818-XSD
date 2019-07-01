package com.xsdzq.mm.service.impl;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xsdzq.mm.dao.UserRepository;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.User;
import com.xsdzq.mm.service.UserService;

@Service(value = "userServiceImpl")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		Optional<UserEntity> userEntity = userRepository.findById(id);

		return null;
	}

	@Override
	public User getUserById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
