package com.xsdzq.mm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.ChangWaiSellViewRepository;
import com.xsdzq.mm.dao.ProductSellViewRepository;
import com.xsdzq.mm.entity.ChangWaiSellViewEntity;
import com.xsdzq.mm.entity.ProductSellViewEntity;
import com.xsdzq.mm.service.ProductSellViewService;

@Service(value = "productSellViewServiceImpl")
@Transactional(readOnly = true)
public class ProductSellViewServiceImpl implements ProductSellViewService{
	@Autowired
	private ProductSellViewRepository productSellViewRepository;

	@Autowired
	private ChangWaiSellViewRepository changWaiSellViewRepository;
	
	@Override
	public List<ProductSellViewEntity> getByDealTimeAndProductCode(int dealTime,  String code) {
		// TODO Auto-generated method stub
		return productSellViewRepository.findByDealTimeAndProductCode(dealTime, code);
	}
	
	
	@Override
	public List<ChangWaiSellViewEntity> getCwByDealTimeAndProductCode(int dealTime,  String code) {
		// TODO Auto-generated method stub
		return changWaiSellViewRepository.findByDealTimeAndProductCode(dealTime, code);
	}

}
