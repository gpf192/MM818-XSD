package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.DzhActivityEntity;

public interface DzhActivityRepository extends JpaRepository<DzhActivityEntity, Long> {
	List<DzhActivityEntity> findByOrderByName();
	DzhActivityEntity findByName(String name);
}
