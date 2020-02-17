package com.xsdzq.mm.service;

import java.util.List;

import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.entity.PrizeNumberEntity;
import com.xsdzq.mm.entity.PrizeResultEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.PrizeRecordAndResult;
import com.xsdzq.mm.model.ZodiacNumber;

public interface PrizeService {

	List<PrizeEntity> getPrizeAll();

	List<PrizeResultEntity> getLatestPrize();

	List<ZodiacNumber> getMyZodiacNumbers(UserEntity userEntity);

	PrizeEntity getMyPrize(UserEntity userEntity);

	List<PrizeRecordAndResult> getMyPrizeRecord(UserEntity userEntity);

	List<PrizeResultEntity> getMyPrizeEntities(UserEntity userEntity);

	PrizeNumberEntity getPrizeNumberEntity(UserEntity userEntity);

	int getAvailableNumber(UserEntity userEntity);

	int getShareEveryDayNumber(UserEntity userEntity);

	boolean sharePutPrizeNumber(UserEntity userEntity);

	boolean hasStockPrize(UserEntity userEntity);

	void selectStockPrize(UserEntity userEntity);

	void addPrizeNumber(UserEntity userEntity, Boolean type, String reason, int number);

}
