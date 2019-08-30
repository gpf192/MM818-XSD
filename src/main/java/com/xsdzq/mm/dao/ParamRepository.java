package com.xsdzq.mm.dao;

import org.springframework.stereotype.Repository;

import com.xsdzq.mm.entity.ParamEntity;

@Repository
public interface ParamRepository {
	ParamEntity getValueByCode(String code);
	void modifyParam(ParamEntity entity);
}