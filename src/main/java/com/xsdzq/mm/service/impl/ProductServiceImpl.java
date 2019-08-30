package com.xsdzq.mm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.ProductRepository;
import com.xsdzq.mm.entity.ProductEntity;
import com.xsdzq.mm.service.ProductService;

@Service(value = "productServiceImpl")
@Transactional(readOnly = true)

public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<ProductEntity> getProductList(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		System.out.println("pageNumber: " + pageNumber + " ; " + "pageSize: " + pageSize);
		logger.info("pageNumber: " + pageNumber + " ; " + "pageSize: " + pageSize);
		Page<ProductEntity> productPage = productRepository.findBy(pageRequest);
		return productPage.getContent();
	}

	@Override
	public List<ProductEntity> getAllProductList() {
		// TODO Auto-generated method stub
		List<ProductEntity> allList = productRepository.findAll();
		List<ProductEntity> list = new ArrayList<ProductEntity>();
		for (ProductEntity productEntity : allList) {
			Date nowDate = new Date();
			Date endDate = productEntity.getEndDate();
			if (endDate != null && !StringUtils.isBlank(endDate.toString())) {
				if (nowDate.after(endDate)) {

				} else {
					list.add(productEntity);
				}

			} else {
				list.add(productEntity);
			}
		}
		return list;
	}
}
