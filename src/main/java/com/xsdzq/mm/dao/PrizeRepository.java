package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.PrizeEntity;

public interface PrizeRepository extends JpaRepository<PrizeEntity, Long>, PrizeWrapper {
	public List<PrizeEntity> findByIndex(int index);

}
