package com.xsdzq.mm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.entity.PrizeResultEntity;
import com.xsdzq.mm.entity.UserEntity;

public class PrizeUtil {

	public static String PRIZE_LOGIN_TYPE = "11";
	public static String PRIZE_SHARE_TYPE = "12";
	public static String PRIZE_REDUCE_TYPE = "01";

	private Random r = null;

	private PrizeUtil() {
		r = new Random();
	}

	private static PrizeUtil singlePrizeUtil = null;

	public synchronized static PrizeUtil getInstance() {
		if (singlePrizeUtil == null) {
			singlePrizeUtil = new PrizeUtil();
		}
		return singlePrizeUtil;
	}

	/*
	 * params （int prizeAmount, int scope） prizeAmount 作为分子，scope作为分母。 随机数在分子中即为中奖
	 */

	public boolean testChoice(int prizeAmount, int scope) {
		// 得到随机数，在[1,scope+)
		int random = r.nextInt(scope) + 1;
		System.out.print(random);
		if (random <= prizeAmount) {
			return true;
		} else {
			return false;
		}
	}

	public PrizeEntity getXieXieEntity(List<PrizeEntity> prizeList) {
		List<PrizeEntity> xiexieEntities = new ArrayList<PrizeEntity>();
		for (PrizeEntity prizeEntity : prizeList) {
			if (!prizeEntity.isType()) {
				xiexieEntities.add(prizeEntity);
			}
		}
		int len = xiexieEntities.size();
		if (len > 0) {
			int random = r.nextInt(len);
			return xiexieEntities.get(random);
		}
		return null;
	}

	// [0,n)
	public int getRandomTicket() {
		int numberArray[] = { 100, 200, 300, 500 };
		int random = r.nextInt(4);
		System.out.println("random: " + random);
		return numberArray[random];
	}

	public PrizeResultEntity getSecretPrizeResultEntity(PrizeResultEntity prizeResultEntity) {
		PrizeResultEntity result = new PrizeResultEntity();

		UserEntity userEntity = new UserEntity();
		userEntity.setClientId(getSecretString(prizeResultEntity.getUserEntity().getClientId()));
		result.setUserEntity(userEntity);

		PrizeEntity prizeEntity = new PrizeEntity();
		prizeEntity.setName(prizeResultEntity.getPrizeEntity().getName());
		result.setPrizeEntity(prizeEntity);
		return result;
	}

	public String getSecretString(String no) {
		if (no.length() > 6) {
			return getStarString(no, 1, 5);
		}
		return no;
	}

	private static String getStarString(String content, int begin, int end) {

		if (begin >= content.length() || begin < 0) {
			return content;
		}
		if (end >= content.length() || end < 0) {
			return content;
		}
		if (begin >= end) {
			return content;
		}
		String starStr = "";
		for (int i = begin; i < end; i++) {
			starStr = starStr + "*";
		}
		return content.substring(0, begin) + starStr + content.substring(end, content.length());

	}

}
