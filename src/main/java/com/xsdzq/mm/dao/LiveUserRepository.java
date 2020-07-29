package com.xsdzq.mm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.LiveUserEntity;

public interface LiveUserRepository extends JpaRepository<LiveUserEntity, Long> {
	LiveUserEntity findByClientId(String paramString);
}
