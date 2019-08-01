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
	//查询之前是否的得过票
	List<UserTicketRecordEntity> findByVotesSourceAndUserEntity_clientId(String votesSource, String clientId);
	//查看用户当天产品交易是否已经通过job获得票
	List<UserTicketRecordEntity> findByVotesSourceAndUserEntity_clientIdAndDateFlag(String votesSource, String clientId, String dateFlag);


}
