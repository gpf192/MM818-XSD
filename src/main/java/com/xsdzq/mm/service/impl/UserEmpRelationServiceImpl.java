package com.xsdzq.mm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.UserEmpRelationRepository;
import com.xsdzq.mm.entity.UserEmpRelationEntity;
import com.xsdzq.mm.service.UserEmpRelationService;

@Service(value = "userEmpRelationServiceImpl")
@Transactional(readOnly = true)
public class UserEmpRelationServiceImpl implements UserEmpRelationService{
	@Autowired
	private UserEmpRelationRepository userEmpRelationRepository;

	@Override
	public UserEmpRelationEntity findByClientId(String clientId) {
		// TODO Auto-generated method stub
		return userEmpRelationRepository.findByClientId(clientId);
	}
}
