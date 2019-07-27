package com.xsdzq.mm.service;

import java.util.Date;
import java.util.List;

import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketEntity;
import com.xsdzq.mm.entity.UserTicketRecordEntity;
import com.xsdzq.mm.entity.UserTicketTotalViewEntity;
import com.xsdzq.mm.entity.UserVoteEmpResultEntity;

public interface UserTicketService {

	int getUserTicket(UserEntity userEntity);

	int countVoteNumber();

	int countUserVoteNumber(UserEntity userEntity);

	UserVoteEmpResultEntity getUserVoteEmpResultEntity(UserEntity userEntity, String gainTime);

	List<UserTicketRecordEntity> getUserRecord(UserEntity userEntity, int pageNumber, int pageSize);

	void userVoteEmp(UserEntity userEntity, String empId, int number);

	UserTicketEntity getUserTicketEntity(UserEntity userEntity);

	void addUserTicketNumber(UserEntity userEntity, int number, String reason, Date date);

	void reduceUserTickeNumber(UserEntity userEntity, int number, String reason, Date dateÏ);

	List<UserTicketTotalViewEntity> getUserTicketSort(int pageNumber, int pageSize);

}
