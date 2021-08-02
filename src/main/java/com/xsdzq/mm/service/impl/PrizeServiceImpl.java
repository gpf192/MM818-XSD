package com.xsdzq.mm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.ParamRepository;
import com.xsdzq.mm.dao.PrizeNumberRepository;
import com.xsdzq.mm.dao.PrizeRecordRepository;
import com.xsdzq.mm.dao.PrizeRepository;
import com.xsdzq.mm.dao.PrizeResultRepository;
import com.xsdzq.mm.dao.UserTicketRecordRepository;
import com.xsdzq.mm.dao.impl.ParamRepositoryImpl;
import com.xsdzq.mm.entity.ParamEntity;
import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.entity.PrizeNumberEntity;
import com.xsdzq.mm.entity.PrizeRecordEntity;
import com.xsdzq.mm.entity.PrizeResultEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketRecordEntity;
import com.xsdzq.mm.service.PrizeService;
import com.xsdzq.mm.service.UserTicketService;
import com.xsdzq.mm.util.DateUtil;
import com.xsdzq.mm.util.PrizeUtil;
import com.xsdzq.mm.util.TicketUtil;

@Service(value = "prizeServiceImpl")
@Transactional(readOnly = true)
public class PrizeServiceImpl implements PrizeService {

	@Autowired
	private PrizeRepository prizeRepository;

	@Autowired
	private ParamRepository paramRepository;

	@Autowired
	private PrizeNumberRepository prizeNumberRepository;

	@Autowired
	private PrizeRecordRepository prizeRecordRepository;

	@Autowired
	private PrizeResultRepository prizeResultRepository;

	@Autowired
	private UserTicketRecordRepository userTicketRecordRepository;

	@Autowired
	private UserTicketService userTicketService;

	// 提供统一的PrizeNumberEntity，没有的话会新增，不会得到空值
	@Override
	@Transactional
	public PrizeNumberEntity getPrizeNumberEntity(UserEntity userEntity) {
		PrizeNumberEntity prizeNumberEntity = prizeNumberRepository.findByUserEntity(userEntity);
		if (prizeNumberEntity == null) {
			PrizeNumberEntity myPrizeNumberEntity = new PrizeNumberEntity();
			myPrizeNumberEntity.setUserEntity(userEntity);
			myPrizeNumberEntity.setNumber(0);
			prizeNumberRepository.create(myPrizeNumberEntity);
			prizeNumberEntity = prizeNumberRepository.findByUserEntity(userEntity);
		}
		return prizeNumberEntity;

	}

	@Override
	public List<PrizeEntity> getPrizeAll() {
		// TODO Auto-generated method stub
		return prizeRepository.findAll();
	}

	@Override
	public boolean hasStockPrize(UserEntity userEntity) {
		// TODO Auto-generated method stub
		return checkStockNumber(userEntity);
	}

	@Override
	public PrizeResultEntity getLatestPrize() {
		// TODO Auto-generated method stub
		PrizeResultEntity prizeResultEntity = prizeResultRepository.getLatestRealPrizeResult();
		// return prizeResultEntity.getPrizeEntity();
		return prizeResultEntity;
	}

	@Override
	public int getShareEveryDayNumber(UserEntity userEntity) {
		// TODO Auto-generated method stub
		String nowString = DateUtil.getStandardDate(new Date());
		List<PrizeRecordEntity> list = prizeRecordRepository.getListByUserAndFlag(userEntity, nowString);
		int total = 0;
		for (PrizeRecordEntity prizeRecordEntity : list) {
			if (PrizeUtil.PRIZE_SHARE_TYPE.endsWith(prizeRecordEntity.getReason())) {
				total += 1;
			}
		}
		return total;
	}

