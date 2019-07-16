package com.xsdzq.mm.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.PrizeWrapper;
import com.xsdzq.mm.entity.PrizeEntity;

@Transactional(readOnly = true)
public class PrizeRepositoryImpl implements PrizeWrapper {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void reducePrizeNumber(PrizeEntity prizeEntity) {
		// TODO Auto-generated method stub
		PrizeEntity nowPrizeEntity = em.find(PrizeEntity.class, prizeEntity.getId());
		nowPrizeEntity.setAmount(nowPrizeEntity.getAmount() - 1);
		em.merge(nowPrizeEntity);

	}

	@Override
	public void addPrizeWinningNumber(PrizeEntity prizeEntity) {
		// TODO Auto-generated method stub
		prizeEntity.setWinningNumber(prizeEntity.getWinningNumber() + 1);
		em.merge(prizeEntity);

	}

}
