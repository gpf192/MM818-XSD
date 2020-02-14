package com.xsdzq.mm.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.PageEventRepository;
import com.xsdzq.mm.entity.PageEventEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.PageEvent;
import com.xsdzq.mm.service.PageEventService;

@Service()
@Transactional(readOnly = true)
public class PageEventServiceImpl implements PageEventService {
	
	@Autowired
	private PageEventRepository pageEventRepository;

	@Override
	@Transactional
	public void savePageEvent(UserEntity userEntity, PageEvent pageEvent) {
		// TODO Auto-generated method stub
		PageEventEntity pageEventEntity = new PageEventEntity();
		pageEventEntity.setUserEntity(userEntity);
		pageEventEntity.setPageEventId(pageEvent.getPageEventId());
		pageEventEntity.setActionEvent(pageEvent.getActionEvent());
		pageEventEntity.setRecordTime(new Date());
		pageEventRepository.save(pageEventEntity);
	}

}