	@Override
	@Transactional
	public PrizeEntity getMyPrize(UserEntity userEntity) {
		// TODO Auto-generated method stub
		// check user available
		// 1.检查有效的投票数，2.投票数量-1 3.插入抽奖记录
		if (checkAvailable(userEntity)) {
			Date nowDate = new Date();
			PrizeEntity prizeEntity = getRandomPrize();
			PrizeResultEntity prizeResultEntity = new PrizeResultEntity();
			prizeResultEntity.setUserEntity(userEntity);
			prizeResultEntity.setPrizeEntity(prizeEntity);
			prizeResultEntity.setRecordTime(nowDate);
			// 1.处理额外投票券
			// addTicketNumber(userEntity, prizeEntity, nowDate); // 20210818 没有额外投票权
			// 2.添加减少记录
			addReduceRecordPrize(userEntity);
			// 3.增加中奖人数
			prizeRepository.addPrizeWinningNumber(prizeEntity);
			// 4.用户抽奖次数减少
			prizeNumberRepository.reduceNumber(userEntity);
			// 5.保存用户抽奖结果
			prizeResultRepository.save(prizeResultEntity);
			return prizeEntity;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public void selectStockPrize(UserEntity userEntity) {
		// TODO Auto-generated method stub
		if (checkStockNumber(userEntity)) {
			return;
		} else {
			Date nowDate = new Date();
			addPrizeNumber(userEntity, true, "3", 1);
			userTicketService.addUserTicketNumber(userEntity, 500, TicketUtil.TOUPIAOSELECT, nowDate);
		}
	}

	private void addTicketNumber(UserEntity userEntity, PrizeEntity prizeEntity, Date date) {
		if (prizeEntity.getImage().equals("award6")) {
			// 额外投票券
			PrizeUtil prizeUtil = PrizeUtil.getInstance();
			int ticketNumber = prizeUtil.getRandomTicket();
			System.out.println("ticketNumber: " + ticketNumber);
			userTicketService.addUserTicketNumber(userEntity, ticketNumber, TicketUtil.PRIZENTICKET, date);
		}
	}

	@Override
	public List<PrizeResultEntity> getMyPrizeEntities(UserEntity userEntity) {
		// TODO Auto-generated method stub
		List<PrizeResultEntity> list = prizeResultRepository.findByUserEntityOrderByRecordTimeDesc(userEntity);
		List<PrizeResultEntity> myRealPrizeResultEntity = new ArrayList<PrizeResultEntity>();
		for (PrizeResultEntity prizeResultEntity : list) {
			PrizeEntity prizeEntity = prizeResultEntity.getPrizeEntity();
			if (prizeEntity.isType()) {
				// System.out.println(DateUtil.getPrizeStandardDate(prizeResultEntity.getRecordTime()).toString());
				// prizeResultEntity.setRecordTime(DateUtil.getPrizeStandardDate(prizeResultEntity.getRecordTime()));
				myRealPrizeResultEntity.add(prizeResultEntity);
			}
		}
		return myRealPrizeResultEntity;
	}

	public PrizeEntity getRandomPrize() {
		// 计算中奖项，返回中奖项
		ParamEntity paramEntity = paramRepository.getValueByCode(ParamRepositoryImpl.LCJCOMPUSER);
		PrizeUtil prizeUtil = PrizeUtil.getInstance();
		String totalString = paramEntity.getValue();
		List<PrizeEntity> prizeList = getPrizeAll();
		if (totalString != null) {
			try {
				int total = Integer.parseInt(totalString);
				if (total > 0) {
					// 得到总数
					for (PrizeEntity prizeEntity : prizeList) {
						// 可用的奖品次数 = 总数 - 中奖次数
						if (prizeEntity.isType()) {
							int amount = prizeEntity.getAmount();
							int winningNumber = prizeEntity.getWinningNumber();
							int availableNumber = amount - winningNumber;
							if (availableNumber > 0 && prizeUtil.testChoice(availableNumber, total)) {
								return prizeEntity;
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// 判断返回 谢谢参与逻辑
		return prizeUtil.getXieXieEntity(prizeList);
	}

	public boolean checkAvailable(UserEntity userEntity) {
		int number = getAvailableNumber(userEntity);
		if (number >= 1) {
			return true;
		} else {
			return false;
		}
	}

	public void reducePrizeNumber(PrizeEntity prizeEntity) {
		if (prizeEntity.isType()) {
			prizeRepository.reducePrizeNumber(prizeEntity);
		}
	}

	@Override
	public int getAvailableNumber(UserEntity userEntity) {
		// TODO Auto-generated method stub
		PrizeNumberEntity prizeNumber = getPrizeNumberEntity(userEntity);
		return prizeNumber.getNumber();
	}

	@Override
	@Transactional
	public void addPrizeNumber(UserEntity userEntity, Boolean type, String reason, int number) {
		// TODO Auto-generated method stub
		// type 0 表示减少 1 表示增加
		// reason 1. 表示每日登陆。 2. 表示分享
		PrizeNumberEntity prizeNumberEntity = getPrizeNumberEntity(userEntity);
		prizeNumberRepository.addNumber(prizeNumberEntity);
		addPrizeRecord(userEntity, type, reason);
	}

	@Override
	@Transactional
	public boolean sharePutPrizeNumber(UserEntity userEntity) {
		// TODO Auto-generated method stub
		if (!checkUserShareStatus(userEntity)) {
			return false;
		}
		PrizeNumberEntity prizeNumberEntity = getPrizeNumberEntity(userEntity);
		Date nowDate = new Date();
		// 分享获得抽奖次数
		prizeNumberRepository.addNumber(prizeNumberEntity);
		addPrizeRecord(userEntity, true, PrizeUtil.PRIZE_SHARE_TYPE);
		// 分享获得投票数量
		// userTicketService.addUserTicketNumber(userEntity, 200, TicketUtil.ACTIVITYSHARETICKET, nowDate); // 20210818 分享没有投票权
		return true;
	}

	private void addPrizeRecord(UserEntity userEntity, boolean type, String reason) {
		String nowString = DateUtil.getStandardDate(new Date());
		PrizeRecordEntity prizeRecordEntity = new PrizeRecordEntity();
		prizeRecordEntity.setUserEntity(userEntity);
		prizeRecordEntity.setType(type);
		prizeRecordEntity.setReason(reason);
		prizeRecordEntity.setNumber(1);
		prizeRecordEntity.setDateFlag(nowString);
		prizeRecordEntity.setRecordTime(new Date());
		prizeRecordRepository.add(prizeRecordEntity);
	}

	public void addReduceRecordPrize(UserEntity userEntity) {
		String nowString = DateUtil.getStandardDate(new Date());
		PrizeRecordEntity prizeRecordEntity = new PrizeRecordEntity();
		prizeRecordEntity.setUserEntity(userEntity);
		prizeRecordEntity.setType(false);
		prizeRecordEntity.setReason(PrizeUtil.PRIZE_REDUCE_TYPE);
		prizeRecordEntity.setNumber(1);
		prizeRecordEntity.setDateFlag(nowString);
		prizeRecordEntity.setRecordTime(new Date());
		prizeRecordRepository.add(prizeRecordEntity);
	}

	public boolean checkUserShareStatus(UserEntity userEntity) {
		String nowString = DateUtil.getStandardDate(new Date());
		List<PrizeRecordEntity> list = prizeRecordRepository.getListByUserAndFlag(userEntity, nowString);
		int total = 0;
		for (PrizeRecordEntity prizeRecordEntity : list) {
			if (PrizeUtil.PRIZE_SHARE_TYPE.endsWith(prizeRecordEntity.getReason())) {
				total += 1;
			}
		}
		if (total >= 300) {
			return false;
		}
		return true;
	}

	public boolean checkStockNumber(UserEntity userEntity) {
		String nowString = DateUtil.getStandardDate(new Date());
		List<UserTicketRecordEntity> list = userTicketRecordRepository
				.findByUserEntityAndDateFlagAndVotesSource(userEntity, nowString, TicketUtil.TOUPIAOSELECT);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

}
