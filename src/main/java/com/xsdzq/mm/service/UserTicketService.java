package com.xsdzq.mm.service;

import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketEntity;

public interface UserTicketService {

	int getUserTicket(UserEntity userEntity);

	void userVoteEmp(UserEntity userEntity, String empId, int number);

	UserTicketEntity getUserTicketEntity(UserEntity userEntity);

	void addUserTicketNumber(UserEntity userEntity, int number, String reason);

	void reduceUserTickeNumber(UserEntity userEntity, int number, String reason);

}
