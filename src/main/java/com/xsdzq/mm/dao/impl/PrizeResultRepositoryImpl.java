package com.xsdzq.mm.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.PrizeResultWrapper;
import com.xsdzq.mm.entity.PrizeResultEntity;

@Repository
@Transactional(readOnly = true)
public class PrizeResultRepositoryImpl implements PrizeResultWrapper {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<PrizeResultEntity> getLatestRealPrizeResult() {
		// TODO Auto-generated method stub
		// mysql
		// String sql = "select r.* from `lcj_prize_result` r , `lcj_prize_info` i where
		// r.`prize_id` = i.id and i.`type`=1 order by r.`record_time` desc LIMIT 2";
		// oracle
		String sql = "select * from (select r.* from lcj_prize_result r, lcj_prize_info i where r.prize_id = i.id and r.type = 1 order by r.record_time desc) where rownum <= 5";
		List<PrizeResultEntity> prizeResultEntity = null;
		try {
			Query query = em.createNativeQuery(sql, PrizeResultEntity.class);
			prizeResultEntity = query.getResultList();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return prizeResultEntity;
	}

}
