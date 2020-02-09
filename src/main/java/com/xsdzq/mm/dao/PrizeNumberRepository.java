package com.xsdzq.mm.dao;

import com.xsdzq.mm.entity.PrizeNumberEntity;
import com.xsdzq.mm.entity.UserEntity;

public interface PrizeNumberRepository {

	void create(PrizeNumberEntity prizeNumberEntity);

	PrizeNumberEntity findByUserEntity(UserEntity userEntity);

	void addNumber(UserEntity userEntity);
	
	void addNumber(PrizeNumberEntity prizeNumberEntity);
	void addNumber(PrizeNumberEntity prizeNumberEntity, int number);//开门红通过基金购买、开通期权两融账户添加相应的抽奖次数

	void reduceNumber(UserEntity userEntity);

}
