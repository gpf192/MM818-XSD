package com.xsdzq.mm.service;

import java.util.List;

import com.xsdzq.mm.entity.PrizeEntity;

public interface PrizeService {

	List<PrizeEntity> getPrizeAll();

	PrizeEntity getMyPrize();

}
