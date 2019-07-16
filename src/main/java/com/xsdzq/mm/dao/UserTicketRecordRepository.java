package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketRecordEntity;

public interface UserTicketRecordRepository extends JpaRepository<UserTicketRecordEntity, Long> {

	List<UserTicketRecordEntity> findByUserEntityAndDateFlag(UserEntity userEntity, String dateFlag);

	List<UserTicketRecordEntity> findByUserEntity(UserEntity userEntity);

}