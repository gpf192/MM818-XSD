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
import com.xsdzq.mm.model.AesInfo;
import com.xsdzq.mm.model.HasNumber;
import com.xsdzq.mm.model.LiveInfo;
import com.xsdzq.mm.model.User;
import com.xsdzq.mm.model.UserData;
import com.xsdzq.mm.service.TokenService;
import com.xsdzq.mm.service.UserService;
import com.xsdzq.mm.util.AESUtil;
import com.xsdzq.mm.util.GsonUtil;
import com.xsdzq.mm.util.LiveUtil;
import com.xsdzq.mm.util.UserUtil;

@RestController
@RequestMapping("/activity/user")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(UserController.class.getName());

	@Autowired
	@Qualifier("tokenServiceImpl")
	TokenService tokenService;

	@Autowired
	@Qualifier("userServiceImpl")
	UserService userService;

	@GetMapping(value = "/test")
	public String Test() {
		Long id = (long) 100;
		userService.getUserById(id);
		return "test";
	}

	@PostMapping(value = "/login", produces = "application/json; charset=utf-8")
	public Map<String, Object> login(@RequestBody UserData userData) throws Exception {
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

	  @PostMapping(value={"/loginHx"}, produces={"application/json; charset=utf-8"})
	  public Map<String, Object> loginHx(@RequestBody UserData userData)
	    throws Exception
	  {
	    this.logger.info(userData.toString());
	    System.out.println(userData.toString());
	    this.logger.info(userData.toString());
	    System.out.println(userData.toString());
	    String cryptUserString = userData.getEncryptData();
	    String userString = AESUtil.decryptAES(cryptUserString);
	    this.logger.info(userString);
	    User user = (User)JSON.parseObject(userString, User.class);
	    
	    String uuid = this.userService.loginHx(user);
	    
	    String aesUid = AESUtil.encryptAES(uuid);
	    AesInfo aesInfo = new AesInfo();
	    aesInfo.setAesUid(aesUid);
	    return GsonUtil.buildMap(0, "ok", aesInfo);
	  }
	//直播接口
	  @PostMapping(value={"/loginLive"}, produces={"application/json; charset=utf-8"})
	  public Map<String, Object> loginLive(@RequestBody UserData userData)
	    throws Exception
	  {
	    this.logger.info(userData.toString());
	    System.out.println(userData.toString());
	    this.logger.info(userData.toString());
	    System.out.println(userData.toString());
	    String cryptUserString = userData.getEncryptData();
	    String userString = AESUtil.decryptAES(cryptUserString);
	    this.logger.info(userString);
	    User user = (User)JSON.parseObject(userString, User.class);
	    String uuid = "";
	    if("".equals(user.getClientId())) {
	    	//游客，不用生成登录记录
	    	uuid = AESUtil.getUuid().substring(0, 20);
	    }else {
	    	//非游客生成唯一标识
		    uuid = this.userService.loginLive(user);
	    }
	    
	    //获取直播url
	    String liveUrl = LiveUtil.getUrl(user, uuid);
	    this.logger.info("_____________________ 直播信息"+user.getClientId()+" "+liveUrl);

	    LiveInfo liveInfo = new LiveInfo();
	    liveInfo.setLiveUrl(liveUrl);
	    return GsonUtil.buildMap(0, "ok", liveInfo);
	  }
	  //直播专用接口
	  @PostMapping(value={"/loginLivePro"}, produces={"application/json; charset=utf-8"})
	  public Map<String, Object> loginLivePro(@RequestBody UserData userData)
	    throws Exception
	  {
	    this.logger.info(userData.toString());
	    System.out.println(userData.toString());
	    this.logger.info(userData.toString());
	    System.out.println(userData.toString());
	    String cryptUserString = userData.getEncryptData();
	    String userString = AESUtil.decryptAES(cryptUserString);
	    this.logger.info(userString);
	    User user = (User)JSON.parseObject(userString, User.class);
	    String uuid = "";
	    if("".equals(user.getClientId())) {
	    	//游客，不用生成登录记录
	    	uuid = AESUtil.getUuid().substring(0, 20);
	    }else {
	    	//非游客生成唯一标识
		    uuid = this.userService.loginLive(user);
	    }
	        
	    //获取直播url
	    String liveUrl = LiveUtil.getUrlPro(user, uuid);
	    this.logger.info("_____________________ 直播信息"+user.getClientId()+" "+liveUrl);

	    LiveInfo liveInfo = new LiveInfo();
	    liveInfo.setLiveUrl(liveUrl);
	    return GsonUtil.buildMap(0, "ok", liveInfo);
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

	@UserLoginToken
	@GetMapping(value = "/hasSignAdviser", produces = "application/json; charset=utf-8")
	public Map<String, Object> hasSignAdviser(@RequestHeader("Authorization") String token) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		boolean hasAdviser = userService.hasSignAdviser(userEntity);
		HasNumber hasNumber = new HasNumber();
		hasNumber.setHasNumber(hasAdviser);
		return GsonUtil.buildMap(0, "ok", hasNumber);
	}

	@UserLoginToken
	@GetMapping(value = "/hasNewFundAccount", produces = "application/json; charset=utf-8")
	public Map<String, Object> hasNewFundAccount(@RequestHeader("Authorization") String token) {
		UserEntity userEntity = tokenService.getUserEntity(token);
		boolean hasAccount = userService.hasNewFundAccount(userEntity);
		HasNumber hasNumber = new HasNumber();
		hasNumber.setHasNumber(hasAccount);
		return GsonUtil.buildMap(0, "ok", hasNumber);
	}

}
