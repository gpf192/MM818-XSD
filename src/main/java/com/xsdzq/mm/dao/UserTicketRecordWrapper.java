package com.xsdzq.mm.dao;

import com.xsdzq.mm.entity.UserTicketRecordEntity;

public interface UserTicketRecordWrapper {
	public void add(UserTicketRecordEntity userTicketRecordEntity, int number);

	public void reduce(UserTicketRecordEntity userTicketRecordEntity, int number);
}
