package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.PrizeResultEntity;
import com.xsdzq.mm.entity.UserEntity;

public interface PrizeResultRepository extends JpaRepository<PrizeResultEntity, Long>, PrizeResultWrapper {

	List<PrizeResultEntity> findByUserEntityOrderByRecordTimeDesc(UserEntity userEntity);

}
