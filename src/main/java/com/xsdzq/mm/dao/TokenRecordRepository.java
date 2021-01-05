package com.xsdzq.mm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.TokenRecordEntity;

public interface TokenRecordRepository extends JpaRepository<TokenRecordEntity, Long> {

}
