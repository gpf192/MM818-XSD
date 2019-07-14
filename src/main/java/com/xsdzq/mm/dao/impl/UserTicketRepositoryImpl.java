package com.xsdzq.mm.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.UserTicketWrapper;
import com.xsdzq.mm.entity.UserTicketEntity;

@Repository
@Transactional(readOnly = true)
public class UserTicketRepositoryImpl implements UserTicketWrapper {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void add(UserTicketEntity userTicketEntity, int number) {
		// TODO Auto-generated method stub
		userTicketEntity.setNumber(userTicketEntity.getNumber() + number);
		em.merge(userTicketEntity);
	}

	@Override
	@Transactional
	public void reduce(UserTicketEntity userTicketEntity, int number) {
		// TODO Auto-generated method stub
		userTicketEntity.setNumber(userTicketEntity.getNumber() - number);
		em.merge(userTicketEntity);
	}

}
