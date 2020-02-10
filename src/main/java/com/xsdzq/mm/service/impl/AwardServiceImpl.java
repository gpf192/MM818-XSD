package com.xsdzq.mm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.AwardRepository;
import com.xsdzq.mm.dao.AwardResultRepository;
import com.xsdzq.mm.entity.AwardEntity;
import com.xsdzq.mm.service.AwardService;

@Service(value = "awardServiceImpl")
@Transactional(readOnly = true)
public class AwardServiceImpl implements AwardService{
	
	@Autowired
	private AwardRepository awardRepository;
	
	@Autowired
	private AwardResultRepository awardResultRepository;

	public List<AwardEntity> getConvertAward() {
		// TODO Auto-generated method stub
		List<AwardEntity> awardEntities = awardRepository.findAll();
		return awardEntities;

	}

}
