package com.xsdzq.mm.service;

import java.util.List;

import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.entity.PrizeNumberEntity;
import com.xsdzq.mm.entity.PrizeResultEntity;
import com.xsdzq.mm.entity.UserEntity;

public interface PrizeService {

	List<PrizeEntity> getPrizeAll();

	PrizeResultEntity getLatestPrize();

	PrizeEntity getMyPrize(UserEntity userEntity);

	List<PrizeResultEntity> getMyPrizeEntities(UserEntity userEntity);

	PrizeNumberEntity getPrizeNumberEntity(UserEntity userEntity);

	int getAvailableNumber(UserEntity userEntity);

	int getShareEveryDayNumber(UserEntity userEntity);

	int sharePutPrizeNumber(UserEntity userEntity);
	
	boolean hasStockPrize(UserEntity userEntity);

	void selectStockPrize(UserEntity userEntity);

	void addPrizeNumber(UserEntity userEntity, Boolean type, String reason, int number);

}
