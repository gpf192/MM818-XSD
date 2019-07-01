package com.xsdzq.mm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xsdzq.mm.annotation.UserLoginToken;
import com.xsdzq.mm.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;

	@GetMapping(value = "/test")
	public String Test() {
		Long id = (long) 100;
		userService.getUserById(id);
		return "test";
	}

	@UserLoginToken
	@GetMapping(value = "/getUserInfo", produces = "application/json; charset=utf-8")
	public String getUserInfo() {

		return "";
	}

}
