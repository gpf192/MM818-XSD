package com.xsdzq.mm.service;

import java.util.List;

import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketEntity;
import com.xsdzq.mm.entity.UserTicketRecordEntity;
import com.xsdzq.mm.entity.UserTicketTotalViewEntity;

public interface UserTicketService {

	int getUserTicket(UserEntity userEntity);

	int countVoteNumber();

	int countUserVoteNumber(UserEntity userEntity);

	List<UserTicketRecordEntity> getUserRecord(UserEntity userEntity, int pageNumber, int pageSize);

	void userVoteEmp(UserEntity userEntity, String empId, int number);

	UserTicketEntity getUserTicketEntity(UserEntity userEntity);

	void addUserTicketNumber(UserEntity userEntity, int number, String reason);

	void reduceUserTickeNumber(UserEntity userEntity, int number, String reason);

	List<UserTicketTotalViewEntity> getUserTicketSort(int pageNumber, int pageSize);

}
