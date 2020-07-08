package com.xsdzq.mm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.DzhggEntity;

public interface DzhggRepository extends JpaRepository<DzhggEntity, Long> {
	DzhggEntity findByActivityAndPhone(String activity, String phone);
}
