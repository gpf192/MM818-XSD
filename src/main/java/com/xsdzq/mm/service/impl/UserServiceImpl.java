package com.xsdzq.mm.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.CreditAccountOpenViewRepository;
import com.xsdzq.mm.dao.EmpRepository;
import com.xsdzq.mm.dao.OpenAccountRepository;
import com.xsdzq.mm.dao.ParamRepository;
import com.xsdzq.mm.dao.PrizeNumberRepository;
import com.xsdzq.mm.dao.PrizeRecordRepository;
import com.xsdzq.mm.dao.ShareOptionAccountOpenViewRepository;
import com.xsdzq.mm.dao.SignInvestViewRepository;
import com.xsdzq.mm.dao.UserEmpRelationRepository;
import com.xsdzq.mm.dao.UserRepository;
import com.xsdzq.mm.dao.UserTicketRecordRepository;
import com.xsdzq.mm.entity.CreditAccountOpenViewEntity;
import com.xsdzq.mm.entity.EmpEntity;
import com.xsdzq.mm.entity.OpenAccountEntity;
import com.xsdzq.mm.entity.ParamEntity;
import com.xsdzq.mm.entity.PrizeNumberEntity;
import com.xsdzq.mm.entity.PrizeRecordEntity;
import com.xsdzq.mm.entity.ShareOptionAccountOpenViewEntity;
import com.xsdzq.mm.entity.SignInvestViewEntity;
import com.xsdzq.mm.entity.UserEmpRelationEntity;
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
	private EmpRepository empRepository;
	
	@Autowired
	private PrizeService prizeService;

	@Autowired
	private UserTicketService userTicketService;
	
	@Autowired
	OpenAccountRepository openAccountRepository;
	
	@Autowired
	ParamRepository paramRepository;
	
	@Autowired
	SignInvestViewRepository signInvestViewRepository;
	//开门红
	@Autowired
	private CreditAccountOpenViewRepository creditAccountOpenViewRepository;
	
	@Autowired
	private ShareOptionAccountOpenViewRepository shareOptionAccountOpenViewRepository;
	
	
	@Autowired
	private UserEmpRelationRepository userEmpRelationRepository;
	
	
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
	public void addTicketByJobWithEmpId(String clientId, String clientName, String empId, int num, String reason) {
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
				//插入用户得票记录表,同时用户票数表增加票
				Date date = new Date();
				userTicketService.addUserTicketNumberByJob(newUser, num, reason, date,"");
				//插入用户投票员工表,同时用户减票,员工增票
				userTicketService.userVoteEmpByJob(newUser, empId, num, reason, date);
				
			}else {
				Date date = new Date();
				//插入用户得票记录表 票数表
				userTicketService.addUserTicketNumberByJob(user, num, reason, date,"");
				//插入用户投票员工表,同时用户减票,员工增票
				userTicketService.userVoteEmpByJob(user, empId, num, reason, date);
			}
		

	}
	
	@Override
	@Transactional
	public void addTicketByJob(String clientId, String clientName, int num, String reason, String lsh) {
		// TODO Auto-generated method stub
		//判断用户是否存在
		UserEntity user = userRepository.findByClientId(clientId);
		Date date = new Date();
		if (user == null) {
			UserEntity newUser = new UserEntity();
			newUser.setClientId(clientId);
			newUser.setClientName(clientName);
			newUser.setCreatetime(new Date());
			//添加新用户
			userRepository.save(newUser);
			//添加用户票数表  先查存在不 若不存在则新建		
			//插入用户得票记录表 同时用户票数表增加票
			userTicketService.addUserTicketNumberByJob(newUser, num, reason, date,lsh);
					
		}else {
			//插入用户得票记录表
			userTicketService.addUserTicketNumberByJob(user, num, reason, date,lsh);
			
		}
	}


	@Override
	public List<OpenAccountEntity> findByOpenDate(int preDay) {
		// TODO Auto-generated method stub
		return openAccountRepository.findByOpenDateLessThanEqual(preDay);
	}

	@Override
	public int countByOpenDateLessThanEqualAndOpenDateGreaterThanEqualAndClientId(int endDate, int beginDate,  String clientId) {
		// TODO Auto-generated method stub
		return openAccountRepository.countByOpenDateLessThanEqualAndOpenDateGreaterThanEqualAndClientId(endDate, beginDate, clientId);
	}

	@Override
	public ParamEntity getValueByCode(String code) {
		// TODO Auto-generated method stub
		return paramRepository.getValueByCode(code);
	}

	@Override
	public List<UserTicketRecordEntity> findByVotesSourceAndUserEntity_clientId(String votesSource, String clientId) {
		// TODO Auto-generated method stub
		return userTicketRecordRepository.findByVotesSourceAndUserEntity_clientId(votesSource, clientId);
	}

	@Override
	public List<SignInvestViewEntity> findByserviceTypeAndStatusAndEffectiveDate(int serviceType, String status,
			String effectiveDate) {
		// TODO Auto-generated method stub
		return signInvestViewRepository.findByserviceTypeAndStatusAndEffectiveDateLessThanEqual(serviceType, status, effectiveDate);
	}

	@Override
	public List<UserTicketRecordEntity> findByVotesSourceAndUserEntity_clientIdAndDateFlag(String votesSource,
			String clientId, String dateFlag) {
		// TODO Auto-generated method stub
		 return userTicketRecordRepository.findByVotesSourceAndUserEntity_clientIdAndDateFlag(votesSource, clientId, dateFlag);
	}
	@Override
	public List<UserTicketRecordEntity> findBySerialNum(String serialNum) {
		// TODO Auto-generated method stub
		 return userTicketRecordRepository.findBySerialNum(serialNum);
	}
	@Override
	public List<PrizeRecordEntity> findPrizeRecordBySerialNum(String serialNum) {
		// TODO Auto-generated method stub
		 return prizeRecordRepository.findPrizeRecordBySerialNum(serialNum);
	}
	//开门红活动
	@Override
	@Transactional
	public void addPrizeNumAndRecordForKMH(String clientId, String reason, int number, String serialNum) {
		// TODO Auto-generated method stub
		UserEntity user = userRepository.findByClientId(clientId);
		UserEmpRelationEntity ue = userEmpRelationRepository.findByClientId(clientId);			
		if (user == null) {
			UserEntity newUser = new UserEntity();
			newUser.setClientId(clientId);
			if(ue != null && ue.getClientName()!= null && ue.getClientName().length()>1) {
				newUser.setClientName(ue.getClientName());
				
			}
			newUser.setCreatetime(new Date());//添加新建时间戳
			//添加新用户
			userRepository.save(newUser);
			user = newUser;
		}else {
			if(ue != null && ue.getClientName()!= null && ue.getClientName().length()>1) {
				user.setClientName(ue.getClientName());
				userRepository.save(user);
			}
			
		}
		prizeService.addPrizeNumberForKMH(user, reason, number, serialNum);
	}
	@Override  
	public List<CreditAccountOpenViewEntity> findCreditAccountBydataFlag(int dataFlag) {
		// TODO Auto-generated method stub
		return creditAccountOpenViewRepository.findByDateFlagLessThanEqual(dataFlag);
	}
	
	@Override 
	public List<ShareOptionAccountOpenViewEntity> findShareOptionAccountBydataFlag(int dataFlag) {
		// TODO Auto-generated method stub
		return shareOptionAccountOpenViewRepository.findByDateFlagLessThanEqual(dataFlag);
	}
	
	@Override 
	public List<PrizeRecordEntity> findPrizeRecordByClinetIdAndReason(String clientId, String reason) {
		// TODO Auto-generated method stub
		UserEntity userEntity = userRepository.findByClientId(clientId);
		return prizeRecordRepository.findPrizeRecordByClinetIdAndReason(userEntity, reason);
	}
}
