package com.xsdzq.mm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.UserEmpRelationEntity;

public interface UserEmpRelationRepository extends JpaRepository<UserEmpRelationEntity, Long> {
	UserEmpRelationEntity findByClientId(String clientId);
}
