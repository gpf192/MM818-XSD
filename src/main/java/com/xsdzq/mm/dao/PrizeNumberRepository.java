package com.xsdzq.mm.dao;

import com.xsdzq.mm.entity.PrizeNumberEntity;
import com.xsdzq.mm.entity.UserEntity;

public interface PrizeNumberRepository {

	void create(PrizeNumberEntity prizeNumberEntity);

	PrizeNumberEntity findByUserEntity(UserEntity userEntity);

	void addNumber(UserEntity userEntity);

	void reduceNumber(UserEntity userEntity);

}
