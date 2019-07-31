package com.xsdzq.mm.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.xsdzq.mm.annotation.UserLoginToken;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.ActivityNumber;
import com.xsdzq.mm.model.User;
import com.xsdzq.mm.model.UserData;
import com.xsdzq.mm.service.TokenService;
import com.xsdzq.mm.service.UserService;
import com.xsdzq.mm.util.AESUtil;
import com.xsdzq.mm.util.GsonUtil;
import com.xsdzq.mm.util.UserUtil;

@RestController
@RequestMapping("/activity/user")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(UserController.class.getName());
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;

	@Autowired
	@Qualifier("tokenServiceImpl")
	TokenService tokenService;

	@GetMapping(value = "/test")
	public String Test() {
		Long id = (long) 100;
		userService.getUserById(id);
		return "test";
	}

	@PostMapping(value = "/login", produces = "application/json; charset=utf-8")
	public Map<String, Object> login(@RequestBody UserData userData) throws Exception {
		logger.info(userData.toString());
		System.out.println(userData.toString());
		String cryptUserString = userData.getEncryptData();
		String userString = AESUtil.decryptAES(cryptUserString);
		logger.info(userString);
		User user = JSON.parseObject(userString, User.class);
		ActivityNumber activityNumber = userService.login(user);
		UserEntity userEntity = userService.getUserByClientId(user.getClientId());
		String token = tokenService.getToken(UserUtil.convertUserByUserEntity(userEntity));
		activityNumber.setToken(token);
		return GsonUtil.buildMap(0, "ok", activityNumber);
	}

	@UserLoginToken
	@GetMapping(value = "/getUserInfo", produces = "application/json; charset=utf-8")
	public Map<String, Object> getUserInfo(@RequestHeader("Authorization") String token) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		userService.setUserInfo(userEntity);
		// userService.addEveryLoginPrizeNumber(userEntity);
		User realUser = userService.findByClientId(userEntity.getClientId());
		return GsonUtil.buildMap(0, "ok", realUser);
	}

}
