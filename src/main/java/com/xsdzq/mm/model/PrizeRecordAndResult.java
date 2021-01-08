package com.xsdzq.mm.model;

import com.xsdzq.mm.entity.PrizeEntity;
import com.xsdzq.mm.entity.PrizeRecordEntity;

public class PrizeRecordAndResult {
	private PrizeEntity prizeEntity;
	private PrizeRecordEntity prizeRecordEntity;

	public PrizeEntity getPrizeEntity() {
		return prizeEntity;
	}

	public void setPrizeEntity(PrizeEntity prizeEntity) {
		this.prizeEntity = prizeEntity;
	}

	public PrizeRecordEntity getPrizeRecordEntity() {
		return prizeRecordEntity;
	}

	public void setPrizeRecordEntity(PrizeRecordEntity prizeRecordEntity) {
		this.prizeRecordEntity = prizeRecordEntity;
	}

}
