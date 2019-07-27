package com.xsdzq.mm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsdzq.mm.entity.ProductEntity;
import com.xsdzq.mm.service.ProductService;
import com.xsdzq.mm.util.GsonUtil;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@GetMapping(value = "/list", produces = "application/json; charset=utf-8")
	public Map<String, Object> getEmpTicketList(@RequestParam int pageNumber, @RequestParam int pageSize) {

		List<ProductEntity> productEntities = productService.getProductList(pageNumber, pageSize);
		return GsonUtil.buildMap(0, "ok", productEntities);
	}

	@GetMapping(value = "/all", produces = "application/json; charset=utf-8")
	public Map<String, Object> getAllEmpTicketList() {

		List<ProductEntity> productEntities = productService.getAllProductList();
		return GsonUtil.buildMap(0, "ok", productEntities);
	}

}
