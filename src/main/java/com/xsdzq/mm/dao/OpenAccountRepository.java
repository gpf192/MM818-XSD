package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.OpenAccountEntity;

public interface OpenAccountRepository  extends JpaRepository<OpenAccountEntity, Long> {
	List<OpenAccountEntity> findByOpenDate(int preDay);
	int countByOpenDateLessThanEqualAndOpenDateGreaterThanEqualAndClientId(int endDate, int beginDate, String clientId);

}
