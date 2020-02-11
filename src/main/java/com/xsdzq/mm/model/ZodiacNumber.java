package com.xsdzq.mm.model;

import com.xsdzq.mm.entity.PrizeEntity;

public class ZodiacNumber {
	private PrizeEntity prizeEntity;
	private int num;

	public ZodiacNumber() {
		super();
	}

	public ZodiacNumber(PrizeEntity prizeEntity, int num) {
		super();
		this.prizeEntity = prizeEntity;
		this.num = num;
	}

	public PrizeEntity getPrizeEntity() {
		return prizeEntity;
	}

	public void setPrizeEntity(PrizeEntity prizeEntity) {
		this.prizeEntity = prizeEntity;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
