package com.xsdzq.mm.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.PrizeNumberRepository;
import com.xsdzq.mm.entity.PrizeNumberEntity;
import com.xsdzq.mm.entity.UserEntity;

@Repository
@Transactional(readOnly = true)
public class PrizeNumberRepositoryImpl implements PrizeNumberRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void create(PrizeNumberEntity prizeNumberEntity) {
		// TODO Auto-generated method stub
		em.persist(prizeNumberEntity);
	}

	@Override
	public PrizeNumberEntity findByUserEntity(UserEntity userEntity) {
		// TODO Auto-generated method stub
		TypedQuery<PrizeNumberEntity> sqlQuery = em
				.createQuery("SELECT n FROM PrizeNumberEntity n WHERE n.userEntity=?1", PrizeNumberEntity.class);
		sqlQuery.setParameter(1, userEntity);
		return sqlQuery.getSingleResult();
	}

	@Override
	@Transactional
	public void addNumber(UserEntity userEntity) {
		// TODO Auto-generated method stub
		PrizeNumberEntity prizeNumberEntity = findByUserEntity(userEntity);
		prizeNumberEntity.setNumber(prizeNumberEntity.getNumber() + 1);
		em.merge(prizeNumberEntity);

	}

	@Override
	@Transactional
	public void reduceNumber(UserEntity userEntity) {
		// TODO Auto-generated method stub
		PrizeNumberEntity prizeNumberEntity = findByUserEntity(userEntity);
		prizeNumberEntity.setNumber(prizeNumberEntity.getNumber() - 1);
		em.merge(prizeNumberEntity);

	}

}
