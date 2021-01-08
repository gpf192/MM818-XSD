package com.xsdzq.mm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.PrizeRecordRepository;
import com.xsdzq.mm.entity.PrizeRecordEntity;
import com.xsdzq.mm.entity.UserEntity;

@Repository
@Transactional(readOnly = true)
public class PrizeRecordRepositoryImpl implements PrizeRecordRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void add(PrizeRecordEntity prizeRecordEntity) {
		// TODO Auto-generated method stub
		em.persist(prizeRecordEntity);
	}

	@Override
	public void reduce() {
		// TODO Auto-generated method stub

	}

	@Override
	public PrizeRecordEntity getLatestRealPrizeResult() {
		// TODO Auto-generated method stub

		// TypedQuery<PrizeRecordEntity> sqlQuery =em.createQuery("SELECT r FROM
		// PrizeResultEntity r WHERE r.userEntity=",PrizeRecordEntity.class);
		return null;
	}

	/*
	 * @Override public List<PrizeRecordEntity> getSignAdviserRecord(UserEntity
	 * userEntity) { // TODO Auto-generated method stub
	 * TypedQuery<PrizeRecordEntity> sqlQuery = em.createQuery(
	 * "SELECT r FROM PrizeRecordEntity r WHERE r.userEntity=?1 and r.type=?2 and r.votesSource=?3"
	 * , PrizeRecordEntity.class); sqlQuery.setParameter(1, userEntity);
	 * sqlQuery.setParameter(2, 1); sqlQuery.setParameter(2, "6"); return
	 * sqlQuery.getResultList(); }
	 * 
	 * @Override public List<PrizeRecordEntity> getNewFundAccountRecord(UserEntity
	 * userEntity) { // TODO Auto-generated method stub
	 * TypedQuery<PrizeRecordEntity> sqlQuery = em.createQuery(
	 * "SELECT r FROM PrizeRecordEntity r WHERE r.userEntity=?1 and r.type=?2 and r.votesSource=?3"
	 * , PrizeRecordEntity.class); sqlQuery.setParameter(1, userEntity);
	 * sqlQuery.setParameter(2, 1); sqlQuery.setParameter(2, "5"); return
	 * sqlQuery.getResultList(); }
	 */

	@Override
	public List<PrizeRecordEntity> getListByUserAndFlag(UserEntity userEntity, String flag) {
		// TODO Auto-generated method stub
		TypedQuery<PrizeRecordEntity> sqlQuery = em.createQuery(
				"SELECT r FROM PrizeRecordEntity r WHERE r.userEntity=?1 and r.dateFlag=?2", PrizeRecordEntity.class);
		sqlQuery.setParameter(1, userEntity);
		sqlQuery.setParameter(2, flag);
		return sqlQuery.getResultList();
	}

	@Override
	public List<PrizeRecordEntity> getByUser(UserEntity userEntity) {
		// TODO Auto-generated method stub
		TypedQuery<PrizeRecordEntity> sqlQuery = em.createQuery(
				"SELECT r FROM PrizeRecordEntity r WHERE r.userEntity=?1 order by r.recordTime desc",
				PrizeRecordEntity.class);
		sqlQuery.setParameter(1, userEntity);
		return sqlQuery.getResultList();
	}

}
