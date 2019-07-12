package com.xsdzq.mm.service;

import java.util.List;

import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.entity.UserEntity;

public interface PrizeService {

	List<PrizeEntity> getPrizeAll();
	
	PrizeEntity getLatestPrize();

	PrizeEntity getMyPrize(UserEntity userEntity);

	List<PrizeEntity> getMyPrizeEntities(UserEntity userEntity);

	int getAvailableNumber(UserEntity userEntity);

	boolean sharePutPrizeNumber(UserEntity userEntity);

	void addPrizeNumber(UserEntity userEntity, Boolean type, String reason, int number);

}
