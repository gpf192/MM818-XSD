package com.xsdzq.mm.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.LiveRecordRepository;
import com.xsdzq.mm.entity.LiveRecordEntity;

@Repository
@Transactional(readOnly = true)
public class LiveRecordRepositoryImpl implements LiveRecordRepository{
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void add(LiveRecordEntity liveRecordEntity) {
		// TODO Auto-generated method stub
		em.persist(liveRecordEntity);
	}
}
