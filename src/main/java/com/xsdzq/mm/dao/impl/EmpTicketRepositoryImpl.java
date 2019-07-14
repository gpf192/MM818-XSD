package com.xsdzq.mm.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.EmpTicketWrapper;
import com.xsdzq.mm.entity.EmpTicketEntity;

@Repository
@Transactional(readOnly = true)
public class EmpTicketRepositoryImpl implements EmpTicketWrapper {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void add(EmpTicketEntity empTicketEntity, int number) {
		// TODO Auto-generated method stub
		empTicketEntity.setNumber(empTicketEntity.getNumber() + number);
		em.merge(empTicketEntity);
	}

	@Override
	@Transactional
	public void reduce(EmpTicketEntity empTicketEntity, int number) {
		// TODO Auto-generated method stub
		empTicketEntity.setNumber(empTicketEntity.getNumber() - number);
		em.merge(empTicketEntity);
	}

}
