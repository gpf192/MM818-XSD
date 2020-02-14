package com.xsdzq.mm.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.AwardRepository;
import com.xsdzq.mm.dao.AwardResultRepository;
import com.xsdzq.mm.dao.PrizeRepository;
import com.xsdzq.mm.dao.PrizeResultRepository;
import com.xsdzq.mm.entity.AwardEntity;
import com.xsdzq.mm.entity.AwardResultEntity;
import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.entity.PrizeResultEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.AwardNumber;
import com.xsdzq.mm.model.ZodiacNumber;
import com.xsdzq.mm.service.AwardService;
import com.xsdzq.mm.service.PrizeService;

@Service(value = "awardServiceImpl")
@Transactional(readOnly = true)
public class AwardServiceImpl implements AwardService {

	@Autowired
	private AwardRepository awardRepository;

	@Autowired
	private PrizeRepository prizeRepository;

	@Autowired
	private AwardResultRepository awardResultRepository;

	@Autowired
	private PrizeResultRepository prizeResultRepository;

	@Autowired
	private PrizeService prizeService;

	private Date date;

	public List<AwardEntity> getConvertAward() {
		// TODO Auto-generated method stub
		List<AwardEntity> awardEntities = awardRepository.findAll();
		return awardEntities;
	}

	@Override
	public List<AwardResultEntity> getAwardResultRecord(UserEntity userEntity) {
		// TODO Auto-generated method stub
		return awardResultRepository.findByUserEntityOrderByRecordTimeDesc(userEntity);
	}

	@Override
	@Transactional
	public boolean convertAward(UserEntity userEntity, AwardNumber awardNumber) {
		// TODO Auto-generated method stub
		AwardEntity awardEntity = awardNumber.getAward();
		int num = awardNumber.getNum();
		date = new Date();
		List<ZodiacNumber> zodiacNumbers = prizeService.getMyZodiacNumbers(userEntity);
		// check 是否可以兑换
		if (checkConvert(userEntity, zodiacNumbers, awardNumber)) {
			// 减卡操作
			reducePrizeCard(userEntity, zodiacNumbers, awardNumber);
			// 兑换成功，增加记录
			AwardResultEntity awardResultEntity = new AwardResultEntity();
			AwardEntity oldAwardEntity = awardRepository.getOne(awardEntity.getId());
			awardResultEntity.setUserEntity(userEntity);
			awardResultEntity.setAwardEntity(oldAwardEntity);
			awardResultEntity.setAwardNumber(num);
			awardResultEntity.setRecordTime(date);
			awardResultRepository.save(awardResultEntity);
			return true;
		}
		return false;
	}

	@Override
	public int checkMyValue(UserEntity userEntity, AwardNumber awardNumber) {
		// TODO Auto-generated method stub
		return checkMyValueAward(userEntity, awardNumber);
	}

	int checkMyValueAward(UserEntity userEntity, AwardNumber awardNumber) {
		List<AwardResultEntity> awardResultEntities = awardResultRepository
				.findByUserEntityOrderByRecordTimeDesc(userEntity);
		// 5000块钱判断
		// 1.计算已经有的价值
		int myTotal = 0;
		for (AwardResultEntity awardResultEntity : awardResultEntities) {
			int value = awardResultEntity.getAwardNumber() * awardResultEntity.getAwardEntity().getAwardValue();
			myTotal += value;
		}
		// 2.计算本次的价值
		int requestTotal = awardNumber.getAward().getAwardValue() * awardNumber.getNum();
		int total = myTotal + requestTotal;
		if (total > 4999) {
			int code = (5000 - myTotal) / awardNumber.getAward().getAwardValue();
			return code;
		}
		return -1;
	}

