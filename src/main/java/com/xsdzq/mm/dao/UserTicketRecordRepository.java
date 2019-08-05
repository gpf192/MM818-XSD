package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketRecordEntity;

public interface UserTicketRecordRepository extends JpaRepository<UserTicketRecordEntity, Long> {

	List<UserTicketRecordEntity> findByUserEntityAndDateFlag(UserEntity userEntity, String dateFlag);

	long countByUserEntity(UserEntity userEntity);

	List<UserTicketRecordEntity> findByUserEntityAndTypeAndVotesSource(UserEntity userEntity, boolean type,
			String votesSource);

	Page<UserTicketRecordEntity> findByUserEntityOrderByGainTimeDescType(UserEntity userEntity, Pageable pageable);

}
