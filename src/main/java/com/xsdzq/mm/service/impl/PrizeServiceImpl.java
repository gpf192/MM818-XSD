package com.xsdzq.mm.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.ParamRepository;
import com.xsdzq.mm.dao.PrizeNumberRepository;
import com.xsdzq.mm.dao.PrizeRecordRepository;
import com.xsdzq.mm.dao.PrizeRepository;
import com.xsdzq.mm.dao.impl.ParamRepositoryImpl;
import com.xsdzq.mm.entity.ParamEntity;
import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.entity.PrizeNumberEntity;
import com.xsdzq.mm.entity.PrizeRecordEntity;
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

	@Override
	public List<PrizeEntity> getPrizeAll() {
		// TODO Auto-generated method stub
		return prizeRepository.findAll();
	}

	@Override
	public PrizeEntity getMyPrize() {
		// TODO Auto-generated method stub
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
						String amountString = prizeEntity.getAmount();
						int amount = Integer.parseInt(amountString);
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
