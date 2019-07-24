package com.xsdzq.mm.service;

import com.xsdzq.mm.entity.UserEmpRelationEntity;

public interface UserEmpRelationService {
	UserEmpRelationEntity findByClientId(String clientId);
}
