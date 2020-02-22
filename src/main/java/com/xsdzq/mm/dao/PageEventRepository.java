package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.PageEventEntity;
import com.xsdzq.mm.entity.UserEntity;

public interface PageEventRepository extends JpaRepository<PageEventEntity, Long> {
	List<PageEventEntity> findByUserEntityAndPageEventId(UserEntity user,String pageEventId);
}
