package com.xsdzq.mm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsdzq.mm.entity.AwardEntity;
import com.xsdzq.mm.service.AwardService;
import com.xsdzq.mm.service.TokenService;
import com.xsdzq.mm.util.GsonUtil;

@RestController
@RequestMapping("/activity/award")
public class AwardController {
	
	@Autowired
	@Qualifier("awardServiceImpl")
	private AwardService awardService;

	@Autowired
	TokenService tokenService;

	@GetMapping(value = "/all", produces = "application/json; charset=utf-8")
	public Map<String, Object> getAllPrize() {
		List<AwardEntity> awardEntities = awardService.getConvertAward();
		return GsonUtil.buildMap(0, "ok", awardEntities);
	}
	
	@PostMapping(value = "/convert", produces = "application/json; charset=utf-8")
	public Map<String, Object> convertAward() {
		List<AwardEntity> awardEntities = awardService.getConvertAward();
		return GsonUtil.buildMap(0, "ok", awardEntities);
	}

}
