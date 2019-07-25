package com.xsdzq.mm.service;

import java.util.List;

import com.xsdzq.mm.entity.EmpEntity;
import com.xsdzq.mm.entity.EmpTicketEntity;

public interface EmpTicketService {

	List<EmpTicketEntity> getEmpTicketEntities(int pageNumber, int pageSize, String divison);

	EmpTicketEntity getEmpTicketEntity(EmpEntity empEntity);

	void addEmpTicketNumber(EmpEntity empEntity, int number, String reason);

	void reduceEmpTickeNumber(EmpEntity empEntity, int number, String reason);
	public void addEmpTicketNumberByJOB(EmpEntity empEntity, int number, String reason);

}
