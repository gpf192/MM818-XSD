package com.xsdzq.mm.dao;

import java.util.List;

import com.xsdzq.mm.entity.PrizeRecordEntity;
import com.xsdzq.mm.entity.UserEntity;

public interface PrizeRecordRepository {

	public void add(PrizeRecordEntity prizeRecordEntity);

	public PrizeRecordEntity getLatestRealPrizeResult();

	public List<PrizeRecordEntity> getListByUserAndFlag(UserEntity userEntity, String flag);

	public void reduce();

}
