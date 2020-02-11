package com.xsdzq.mm.service;

import java.util.List;

import com.xsdzq.mm.entity.ChangWaiSellViewEntity;
import com.xsdzq.mm.entity.ProductSellViewEntity;

public interface ProductSellViewService {
	List<ProductSellViewEntity>getByDealTimeAndProductCode(int dealTime,  String code);

	List<ChangWaiSellViewEntity> getCwByDealTimeAndProductCode(int dealTime, String code);
}
