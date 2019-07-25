package com.xsdzq.mm.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.PrizeNumberRepository;
import com.xsdzq.mm.dao.PrizeRecordRepository;
import com.xsdzq.mm.dao.UserRepository;
import com.xsdzq.mm.dao.UserTicketRecordRepository;
import com.xsdzq.mm.entity.PrizeNumberEntity;
import com.xsdzq.mm.entity.PrizeRecordEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketRecordEntity;
import com.xsdzq.mm.model.ActivityNumber;
import com.xsdzq.mm.model.User;
import com.xsdzq.mm.service.PrizeService;
import com.xsdzq.mm.service.UserService;
import com.xsdzq.mm.service.UserTicketService;
import com.xsdzq.mm.util.DateUtil;
import com.xsdzq.mm.util.PrizeUtil;
import com.xsdzq.mm.util.TicketUtil;
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
	private UserTicketRecordRepository userTicketRecordRepository;

	@Autowired
	private PrizeService prizeService;

	@Autowired
	private UserTicketService userTicketService;

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
	public ActivityNumber login(User user) {
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
		int prizeNumber = handlerLoginPrize(realUserEntity);
		// 处理登陆后的票数
		int ticketNumber = handlerLoginTicket(realUserEntity);
		System.out.println("user prize ticket: " + prizeNumber);
		System.out.println("user login ticket: " + ticketNumber);
		ActivityNumber activityNumber = new ActivityNumber();
		activityNumber.setPrizeNumber(prizeNumber);
		activityNumber.setTicketNumber(ticketNumber);
		return activityNumber;
	}

	private int handlerLoginPrize(UserEntity userEntity) {
		// prizeService.addPrizeNumber(userEntity, true, PrizeUtil.PRIZE_LOGIN_TYPE, 1);
		// return true;
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
			userTicketService.addUserTicketNumber(userEntity, 100, TicketUtil.ACTIVITYLOGINTICKET);
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
	//JOB
	@Override
	@Transactional
	public void addTicketByJobWithEmpId(String clientId, String clientName, String empId, int num) {
		// TODO Auto-generated method stub
		//判断用户是否存在
		UserEntity user = userRepository.findByClientId(clientId);
		if (user == null) {
			UserEntity newUser = new UserEntity();
			newUser.setClientId(clientId);
			newUser.setClientName(clientName);
			//添加新用户
			userRepository.save(newUser);
			//添加用户票数表  先查存在不 若不存在则新建
			userTicketService.getUserTicketEntity(newUser);
			//插入用户得票记录表,同时用户票数表增加票
			userTicketService.addUserTicketNumberByJob(newUser, num, "3");
			//插入用户投票员工表,同时用户减票,员工增票
			userTicketService.userVoteEmpByJob(newUser, empId, num, "3");
			
		}else {
			//插入用户得票记录表
			userTicketService.addUserTicketNumberByJob(user, num, "3");
			//插入用户投票员工表,同时用户减票,员工增票
			userTicketService.userVoteEmpByJob(user, empId, num, "3");
		}
	}
	
	@Override
	@Transactional
	public void addTicketByJob(String clientId, String clientName, int num) {
		// TODO Auto-generated method stub
		//判断用户是否存在
		UserEntity user = userRepository.findByClientId(clientId);
		if (user == null) {
			UserEntity newUser = new UserEntity();
			newUser.setClientId(clientId);
			newUser.setClientName(clientName);
			//添加新用户
			userRepository.save(newUser);
			//添加用户票数表  先查存在不 若不存在则新建
			userTicketService.getUserTicketEntity(newUser);
			//插入用户得票记录表 同时用户票数表增加票
			userTicketService.addUserTicketNumberByJob(newUser, num, "3");
					
		}else {
			//插入用户得票记录表
			userTicketService.addUserTicketNumberByJob(user, num, "3");
			
		}
	}

	@Override
	public void addTicketByJobForReduceEmp(String clientId, String empId, int num, String reason) {
		// TODO Auto-generated method stub
		UserEntity user = userRepository.findByClientId(clientId);
		//添加用户票数表  先查存在不 若不存在则新建
		userTicketService.getUserTicketEntity(user);
		//前端登录 用户记录表已经插入得票数据 这里不需要重复插入
		//插入用户投票员工表,同时用户减票,员工加票
		userTicketService.userVoteEmpByJob(user, empId, num, reason);
	}
}
