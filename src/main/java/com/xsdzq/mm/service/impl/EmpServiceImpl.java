package com.xsdzq.mm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.EmpTicketRepository;
import com.xsdzq.mm.entity.EmpTicketEntity;
import com.xsdzq.mm.service.EmpService;

@Service(value = "empServiceImpl")
@Transactional(readOnly = true)
public class EmpServiceImpl implements EmpService {

	@Autowired
	private EmpTicketRepository empTicketRepository;

	@Override
	public List<EmpTicketEntity> findByEmpNameLike(String name) {
		// TODO Auto-generated method stub
		String queryName = "%" + name + "%";
		List<EmpTicketEntity> empTicketEntities = empTicketRepository
				.findByEmpEntityEmpNameLikeAndEmpEntityEnable(queryName, 1);
		return empTicketEntities;
	}

}
