package com.xsdzq.mm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsdzq.mm.annotation.UserLoginToken;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.PageEvent;
import com.xsdzq.mm.service.PageEventService;
import com.xsdzq.mm.service.TokenService;
import com.xsdzq.mm.util.GsonUtil;

@RestController
@RequestMapping("/activity/event")
public class PageEventController {
	@Autowired
	private PageEventService pageEventService;

	@Autowired
	TokenService tokenService;

	@PostMapping(value = "/add", produces = "application/json; charset=utf-8")
	@UserLoginToken
	public Map<String, Object> getConvertRecord(@RequestHeader("Authorization") String token,
			@RequestBody PageEvent pageEvent) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		pageEventService.savePageEvent(userEntity, pageEvent);
		return GsonUtil.buildMap(0, "ok", null);
	}

}
