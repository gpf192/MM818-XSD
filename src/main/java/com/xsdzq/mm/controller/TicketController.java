package com.xsdzq.mm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsdzq.mm.annotation.UserLoginToken;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketTotalViewEntity;
import com.xsdzq.mm.entity.UserVoteEmpResultEntity;
import com.xsdzq.mm.model.Number;
import com.xsdzq.mm.model.Pagination;
import com.xsdzq.mm.model.UserTicketRecordAndResult;
import com.xsdzq.mm.model.VoteModel;
import com.xsdzq.mm.service.TokenService;
import com.xsdzq.mm.service.UserTicketService;
import com.xsdzq.mm.util.DateUtil;
import com.xsdzq.mm.util.GsonUtil;
import com.xsdzq.mm.util.PrizeUtil;

@RestController
@RequestMapping("/activity/ticket")
public class TicketController {

	private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

	@Autowired
	TokenService tokenService;

	@Autowired
	UserTicketService userTicketService;

	@GetMapping(value = "/number", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> getAvailableNumber(@RequestHeader("Authorization") String token) {
		System.out.println(token);
		UserEntity userEntity = tokenService.getUserEntity(token);
		int number = userTicketService.getUserTicket(userEntity);
		Number ticketNumber = new Number(number);
		return GsonUtil.buildMap(0, "ok", ticketNumber);
	}

	@GetMapping(value = "/userRecord", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> getUserRecord(@RequestHeader("Authorization") String token, @RequestParam int pageNumber,
			@RequestParam int pageSize) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		List<UserTicketRecordAndResult> list = userTicketService.getUserRecord(userEntity, pageNumber, pageSize);
		Pagination pagination = new Pagination(pageNumber, pageSize);
		int total = userTicketService.countUserVoteNumber(userEntity);
		pagination.setTotalItems(total);
		return GsonUtil.buildMap(0, "ok", list, pagination);
	}

	@PostMapping(value = "/vote", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> userVoteEmp(@RequestHeader("Authorization") String token,
			@RequestBody VoteModel voteModel) {

		String endFlag = tokenService.getValueByCode("kmhEndFlag").getValue();
		try {
			if (!DateUtil.checkDate(endFlag)) {
				return GsonUtil.buildMap(1, "活动已结束，无法进行此项操作。", null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return GsonUtil.buildMap(1, "活动已结束，无法进行此项操作。", null);
		}
		UserEntity userEntity = tokenService.getUserEntity(token);
		try {
			String empId = voteModel.getEmpId();
			System.out.println(voteModel.toString());
			String numberString = voteModel.getTicketNumber();
			int number;
			try {
				number = Integer.parseInt(numberString);
			} catch (Exception e) {
				// TODO: handle exception
				return GsonUtil.buildMap(1, "投票数只能是整数", null);
			}
			int myNumer = userTicketService.getUserTicket(userEntity);
			logger.info("nubmer: " + number);
			logger.info("myNumer: " + myNumer);
			System.out.print("nubmer: " + number);
			System.out.print("myNumer: " + myNumer);
			if (number > myNumer) {
				return GsonUtil.buildMap(1, "投票超过上限", null);
			}
			userTicketService.userVoteEmp(userEntity, empId, number);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return GsonUtil.buildMap(1, "投票异常", null);

		}
		return GsonUtil.buildMap(0, "ok", null);
	}

	@GetMapping(value = "/userSort", produces = "application/json; charset=utf-8")
	public Map<String, Object> getUserTicketSort(@RequestParam int pageNumber, @RequestParam int pageSize) {
		List<UserTicketTotalViewEntity> list = userTicketService.getUserTicketSort(pageNumber, pageSize);
		List<UserTicketTotalViewEntity> responsEntities = new ArrayList<UserTicketTotalViewEntity>();
		for (UserTicketTotalViewEntity entity : list) {
			// entity.setClientId(PrizeUtil.getInstance().getSecretString(entity.getClientId()));
			logger.info("origin: " + entity.toString());
			UserTicketTotalViewEntity uEntity = new UserTicketTotalViewEntity();
			uEntity.setClientId(PrizeUtil.getInstance().getSecretString(entity.getClientId()));
			uEntity.setTotal(entity.getTotal());
			logger.info("secret: " + uEntity.toString());
			responsEntities.add(uEntity);
		}

		Pagination pagination = new Pagination(pageNumber, pageSize);
		int total = userTicketService.countVoteNumber();
		pagination.setTotalItems(total);
		return GsonUtil.buildMap(0, "ok", responsEntities, pagination);
	}

	@GetMapping(value = "/myVoteEmp", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> getUserTicketVoteEmp(@RequestHeader("Authorization") String token,
			@RequestParam String gainTime) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		UserVoteEmpResultEntity voteEmpResultEntity = userTicketService.getUserVoteEmpResultEntity(userEntity,
				gainTime);
		return GsonUtil.buildMap(0, "ok", voteEmpResultEntity);
	}

}
