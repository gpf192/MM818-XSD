package com.xsdzq.mm.service;

import java.util.List;

import com.xsdzq.mm.entity.EmpTicketEntity;

public interface EmpService {

	List<EmpTicketEntity> findByEmpNameLike(String name);

}
