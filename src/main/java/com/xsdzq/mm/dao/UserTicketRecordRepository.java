package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketRecordEntity;

public interface UserTicketRecordRepository extends JpaRepository<UserTicketRecordEntity, Long> ,UserTicketRecordWrapper{

	List<UserTicketRecordEntity> findByUserEntityAndDateFlag(UserEntity userEntity, String dateFlag);

	List<UserTicketRecordEntity> findByUserEntityOrderByGainTimeDesc(UserEntity userEntity);
	
	//定时任务 查询 登录 分享 抽奖 的用户记录
	List<UserTicketRecordEntity> findByVotesSourceAndDateFlag(String votesSource, String dateFlag);

}