	boolean checkConvert(UserEntity userEntity, List<ZodiacNumber> zodiacNumbers, AwardNumber awardNumber) {
		if (checkMyValueAward(userEntity, awardNumber) > -1) {
			return false;
		}
		AwardEntity awardEntity = awardNumber.getAward();
		int converNumber = awardNumber.getNum();
		if (awardEntity.getIndex() == 1) {
			// 牛气冲天奖逻辑
			int myNiuCardNumber = 0;
			for (ZodiacNumber zodiacNumber : zodiacNumbers) {
				if (zodiacNumber.getPrizeEntity().getIndex() == 2) {
					myNiuCardNumber = zodiacNumber.getNum();
				}
			}
			if (myNiuCardNumber < converNumber) {
				return false; // 牛卡不够
			}
		} else if (awardEntity.getIndex() == 2) {
			// 鼠一鼠二奖逻辑
			int myShuCardNumber = 0;
			for (ZodiacNumber zodiacNumber : zodiacNumbers) {
				if (zodiacNumber.getPrizeEntity().getIndex() == 1) {
					myShuCardNumber = zodiacNumber.getNum();
				}
			}
			if (myShuCardNumber < converNumber * 2) {
				return false; // 鼠卡不够
			}
		} else if (awardEntity.getIndex() == 3) {
			// 鼠你最牛奖逻辑
			int myNiuCardNumber = 0;
			int myShuCardNumber = 0;
			for (ZodiacNumber zodiacNumber : zodiacNumbers) {
				if (zodiacNumber.getPrizeEntity().getIndex() == 1) {
					myShuCardNumber = zodiacNumber.getNum();
				}
				if (zodiacNumber.getPrizeEntity().getIndex() == 2) {
					myNiuCardNumber = zodiacNumber.getNum();
				}
			}
			if (myShuCardNumber < converNumber || myNiuCardNumber < converNumber) {
				return false; // 鼠卡不够
			}
		} else {
			for (ZodiacNumber zodiacNumber : zodiacNumbers) {
				if (zodiacNumber.getNum() < converNumber) {
					return false; // 有可能某个卡不够
				}
			}
		}
		return true;
	}

	void reducePrizeCard(UserEntity userEntity, List<ZodiacNumber> zodiacNumbers, AwardNumber awardNumber) {
		AwardEntity convertAwardEntity = awardNumber.getAward();
		if (convertAwardEntity.getIndex() == 1) {
			// 牛气冲天奖逻辑
			ZodiacNumber reduceZodiacNumber = new ZodiacNumber();
			PrizeEntity prizeEntity = prizeRepository.findByIndex(2).get(0);
			reduceZodiacNumber.setPrizeEntity(prizeEntity);
			reduceZodiacNumber.setNum(awardNumber.getNum());
			reducePrizeNumber(userEntity, reduceZodiacNumber);
		} else if (convertAwardEntity.getIndex() == 2) {
			// 鼠一鼠二奖逻辑
			ZodiacNumber reduceZodiacNumber = new ZodiacNumber();
			PrizeEntity prizeEntity = prizeRepository.findByIndex(1).get(0);
			reduceZodiacNumber.setPrizeEntity(prizeEntity);
			reduceZodiacNumber.setNum(awardNumber.getNum() * 2);
			reducePrizeNumber(userEntity, reduceZodiacNumber);
		} else if (convertAwardEntity.getIndex() == 3) {
			// 鼠你最牛奖逻辑
			ZodiacNumber reduceShuZodiacNumber = new ZodiacNumber();
			ZodiacNumber reduceNiuZodiacNumber = new ZodiacNumber();
			PrizeEntity niuPrizeEntity = prizeRepository.findByIndex(2).get(0);
			PrizeEntity shuPrizeEntity = prizeRepository.findByIndex(2).get(0);
			reduceNiuZodiacNumber.setPrizeEntity(niuPrizeEntity);
			reduceNiuZodiacNumber.setNum(awardNumber.getNum());
			reduceNiuZodiacNumber.setPrizeEntity(shuPrizeEntity);
			reduceNiuZodiacNumber.setNum(awardNumber.getNum());
			reducePrizeNumber(userEntity, reduceShuZodiacNumber);
			reducePrizeNumber(userEntity, reduceNiuZodiacNumber);
		} else if (convertAwardEntity.getIndex() == 4) {
			// 全家福奖逻辑
			List<PrizeEntity> allPrizeEntities = prizeRepository.findAll();
			for (PrizeEntity prizeEntity : allPrizeEntities) {
				ZodiacNumber reduceZodiacNumber = new ZodiacNumber();
				reduceZodiacNumber.setPrizeEntity(prizeEntity);
				reduceZodiacNumber.setNum(awardNumber.getNum());
				reducePrizeNumber(userEntity, reduceZodiacNumber);
			}
		}
	}

	void reducePrizeNumber(UserEntity userEntity, ZodiacNumber zodiacNumber) {
		PrizeResultEntity prizeResultEntity = new PrizeResultEntity();
		prizeResultEntity.setUserEntity(userEntity);
		prizeResultEntity.setPrizeEntity(zodiacNumber.getPrizeEntity());
		prizeResultEntity.setRecordTime(date);
		prizeResultEntity.setNumber(zodiacNumber.getNum());
		prizeResultEntity.setType(false);
		prizeResultRepository.save(prizeResultEntity);
	}

}
