package com.xsdzq.mm.service;

import java.util.List;

import com.xsdzq.mm.entity.ProductSellViewEntity;

public interface ProductSellViewService {
	List<ProductSellViewEntity>getByDealTime(int dealTime);
}
