package com.xsdzq.mm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.xsdzq.mm.annotation.UserLoginToken;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.Token;
import com.xsdzq.mm.model.User;
import com.xsdzq.mm.service.TokenService;
import com.xsdzq.mm.service.UserService;
import com.xsdzq.mm.util.GsonUtil;
import com.xsdzq.mm.util.UserUtil;

@RestController
@RequestMapping("/user")
public class UserController {
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
	public Map<String, Object> login(@RequestBody User user) {
		// PrizeEntity prize = prizeService.getMyPrize();
		System.out.println(user.toString());
		userService.login(user);
		UserEntity userEntity = userService.getUserByClientId(user.getClientId());
		userService.setUserInfo(userEntity);
		userService.addEveryLoginPrizeNumber(userEntity);
		String token = tokenService.getToken(UserUtil.convertUserByUserEntity(userEntity));
		Token tokenObject = new Token();
		tokenObject.setToken(token);
		return GsonUtil.buildMap(0, "ok", tokenObject);
	}

	@UserLoginToken
	@GetMapping(value = "/getUserInfo", produces = "application/json; charset=utf-8")
	public Map<String, Object> getUserInfo(@RequestHeader("Authorization") String token) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		userService.setUserInfo(userEntity);
		userService.addEveryLoginPrizeNumber(userEntity);
		User realUser = userService.findByClientId(userEntity.getClientId());
		return GsonUtil.buildMap(0, "ok", realUser);
	}

}
