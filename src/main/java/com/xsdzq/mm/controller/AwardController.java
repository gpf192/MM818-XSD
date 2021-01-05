package com.xsdzq.mm.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsdzq.mm.annotation.UserLoginToken;
import com.xsdzq.mm.entity.AwardEntity;
import com.xsdzq.mm.entity.AwardResultEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.AwardNumber;
import com.xsdzq.mm.model.KmhFlag;
import com.xsdzq.mm.service.AwardService;
import com.xsdzq.mm.service.TokenService;
import com.xsdzq.mm.util.DateUtil;
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

	@GetMapping(value = "/record", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> getConvertRecord(@RequestHeader("Authorization") String token) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		List<AwardResultEntity> awardResultEntities = awardService.getAwardResultRecord(userEntity);
		return GsonUtil.buildMap(0, "ok", awardResultEntities);
	}

	@PostMapping(value = "/convert", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> convertAward(@RequestHeader("Authorization") String token,
			@RequestBody AwardNumber awardNumber) throws ParseException {
		// 判断活动是否结束
		// KmhFlag k = new KmhFlag();
		// String endFlag = tokenService.getValueByCode("kmhEndFlag").getValue();
		// if (!DateUtil.checkDate(endFlag)) {
		// return GsonUtil.buildMap(1, "活动已结束，无法进行此项操作。", null);
		// }

		UserEntity userEntity = tokenService.getUserEntity(token);
		if (userEntity == null) {
			return GsonUtil.buildMap(1, "用户不存在", null);
		}
		if (awardNumber == null || awardNumber.getAward() == null) {
			return GsonUtil.buildMap(1, "奖品不存在", null);
		}
		if (awardNumber.getNum() < 0) {
			return GsonUtil.buildMap(1, "奖品兑换数量必须大于1", null);
		}

		// 对全家福大奖进行个数检查
		// if (awardNumber.getAward().getIndex() == 4) {
		// AwardEntity serverAwardEntity = awardService.getAwardEntity(4);
		// if (awardService.getSurplusAwardNumber(serverAwardEntity) <= 0) {
		// String message = serverAwardEntity.getAwardName() + "已经兑换完";
		// return GsonUtil.buildMap(-1, message, null);
		// }
		// }
		// 5000的逻辑
		int codePlus = awardService.checkMyValue(userEntity, awardNumber);
		if (codePlus > -1) {
			String message = "当前最多可兑换" + codePlus + "个" + awardNumber.getAward().getAwardName() + "，请您重新输入再兑换~";
			return GsonUtil.buildMap(-1, message, null);
		}
		if (!awardService.checkAwardNumber(awardNumber)) {
			return GsonUtil.buildMap(1, "奖品数量不足", null);
		}
		boolean result = awardService.convertAward(userEntity, awardNumber);
		if (result) {
			return GsonUtil.buildMap(0, "ok", awardNumber);
		} else {
			return GsonUtil.buildMap(1, "兑换失败", null);
		}
	}

}
