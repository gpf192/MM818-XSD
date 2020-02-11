package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.ChangWaiSellViewEntity;

public interface ChangWaiSellViewRepository extends JpaRepository<ChangWaiSellViewEntity, Long> {
	List<ChangWaiSellViewEntity>findByDealTimeAndProductCode(int dealTime, String code);

}
