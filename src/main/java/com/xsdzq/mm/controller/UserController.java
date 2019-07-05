package com.xsdzq.mm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.JsonObject;
import com.xsdzq.mm.annotation.UserLoginToken;
import com.xsdzq.mm.model.Token;
import com.xsdzq.mm.model.User;
import com.xsdzq.mm.service.TokenService;
import com.xsdzq.mm.service.UserService;
import com.xsdzq.mm.util.GsonUtil;

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

	@PostMapping(value = "/setUser", produces = "application/json; charset=utf-8")
	public Map<String, Object> getPrize(@RequestBody User user) {
		// PrizeEntity prize = prizeService.getMyPrize();
		userService.setUser(user);
		User realUser = userService.findByClientId(user.getClientId());
		String token = tokenService.getToken(realUser);
		Token tokenObject = new Token();
		tokenObject.setToken(token);
		return GsonUtil.buildMap(0, "ok", tokenObject);
	}

	@UserLoginToken
	@GetMapping(value = "/getUserInfo", produces = "application/json; charset=utf-8")
	public String getUserInfo() {

		return "";
	}

}
