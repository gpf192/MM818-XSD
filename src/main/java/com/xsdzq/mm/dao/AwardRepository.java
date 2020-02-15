package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.AwardEntity;

public interface AwardRepository extends JpaRepository<AwardEntity, Long> {

	public List<AwardEntity> findByIndex(int index);

}
