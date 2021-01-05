package com.xsdzq.mm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.controller.TicketController;
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

	Logger logger = LoggerFactory.getLogger(AwardServiceImpl.class.getName());
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
		for (AwardEntity awardEntity : awardEntities) {
			if (awardEntity.getIndex() == 4) {
				int resultNumber = getAwardResultNumber(awardEntity);
				int total = awardEntity.getImageNumber();
				int residueNumber = total - resultNumber;
				if (residueNumber < 0) {
					residueNumber = 0;
				}
				awardEntity.setImageNumber(residueNumber);
			}
		}
		return awardEntities;
	}

	@Override
	public int getSurplusAwardNumber(AwardEntity awardEntity) {
		// TODO Auto-generated method stub
		AwardEntity myAwardEntity = awardRepository.findById(awardEntity.getId()).get();
		int resultNumber = getAwardResultNumber(myAwardEntity);
		int total = myAwardEntity.getImageNumber();
		int residueNumber = total - resultNumber;
		if (residueNumber < 0) {
			residueNumber = 0;
		}
		return residueNumber;
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
		boolean canChange = checkAwardNumber(awardNumber);
		if (!canChange) {
			return false;
		}
		date = new Date();
		List<ZodiacNumber> zodiacNumbers = prizeService.getMyZodiacNumbers(userEntity);
		// 奖品为手机时，减数量 总数不变
		// if (awardEntity.getIndex() == 4) {
		// // 再次校验奖品数量
		// int qjfNumber = getSurplusAwardNumber(awardEntity);
		// if (qjfNumber <= 0) {
		// return false;
		// }
		// }
		// check 是否可以兑换
		if (checkConvert(userEntity, zodiacNumbers, awardNumber)) {
			// 减卡操作
			reducePrizeCard(userEntity, zodiacNumbers, awardNumber);
			// 兑换成功，增加记录
			AwardResultEntity awardResultEntity = new AwardResultEntity();
			AwardEntity oldAwardEntity = awardRepository.getOne(awardEntity.getId());

			// 已使用数量增加
			oldAwardEntity.setUsedNumber(oldAwardEntity.getUsedNumber() + awardNumber.getNum());
			awardRepository.save(oldAwardEntity);

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
	public boolean checkAwardNumber(AwardNumber awardNumber) {

		AwardEntity currentAwardEntity = awardRepository.findById(awardNumber.getAward().getId()).get();
		if (currentAwardEntity.getAmount() - currentAwardEntity.getUsedNumber() >= awardNumber.getNum()) {
			return true;
		}
		return false;

	}

	@Override
	public int checkMyValue(UserEntity userEntity, AwardNumber awardNumber) {
		// TODO Auto-generated method stub
		// int countNumber =
		return checkMyValueAward(userEntity, awardNumber);
	}

	public int getAwardResultNumber(AwardEntity awardEntity) {
		int total = 0;
		List<AwardResultEntity> awardResultEntities = awardResultRepository.findByAwardEntity(awardEntity);
		for (AwardResultEntity awardResultEntity : awardResultEntities) {
			total += awardResultEntity.getAwardNumber();
		}
		return total;
	}

	@Override
	public AwardEntity getAwardEntity(int index) {
		// TODO Auto-generated method stub
		return awardRepository.findByIndex(index).get(0);
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
		if (myTotal > 4999) {
			return 0;
		}
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
			// 长牛奖逻辑
			int myNiuCardNumber = 0;
			for (ZodiacNumber zodiacNumber : zodiacNumbers) {
				if (zodiacNumber.getPrizeEntity().getIndex() == 1) {
					myNiuCardNumber = zodiacNumber.getNum();
				}
			}
			if (myNiuCardNumber < converNumber) {
				return false; // 牛卡不够
			}
		} else if (awardEntity.getIndex() == 2) {
			// 牪牛奖逻辑
			int myDoubleNiuCardNumber = 0;
			for (ZodiacNumber zodiacNumber : zodiacNumbers) {
				if (zodiacNumber.getPrizeEntity().getIndex() == 1) {
					myDoubleNiuCardNumber = zodiacNumber.getNum();
				}
			}
			if (myDoubleNiuCardNumber < converNumber * 2) {
				return false;
			}
		} else if (awardEntity.getIndex() == 3) {
			// 犇牛奖逻辑
			int myTripleNiuCardNumber = 0;
			for (ZodiacNumber zodiacNumber : zodiacNumbers) {
				if (zodiacNumber.getPrizeEntity().getIndex() == 1) {
					myTripleNiuCardNumber = zodiacNumber.getNum();
				}
			}
			if (myTripleNiuCardNumber < converNumber * 3) {
				return false;
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
			// 长牛奖逻辑逻辑
			ZodiacNumber reduceZodiacNumber = new ZodiacNumber();
			PrizeEntity prizeEntity = prizeRepository.findByIndex(1).get(0);
			reduceZodiacNumber.setPrizeEntity(prizeEntity);
			reduceZodiacNumber.setNum(awardNumber.getNum());
			reducePrizeNumber(userEntity, reduceZodiacNumber);
		} else if (convertAwardEntity.getIndex() == 2) {
			// 牪牛奖逻辑
			ZodiacNumber reduceZodiacNumber = new ZodiacNumber();
			PrizeEntity prizeEntity = prizeRepository.findByIndex(1).get(0);
			reduceZodiacNumber.setPrizeEntity(prizeEntity);
			reduceZodiacNumber.setNum(awardNumber.getNum() * 2);
			reducePrizeNumber(userEntity, reduceZodiacNumber);
		} else if (convertAwardEntity.getIndex() == 3) {
			// 犇牛奖逻辑
			ZodiacNumber reduceNiuZodiacNumber = new ZodiacNumber();
			PrizeEntity niuPrizeEntity = prizeRepository.findByIndex(1).get(0);
			reduceNiuZodiacNumber.setPrizeEntity(niuPrizeEntity);
			reduceNiuZodiacNumber.setNum(awardNumber.getNum() * 3);
			reducePrizeNumber(userEntity, reduceNiuZodiacNumber);
		} else if (convertAwardEntity.getIndex() == 4) {
			// 满堂红奖
			List<PrizeEntity> allPrizeEntities = prizeRepository.findAll();
			for (PrizeEntity prizeEntity : allPrizeEntities) {
				ZodiacNumber reduceZodiacNumber = new ZodiacNumber();
				reduceZodiacNumber.setPrizeEntity(prizeEntity);
				reduceZodiacNumber.setNum(awardNumber.getNum());
				reducePrizeNumber(userEntity, reduceZodiacNumber);
			}
		}
	}

	void reduceAwardNumber(AwardEntity awardEntity) {
		awardEntity.setImageNumber(awardEntity.getImageNumber() - 1);
		awardRepository.saveAndFlush(awardEntity);
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
