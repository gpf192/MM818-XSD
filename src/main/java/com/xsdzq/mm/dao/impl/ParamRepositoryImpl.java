package com.xsdzq.mm.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.ParamRepository;
import com.xsdzq.mm.entity.ParamEntity;

@Repository
@Transactional(readOnly = true)
public class ParamRepositoryImpl implements ParamRepository {

	public static String LCJCOMPUSER = "lcj_comp_user";

	@PersistenceContext
	private EntityManager em;

	@Override
	public ParamEntity getValueByCode(String code) {
		// TODO Auto-generated method stub
		TypedQuery<ParamEntity> sqlQuery = em.createQuery("SELECT c FROM ParamEntity c WHERE c.code=?0",
				ParamEntity.class);
		sqlQuery.setParameter(0, code);
		return sqlQuery.getSingleResult();
	}

}
