package com.xsdzq.mm.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xsdzq.mm.dao.HxUserRepository;
import com.xsdzq.mm.dao.LiveRecordRepository;
import com.xsdzq.mm.dao.LiveUserRepository;
import com.xsdzq.mm.dao.PrizeNumberRepository;
import com.xsdzq.mm.dao.PrizeRecordRepository;
import com.xsdzq.mm.dao.TokenRecordRepository;
import com.xsdzq.mm.dao.UserRepository;
import com.xsdzq.mm.dao.UserTicketRecordRepository;
import com.xsdzq.mm.entity.HxUserEntity;
import com.xsdzq.mm.entity.LiveRecordEntity;
import com.xsdzq.mm.entity.LiveUserEntity;
import com.xsdzq.mm.entity.PrizeNumberEntity;
import com.xsdzq.mm.entity.PrizeRecordEntity;
import com.xsdzq.mm.entity.TokenRecordEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketRecordEntity;
import com.xsdzq.mm.model.ActivityNumber;
import com.xsdzq.mm.model.User;
import com.xsdzq.mm.properties.HSURLProperties;
import com.xsdzq.mm.service.PrizeService;
import com.xsdzq.mm.service.UserService;
import com.xsdzq.mm.service.UserTicketService;
import com.xsdzq.mm.util.AESUtil;
import com.xsdzq.mm.util.DateUtil;
import com.xsdzq.mm.util.PrizeUtil;
import com.xsdzq.mm.util.TicketUtil;
import com.xsdzq.mm.util.UserUtil;

