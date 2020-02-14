package com.xsdzq.mm.service;

import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.model.PageEvent;

public interface PageEventService {
	
	void savePageEvent(UserEntity userEntity, PageEvent pageEvent);

}
