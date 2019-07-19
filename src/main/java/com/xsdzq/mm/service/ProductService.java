package com.xsdzq.mm.service;

import java.util.List;

import com.xsdzq.mm.entity.ProductEntity;

public interface ProductService {

	List<ProductEntity> getProductList(int pageNumber, int pageSize);

}
