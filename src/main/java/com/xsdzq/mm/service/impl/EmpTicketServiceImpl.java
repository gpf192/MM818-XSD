package com.xsdzq.mm.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.EmpTicketRecordRepository;
import com.xsdzq.mm.dao.EmpTicketRepository;
import com.xsdzq.mm.entity.EmpEntity;
import com.xsdzq.mm.entity.EmpTicketEntity;
import com.xsdzq.mm.entity.EmpTicketRecordEntity;
import com.xsdzq.mm.service.EmpTicketService;
import com.xsdzq.mm.util.DateUtil;

@Service(value = "empTicketServiceImpl")
@Transactional(readOnly = true)
public class EmpTicketServiceImpl implements EmpTicketService {

	@Autowired
	EmpTicketRepository empTicketRepository;

	@Autowired
	EmpTicketRecordRepository empTicketRecordRepository;

	@Override
	public int countEmpNumber() {
		// TODO Auto-generated method stub
		return (int) empTicketRepository.count();
	}

	@Override
	public int countEmpNumberByDivison(String division) {
		// TODO Auto-generated method stub
		return (int) empTicketRepository.countByEmpEntityDivisionAndEmpEntityEnable(division, 1);
	}

	@Override
	public List<EmpTicketEntity> getEmpTicketEntities(int pageNumber, int pageSize, String divison) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		Page<EmpTicketEntity> empTIcketPage = empTicketRepository
				.findByEmpEntityDivisionAndEmpEntityEnableOrderByNumberDesc(divison, 1, pageRequest);

		List<EmpTicketEntity> empTicketList = empTIcketPage.getContent();
		return empTicketList;
	}

	@Override
	@Transactional
	public EmpTicketEntity getEmpTicketEntity(EmpEntity empEntity) {
		// TODO Auto-generated method stub
		EmpTicketEntity empTicketEntity = empTicketRepository.findByEmpEntity(empEntity);
		if (empTicketEntity == null) {
			EmpTicketEntity eTicketEntity = new EmpTicketEntity();
			eTicketEntity.setEmpEntity(empEntity);
			eTicketEntity.setNumber(0);
			empTicketRepository.save(eTicketEntity);
			empTicketEntity = empTicketRepository.findByEmpEntity(empEntity);
		}
		return empTicketEntity;
	}

	@Override
	@Transactional
	public void addEmpTicketNumber(EmpEntity empEntity, int number, String reason) {
		// TODO Auto-generated method stub
		EmpTicketEntity empTicketEntity = getEmpTicketEntity(empEntity);
		empTicketRepository.add(empTicketEntity, number);
		addEmpTicketRecord(empEntity, true, number, reason);
	}

	@Override
	@Transactional
	public void reduceEmpTickeNumber(EmpEntity empEntity, int number, String reason) {
		// TODO Auto-generated method stub

	}

	// 添加员工票数变化的记录
	private void addEmpTicketRecord(EmpEntity empEntity, boolean type, int number, String votesSource) {
		String nowString = DateUtil.getStandardDate(new Date());
		EmpTicketRecordEntity empTicketRecordEntity = new EmpTicketRecordEntity();
		empTicketRecordEntity.setEmpEntity(empEntity);
		empTicketRecordEntity.setNumber(number);
		empTicketRecordEntity.setType(type);
		empTicketRecordEntity.setVotesSource(votesSource);
		empTicketRecordEntity.setDateFlag(nowString);
		empTicketRecordEntity.setRecordTime(new Date());
		empTicketRecordRepository.save(empTicketRecordEntity);
	}

}
