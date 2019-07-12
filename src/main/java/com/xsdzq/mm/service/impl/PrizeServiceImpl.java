package com.xsdzq.mm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.ParamRepository;
import com.xsdzq.mm.dao.PrizeNumberRepository;
import com.xsdzq.mm.dao.PrizeRecordRepository;
import com.xsdzq.mm.dao.PrizeRepository;
import com.xsdzq.mm.dao.PrizeResultRepository;
import com.xsdzq.mm.dao.impl.ParamRepositoryImpl;
import com.xsdzq.mm.entity.ParamEntity;
import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.entity.PrizeNumberEntity;
import com.xsdzq.mm.entity.PrizeRecordEntity;
import com.xsdzq.mm.entity.PrizeResultEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.service.PrizeService;
import com.xsdzq.mm.util.DateUtil;
import com.xsdzq.mm.util.PrizeUtil;

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


	@Override
	public List<PrizeEntity> getPrizeAll() {
		// TODO Auto-generated method stub
		return prizeRepository.findAll();
	}
	
	@Override
	public PrizeResultEntity getLatestPrize() {
		// TODO Auto-generated method stub
		PrizeResultEntity prizeResultEntity = prizeResultRepository.getLatestRealPrizeResult();
		//return prizeResultEntity.getPrizeEntity();
		return prizeResultEntity;
	}

	@Override
	@Transactional
	public PrizeEntity getMyPrize(UserEntity userEntity) {
		// TODO Auto-generated method stub
		// check user available
		// 1.检查有效的投票数，2.投票数量-1 3.插入抽奖记录
		if (checkAvailable(userEntity)) {
			PrizeEntity prizeEntity = getRandomPrize();
			PrizeResultEntity prizeResultEntity = new PrizeResultEntity();
			prizeResultEntity.setUserEntity(userEntity);
			prizeResultEntity.setPrizeEntity(prizeEntity);
			prizeResultEntity.setRecordTime(new Date());
			addReduceRecordPrize(userEntity);
			reducePrizeNumber(prizeEntity);
			prizeNumberRepository.reduceNumber(userEntity);
			prizeResultRepository.save(prizeResultEntity);
			return prizeEntity;
		} else {
			return null;
		}
	}

	@Override
	public List<PrizeEntity> getMyPrizeEntities(UserEntity userEntity) {
		// TODO Auto-generated method stub
		List<PrizeResultEntity> list = prizeResultRepository.findByUserEntity(userEntity);
		List<PrizeEntity> prizeEntities = new ArrayList<PrizeEntity>();
		for (PrizeResultEntity prizeResultEntity : list) {
			PrizeEntity prizeEntity = prizeResultEntity.getPrizeEntity();
			if (prizeEntity.isType()) {
				prizeEntities.add(prizeEntity);
			}
		}
		return prizeEntities;
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
						System.out.println(prizeEntity.toString());
						// String amountString = prizeEntity.getAmount();
						int amount = prizeEntity.getAmount();
						if (prizeUtil.testChoice(amount, total)) {
							return prizeEntity;
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
		System.out.println(prizeEntity.toString());
		if (prizeEntity.isType()) {
			prizeRepository.reducePrizeNumber(prizeEntity);
		}
	}

	@Override
	public int getAvailableNumber(UserEntity userEntity) {
		// TODO Auto-generated method stub
		PrizeNumberEntity prizeNumber = prizeNumberRepository.findByUserEntity(userEntity);
		return prizeNumber.getNumber();
	}

	@Override
	@Transactional
	public void addPrizeNumber(UserEntity userEntity, Boolean type, String reason, int number) {
		// TODO Auto-generated method stub
		// type 0 表示减少 1 表示增加
		// reason 1. 表示每日登陆。 2. 表示分享
		String nowString = DateUtil.getStandardDate(new Date());
		System.out.println(nowString);
		PrizeRecordEntity prizeRecordEntity = new PrizeRecordEntity();
		prizeRecordEntity.setUserEntity(userEntity);
		prizeRecordEntity.setType(type);
		prizeRecordEntity.setReason(reason);
		prizeRecordEntity.setNumber(number);
		prizeRecordEntity.setDateFlag(nowString);
		prizeRecordEntity.setRecordTime(new Date());
		prizeRecordRepository.add(prizeRecordEntity);
		prizeNumberRepository.addNumber(userEntity);
	}

	@Override
	@Transactional
	public boolean sharePutPrizeNumber(UserEntity userEntity) {
		// TODO Auto-generated method stub
		if (!checkUserShareStatus(userEntity)) {
			return false;
		}
		String nowString = DateUtil.getStandardDate(new Date());
		PrizeRecordEntity prizeRecordEntity = new PrizeRecordEntity();
		prizeRecordEntity.setUserEntity(userEntity);
		prizeRecordEntity.setType(true);
		prizeRecordEntity.setReason(PrizeUtil.PRIZE_SHARE_TYPE);
		prizeRecordEntity.setNumber(1);
		prizeRecordEntity.setDateFlag(nowString);
		prizeRecordEntity.setRecordTime(new Date());
		prizeRecordRepository.add(prizeRecordEntity);
		prizeNumberRepository.addNumber(userEntity);
		return true;
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
		if (total >= 5) {
			return false;
		}
		return true;
	}

}
