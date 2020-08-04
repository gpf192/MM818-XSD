package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.SignInvestViewEntity;

public interface SignInvestViewRepository  extends JpaRepository<SignInvestViewEntity, Long> {
	List<SignInvestViewEntity>findByserviceTypeAndStatusAndEffectiveDateLessThanEqual(int serviceType, String status, String effectiveDate);
}
