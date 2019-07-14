package com.xsdzq.mm.dao;

import com.xsdzq.mm.entity.UserTicketEntity;

public interface UserTicketWrapper {

	public void add(UserTicketEntity userTicketEntity, int number);

	public void reduce(UserTicketEntity userTicketEntity, int number);

}
