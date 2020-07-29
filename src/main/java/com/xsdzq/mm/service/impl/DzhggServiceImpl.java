package com.xsdzq.mm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.DzhActivityRepository;
import com.xsdzq.mm.dao.DzhggRepository;
import com.xsdzq.mm.entity.DzhActivityEntity;
import com.xsdzq.mm.entity.DzhggEntity;
import com.xsdzq.mm.service.DzhggService;

@Service(value = "dzhggServiceImpl")
@Transactional(readOnly = true)
public class DzhggServiceImpl implements DzhggService{
	@Autowired
	private DzhggRepository dzhggRepository;
	
	@Autowired
	private DzhActivityRepository dzhActivityRepository;

	@Override
	@Transactional
	public int addEntity(DzhggEntity d) {
		// TODO Auto-generated method stub
		DzhggEntity entity = null;
		DzhActivityEntity daEntity = null;
		//插入活动名称
		daEntity = dzhActivityRepository.findByName(d.getActivity());
		if(daEntity == null ) {
			DzhActivityEntity e = new DzhActivityEntity(); 
			e.setName(d.getActivity());
			dzhActivityRepository.save(e);
		}
		entity = dzhggRepository.findByActivityAndPhone(d.getActivity(), d.getPhone());
		if(entity == null) {
			dzhggRepository.save(d);
			return 0;
		}else {
			return 1;
		}
	}
}
