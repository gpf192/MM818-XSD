package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.ProductSellViewEntity;

public interface ProductSellViewRepository extends JpaRepository<ProductSellViewEntity, Long> {
	List<ProductSellViewEntity>findByDealTime(int dealTime);
}
