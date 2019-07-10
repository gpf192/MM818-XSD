package com.xsdzq.mm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.xsdzq.mm.entity.PrizeEntity;

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
}
