package com.xsdzq.mm.dao;

import com.xsdzq.mm.entity.EmpTicketEntity;

public interface EmpTicketWrapper {

	public void add(EmpTicketEntity empTicketEntity, int number);

	public void reduce(EmpTicketEntity empTicketEntity, int number);

}
