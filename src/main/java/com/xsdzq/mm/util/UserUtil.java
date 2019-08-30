package com.xsdzq.mm.util;

import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.User;

public class UserUtil {

	public static User convertUserByUserEntity(UserEntity userEntity) {
		User user = new User();
		user.setClientId(userEntity.getClientId());
		user.setClientName(userEntity.getClientName());
		user.setFundAccount(userEntity.getFundAccount());
		user.setAccessToken(userEntity.getAccessToken());
		user.setMobile(userEntity.getMobile());
		user.setAppVersion(userEntity.getAppVersion());
		user.setLastOpIP(userEntity.getLastOpIP());
		user.setLastLoginTime(userEntity.getLastLoginTime());
		return user;
	}

	public static UserEntity convertUserByUserEntity(User user) {
		UserEntity userEntity = new UserEntity();
		userEntity.setClientId(user.getClientId());
		userEntity.setClientName(user.getClientName());
		userEntity.setFundAccount(user.getFundAccount());
		userEntity.setAccessToken(user.getAccessToken());
		userEntity.setMobile(user.getMobile());
		userEntity.setAppVersion(user.getAppVersion());
		userEntity.setLastOpIP(user.getLastOpIP());
		userEntity.setLastLoginTime(user.getLastLoginTime());
		return userEntity;
	}

	public static void updateUserEntityByUser(UserEntity userEntity, User user) {
		userEntity.setClientId(user.getClientId());
		userEntity.setClientName(user.getClientName());
		userEntity.setFundAccount(user.getFundAccount());
		userEntity.setAccessToken(user.getAccessToken());
		userEntity.setMobile(user.getMobile());
		userEntity.setAppVersion(user.getAppVersion());
		userEntity.setLastOpIP(user.getLastOpIP());
		userEntity.setLastLoginTime(user.getLastLoginTime());
	}

}
