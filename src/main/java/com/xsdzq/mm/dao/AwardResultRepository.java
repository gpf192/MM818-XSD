package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.AwardResultEntity;
import com.xsdzq.mm.entity.UserEntity;

public interface AwardResultRepository extends JpaRepository<AwardResultEntity, Long> {
	
	List<AwardResultEntity> findByUserEntityOrderByRecordTimeDesc(UserEntity userEntity);

}
