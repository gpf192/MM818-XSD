package com.xsdzq.mm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.HxUserEntity;

public interface HxUserRepository extends JpaRepository<HxUserEntity, Long> {
	 HxUserEntity findByClientId(String paramString);
}
