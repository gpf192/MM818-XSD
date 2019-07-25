package com.xsdzq.mm.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.UserTicketRecordWrapper;
import com.xsdzq.mm.entity.UserTicketRecordEntity;

@Repository
@Transactional(readOnly = true)
public class UserTicketRecordRepositoryImpl implements UserTicketRecordWrapper{
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void add(UserTicketRecordEntity userTicketRecordEntity, int number) {
		// TODO Auto-generated method stub
		em.merge(userTicketRecordEntity);
	}

	@Override
	@Transactional
	public void reduce(UserTicketRecordEntity userTicketRecordEntity, int number) {
		// TODO Auto-generated method stub
		em.merge(userTicketRecordEntity);
	}
}
