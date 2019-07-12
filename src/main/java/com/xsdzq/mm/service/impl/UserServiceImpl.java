package com.xsdzq.mm.service.impl;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xsdzq.mm.dao.PrizeNumberRepository;
import com.xsdzq.mm.dao.PrizeRecordRepository;
import com.xsdzq.mm.dao.UserRepository;
import com.xsdzq.mm.entity.PrizeNumberEntity;
import com.xsdzq.mm.entity.PrizeRecordEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.User;
import com.xsdzq.mm.service.PrizeService;
import com.xsdzq.mm.service.UserService;
import com.xsdzq.mm.util.DateUtil;
import com.xsdzq.mm.util.PrizeUtil;
import com.xsdzq.mm.util.UserUtil;

@Service(value = "userServiceImpl")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Value("${jwt.expiretime}")
	private int expiretime;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PrizeNumberRepository prizeNumberRepository;

	@Autowired
	private PrizeRecordRepository prizeRecordRepository;

	@Autowired
	private PrizeService prizeService;

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		// Optional<UserEntity> userEntity = userRepository.findById(id);

		return null;
	}
	

	@Override
	public UserEntity getUserByClientId(String clientId) {
		// TODO Auto-generated method stub
		return userRepository.findByClientId(clientId);
	}

	@Override
	public User getUserById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void login(User user) {
		UserEntity owner = userRepository.findByClientId(user.getClientId());
		if (owner == null) {
			UserEntity requestUser = UserUtil.convertUserByUserEntity(user);
			userRepository.save(requestUser);
		} else {
			UserUtil.updateUserEntityByUser(owner, user);
			userRepository.saveAndFlush(owner);
		}
	}

	@Override
	@Transactional
	public void setUserInfo(UserEntity userEntity) {
		// TODO Auto-generated method stub
		PrizeNumberEntity prizeNumberEntity = prizeNumberRepository.findByUserEntity(userEntity);
		if (prizeNumberEntity == null) {
			createPrizeNumberEntity(userEntity);
		}
	}

	@Override
	public User findByClientId(String clientId) {
		// TODO Auto-generated method stub
		UserEntity owner = userRepository.findByClientId(clientId);
		User user = UserUtil.convertUserByUserEntity(owner);
		return user;
	}

	public void createPrizeNumberEntity(UserEntity userEntity) {
		System.out.println("------------------------");
		System.out.println(userEntity.toString());
		PrizeNumberEntity prizeNumberEntity = new PrizeNumberEntity();
		prizeNumberEntity.setUserEntity(userEntity);
		prizeNumberEntity.setNumber(0);
		prizeNumberRepository.create(prizeNumberEntity);
	}

	@Override
	@Transactional
	public void addEveryLoginPrizeNumber(UserEntity userEntity) {
		String nowString = DateUtil.getStandardDate(new Date());
		List<PrizeRecordEntity> prizeRecordEntities = prizeRecordRepository.getListByUserAndFlag(userEntity, nowString);
		for (PrizeRecordEntity prizeRecordEntity : prizeRecordEntities) {
			String reasonString = prizeRecordEntity.getReason();
			if (reasonString.equals(PrizeUtil.PRIZE_LOGIN_TYPE)) {
				// 表示当日登陆成功已经给我一次抽奖机会
				return;
			}
		}
		prizeService.addPrizeNumber(userEntity, true, PrizeUtil.PRIZE_LOGIN_TYPE, 1);
		//return true;
	}

}
