package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.CreditAccountOpenViewEntity;


public interface CreditAccountOpenViewRepository extends JpaRepository<CreditAccountOpenViewEntity, Long> {
	List<CreditAccountOpenViewEntity>findByDateFlag(String dataFlag);

}
