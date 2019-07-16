package com.xsdzq.mm.controller;

import java.util.List;
import java.util.Map;

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
import com.xsdzq.mm.entity.UserTicketRecordEntity;
import com.xsdzq.mm.entity.UserTicketTotalViewEntity;
import com.xsdzq.mm.model.Number;
import com.xsdzq.mm.model.VoteModel;
import com.xsdzq.mm.service.TokenService;
import com.xsdzq.mm.service.UserTicketService;
import com.xsdzq.mm.util.GsonUtil;

@RestController
@RequestMapping("/ticket")
public class TicketController {

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
	public Map<String, Object> getUserRecord(@RequestHeader("Authorization") String token) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		List<UserTicketRecordEntity> list = userTicketService.getUserRecord(userEntity);
		return GsonUtil.buildMap(0, "ok", list);
	}

	@PostMapping(value = "/vote", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> userVoteEmp(@RequestHeader("Authorization") String token,
			@RequestBody VoteModel voteModel) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		try {
			String empId = voteModel.getEmpId();
			System.out.println(voteModel.toString());
			String numberString = voteModel.getTicketNumber();
			int number = Integer.parseInt(numberString);
			userTicketService.userVoteEmp(userEntity, empId, number);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("---ddddddd---");
			System.out.println(e.getMessage());
		}

		return GsonUtil.buildMap(0, "ok", null);
	}

	@GetMapping(value = "/userSort", produces = "application/json; charset=utf-8")
	public Map<String, Object> getUserTicketSort(@RequestParam int pageNumber, @RequestParam int pageSize) {
		List<UserTicketTotalViewEntity> list = userTicketService.getUserTicketSort(pageNumber, pageSize);
		return GsonUtil.buildMap(0, "ok", list);
	}

}