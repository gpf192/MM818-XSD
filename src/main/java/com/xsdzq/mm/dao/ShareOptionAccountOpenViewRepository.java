package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.ShareOptionAccountOpenViewEntity;

public interface ShareOptionAccountOpenViewRepository extends JpaRepository<ShareOptionAccountOpenViewEntity, Long> {
	List<ShareOptionAccountOpenViewEntity>findByDateFlagLessThanEqual(int dataFlag);
}
