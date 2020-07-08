package com.xsdzq.mm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.DzhggRepository;
import com.xsdzq.mm.entity.DzhggEntity;
import com.xsdzq.mm.service.DzhggService;

@Service()
@Transactional(readOnly = true)
public class DzhggServiceImpl implements DzhggService{
	@Autowired
	private DzhggRepository dzhggRepository;

	@Override
	@Transactional
	public void addEntity(DzhggEntity d) {
		// TODO Auto-generated method stub
		DzhggEntity entity = null;
		entity = dzhggRepository.findByActivityAndPhone(d.getActivity(), d.getPhone());
		if(entity == null) {
			dzhggRepository.save(d);
		}
	}
}
