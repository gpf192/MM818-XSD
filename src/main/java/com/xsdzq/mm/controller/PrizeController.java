package com.xsdzq.mm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsdzq.mm.annotation.UserLoginToken;
import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.entity.PrizeResultEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.HasNumber;
import com.xsdzq.mm.model.Number;
import com.xsdzq.mm.model.ZodiacNumber;
import com.xsdzq.mm.service.PrizeService;
import com.xsdzq.mm.service.TokenService;
import com.xsdzq.mm.util.GsonUtil;
import com.xsdzq.mm.util.PrizeUtil;

@RestController
@RequestMapping("/activity/prize")
public class PrizeController {

	@Autowired
	@Qualifier("prizeServiceImpl")
	PrizeService prizeService;

	@Autowired
	TokenService tokenService;

	@GetMapping(value = "/all", produces = "application/json; charset=utf-8")
	public Map<String, Object> getAllPrize() {
		List<PrizeEntity> prizeEntities = prizeService.getPrizeAll();
		return GsonUtil.buildMap(0, "ok", prizeEntities);
	}

	@GetMapping(value = "/latest", produces = "application/json; charset=utf-8")
	public Map<String, Object> getLatestPrize() {
		PrizeResultEntity prizeResultEntity = PrizeUtil.getInstance()
				.getSecretPrizeResultEntity(prizeService.getLatestPrize());
		return GsonUtil.buildMap(0, "ok", prizeResultEntity);
	}

	@GetMapping(value = "/getPrize", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> getPrize(@RequestHeader("Authorization") String token) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		PrizeEntity prize = prizeService.getMyPrize(userEntity);
		if (prize == null) {
			return GsonUtil.buildMap(1, "没有抽奖机会了", null);
		}
		return GsonUtil.buildMap(0, "ok", prize);
	}

	@GetMapping(value = "/number", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> getAvailableNumber(@RequestHeader("Authorization") String token) {
		System.out.println(token);
		UserEntity userEntity = tokenService.getUserEntity(token);
		int number = prizeService.getAvailableNumber(userEntity);
		Number prizeNumber = new Number(number);
		return GsonUtil.buildMap(0, "ok", prizeNumber);
	}

	@GetMapping(value = "/myZodiac", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> getMyZodiac(@RequestHeader("Authorization") String token) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		List<ZodiacNumber> list = prizeService.getMyZodiacNumbers(userEntity);
		return GsonUtil.buildMap(0, "ok", list);
	}

	@PostMapping(value = "/share", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> sharePutPrizeNumber(@RequestHeader("Authorization") String token) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		boolean isRule = prizeService.sharePutPrizeNumber(userEntity);
		if (isRule) {
			return GsonUtil.buildMap(0, "ok", null);
		} else {
			return GsonUtil.buildMap(1, "分享活动获得的票数，已经达到上限!", null);
		}
	}

	@PostMapping(value = "/selectStockPrize", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> selectStockPrize(@RequestHeader("Authorization") String token) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		prizeService.selectStockPrize(userEntity);
		return GsonUtil.buildMap(0, "ok", null);
	}

	@GetMapping(value = "/hasStockPrize", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> hasStockPrize(@RequestHeader("Authorization") String token) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		boolean hasPrize = prizeService.hasStockPrize(userEntity);
		HasNumber hasNumber = new HasNumber();
		hasNumber.setHasNumber(hasPrize);
		return GsonUtil.buildMap(0, "ok", hasNumber);
	}

	@GetMapping(value = "/shareNumber", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> getShareEveryDayNumber(@RequestHeader("Authorization") String token) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		int shareNumber = prizeService.getShareEveryDayNumber(userEntity);
		Number mNumber = new Number();
		mNumber.setNumber(shareNumber);
		return GsonUtil.buildMap(0, "ok", mNumber);

	}

	@GetMapping(value = "/prizes", produces = "application/json; charset=utf-8")
	public Map<String, Object> getOwnPrizes(@RequestHeader("Authorization") String token) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		List<PrizeResultEntity> myRealPrizeResultEntity = prizeService.getMyPrizeEntities(userEntity);
		return GsonUtil.buildMap(0, "ok", myRealPrizeResultEntity);
	}

}
