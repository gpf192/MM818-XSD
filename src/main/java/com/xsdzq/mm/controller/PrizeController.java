package com.xsdzq.mm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.service.PrizeService;
import com.xsdzq.mm.util.GsonUtil;

@RestController
@RequestMapping("/prize")
public class PrizeController {

	@Autowired
	@Qualifier("prizeServiceImpl")
	PrizeService prizeService;

	@GetMapping(value = "/all", produces = "application/json; charset=utf-8")
	public Map<String, Object> getAllPrize() {
		List<PrizeEntity> prizeEntities = prizeService.getPrizeAll();
		return GsonUtil.buildMap(0, "ok", prizeEntities);
	}

	@GetMapping(value = "/getPrize", produces = "application/json; charset=utf-8")
	public Map<String, Object> getPrize() {
		PrizeEntity prize = prizeService.getMyPrize();
		return GsonUtil.buildMap(0, "ok", prize);
	}

}
