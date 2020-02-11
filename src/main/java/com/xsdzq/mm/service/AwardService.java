package com.xsdzq.mm.service;

import java.util.List;

import com.xsdzq.mm.entity.AwardEntity;
import com.xsdzq.mm.entity.AwardResultEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.AwardNumber;

public interface AwardService {

	List<AwardEntity> getConvertAward();

	List<AwardResultEntity> getAwardResultRecord(UserEntity userEntity);

	boolean convertAward(UserEntity userEntity, AwardNumber awardNumber);

}