@Service(value = "userServiceImpl")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Value("${jwt.expiretime}")
	private int expiretime;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HxUserRepository hxUserRepository;

	@Autowired
	private LiveUserRepository liveUserRepository;

	@Autowired
	private LiveRecordRepository liveRecordRepository;

	@Autowired
	private PrizeNumberRepository prizeNumberRepository;

	@Autowired
	private PrizeRecordRepository prizeRecordRepository;

	@Autowired
	private UserTicketRecordRepository userTicketRecordRepository;

	@Autowired
	private PrizeService prizeService;

	@Autowired
	private UserTicketService userTicketService;

	@Autowired
	private TokenRecordRepository tokenRecordRepository;

	@Autowired
	private HSURLProperties hSURLProperties;

	@Autowired
	private RestTemplate restTemplate;

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
	public boolean hasSignAdviser(UserEntity userEntity) {
		// TODO Auto-generated method stub
		List<UserTicketRecordEntity> list = userTicketRecordRepository.findByUserEntityAndTypeAndVotesSource(userEntity,
				true, "6");
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasNewFundAccount(UserEntity userEntity) {
		// TODO Auto-generated method stub
		List<UserTicketRecordEntity> list = userTicketRecordRepository.findByUserEntityAndTypeAndVotesSource(userEntity,
				true, "5");
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean hsServiceCheck(String clientId, String loginClientId, String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("access_token", accessToken);
		HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
		String HS_GET_URL = hSURLProperties.getInfo();
		String url = HS_GET_URL + "?client_id=" + loginClientId;
		log.info(headers.toString());
		log.info("request url: " + url);
		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
					String.class);
			if (HttpStatus.OK == responseEntity.getStatusCode()) {
				String response = responseEntity.getBody();
				log.info("hs loginClientId " + loginClientId + " response: " + response);
				TokenRecordEntity tokenRecordEntity = new TokenRecordEntity();
				tokenRecordEntity.setClientId(clientId);
				tokenRecordEntity.setLoginClientId(loginClientId);
				tokenRecordEntity.setAccessToken(accessToken);
				tokenRecordEntity.setResponse(response);
				tokenRecordRepository.save(tokenRecordEntity);

				JSONObject hsJsonObject = JSON.parseObject(response);
				String responseClientId = hsJsonObject.getString("client_id");
				if (responseClientId != null && loginClientId.equals(responseClientId)) {
					// 校验通过
					log.info(loginClientId + " 校验通过");
					return true;

				} else {
					return false;
					// throw new BusinessException("登录失败，请重新登录");
				}

			} else {
				log.error("#method# 远程调用失败 httpCode = [{}]", responseEntity.getStatusCode());
				return false;
				// throw new BusinessException("登录服务异常");
			}
		} catch (Exception e) {
			// TODO: handle exception
			TokenRecordEntity tokenRecordEntity = new TokenRecordEntity();
			tokenRecordEntity.setClientId(clientId);
			tokenRecordEntity.setLoginClientId(loginClientId);
			tokenRecordEntity.setAccessToken(accessToken);
			tokenRecordEntity.setResponse(e.getMessage());
			log.info("恒生调用 base/get 异常:" + tokenRecordEntity.toString());
			tokenRecordRepository.save(tokenRecordEntity);
			return false;
			// throw new BusinessException("登录失败，请重新登录");
		}

	}

	@Override
	@Transactional
	public ActivityNumber login(User user) {
		// 0 前置 恒生校验
		/*
		 * if (user.getLoginClientId() != null && user.getLoginClientId().length() > 0)
		 * { boolean isCheck = hsServiceCheck(user.getClientId(),
		 * user.getLoginClientId(), user.getAccessToken()); if (!isCheck) { return null;
		 * } } else { log.info(user.getClientId() + " 校验未通过"); }
		 */
		// 1 登录逻辑
		UserEntity owner = userRepository.findByClientId(user.getClientId());
		if (owner == null) {
			UserEntity requestUser = UserUtil.convertUserByUserEntity(user);
			userRepository.save(requestUser);
		} else {
			UserUtil.updateUserEntityByUser(owner, user);
			userRepository.saveAndFlush(owner);
		}
		UserEntity realUserEntity = userRepository.findByClientId(user.getClientId());
		// 用户的抽奖次数
		// int prizeNumber = handlerLoginPrize(realUserEntity); -- 登录不给抽奖次数
		int prizeNumber = prizeService.getAvailableNumber(realUserEntity);
		// 处理登陆后的票数
		// int ticketNumber = handlerLoginTicket(realUserEntity);-- 没有这个逻辑
		int ticketNumber = 0;
		ActivityNumber activityNumber = new ActivityNumber();
		activityNumber.setPrizeNumber(prizeNumber);
		activityNumber.setTicketNumber(ticketNumber);
		return activityNumber;
	}

	private int handlerLoginPrize(UserEntity userEntity) {

		boolean hasLoginPrize = hasEveryLoginPrize(userEntity);
		if (hasLoginPrize) {
			// 当日已经给过抽奖次数
		} else {
			prizeService.addPrizeNumber(userEntity, true, PrizeUtil.PRIZE_LOGIN_TYPE, 1);
		}
		int number = prizeService.getAvailableNumber(userEntity);
		return number;
	}

	private int handlerLoginTicket(UserEntity userEntity) {
		boolean hasLoginTicket = hasEveryLoginTicket(userEntity);
		if (hasLoginTicket) {
			// 已经添加过登陆票数了，不需要添加
		} else {
			// 添加每天的登陆票数 100票
			Date nowDate = new Date();
			userTicketService.addUserTicketNumber(userEntity, 100, TicketUtil.ACTIVITYLOGINTICKET, nowDate);
		}
		int number = userTicketService.getUserTicket(userEntity);
		return number;
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

	public boolean hasEveryLoginPrize(UserEntity userEntity) {
		String nowString = DateUtil.getStandardDate(new Date());
		List<PrizeRecordEntity> prizeRecordEntities = prizeRecordRepository.getListByUserAndFlag(userEntity, nowString);
		for (PrizeRecordEntity prizeRecordEntity : prizeRecordEntities) {
			String reasonString = prizeRecordEntity.getReason();
			if (reasonString.equals(PrizeUtil.PRIZE_LOGIN_TYPE)) {
				// 表示当日登陆成功已经给我一次抽奖机会
				return true;
			}
		}
		return false;
	}

	public boolean hasEveryLoginTicket(UserEntity userEntity) {
		String nowString = DateUtil.getStandardDate(new Date());
		List<UserTicketRecordEntity> userTicketRecordEntities = userTicketRecordRepository
				.findByUserEntityAndDateFlag(userEntity, nowString);
		for (UserTicketRecordEntity userTicketRecordEntity : userTicketRecordEntities) {
			String reasonString = userTicketRecordEntity.getVotesSource();
			if (TicketUtil.ACTIVITYLOGINTICKET.equals(reasonString)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public String loginHx(User user) {
		// TODO Auto-generated method stub
		HxUserEntity owner = this.hxUserRepository.findByClientId(user.getClientId());
		if (owner == null) {
			String uuid = null;
			try {
				uuid = AESUtil.getUuid();
			} catch (Exception e) {
				e.printStackTrace();
			}
			HxUserEntity requestUser = UserUtil.convertHxUserEntityByUser(user);
			requestUser.setUuid(uuid);
			hxUserRepository.save(requestUser);
			return uuid;
		}
		UserUtil.updateHxUserEntityByUser(owner, user);

		hxUserRepository.saveAndFlush(owner);
		return owner.getUuid();
	}

	@Override
	@Transactional
	public String loginLive(User user) {
		// TODO Auto-generated method stub
		LiveUserEntity owner = this.liveUserRepository.findByClientId(user.getClientId());
		if (owner == null) {
			String uuid = null;
			try {
				uuid = AESUtil.getUuid().substring(0, 20);
			} catch (Exception e) {
				e.printStackTrace();
			}
			LiveUserEntity requestUser = UserUtil.convertLiveUserEntityByUser(user);
			requestUser.setUuid(uuid);
			liveUserRepository.save(requestUser);
			LiveRecordEntity loginRecord = new LiveRecordEntity();
			loginRecord.setRecordTime(new Date());
			loginRecord.setUserEntity(requestUser);
			liveRecordRepository.add(loginRecord);// 增加登录记录
			return uuid;
		}
		UserUtil.updateLiveUserEntityByUser(owner, user);
		liveUserRepository.saveAndFlush(owner);
		LiveRecordEntity loginRecord = new LiveRecordEntity();
		loginRecord.setRecordTime(new Date());
		loginRecord.setUserEntity(owner);
		liveRecordRepository.add(loginRecord);// 增加登录记录
		return owner.getUuid();
	}
}
