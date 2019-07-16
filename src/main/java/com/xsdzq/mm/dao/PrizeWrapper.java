package com.xsdzq.mm.dao;

import com.xsdzq.mm.entity.PrizeEntity;

public interface PrizeWrapper {

	public void reducePrizeNumber(PrizeEntity prizeEntity);

	public void addPrizeWinningNumber(PrizeEntity prizeEntity);

}
