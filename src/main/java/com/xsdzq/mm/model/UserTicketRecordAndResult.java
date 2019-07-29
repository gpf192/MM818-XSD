package com.xsdzq.mm.model;

import com.xsdzq.mm.entity.UserTicketRecordEntity;
import com.xsdzq.mm.entity.UserVoteEmpResultEntity;

public class UserTicketRecordAndResult {

	private UserTicketRecordEntity userTicketRecordEntity;
	private UserVoteEmpResultEntity userVoteEmpResultEntity;

	public UserTicketRecordAndResult() {
		super();
	}

	public UserTicketRecordAndResult(UserTicketRecordEntity userTicketRecordEntity,
			UserVoteEmpResultEntity userVoteEmpResultEntity) {
		super();
		this.userTicketRecordEntity = userTicketRecordEntity;
		this.userVoteEmpResultEntity = userVoteEmpResultEntity;
	}

	public UserTicketRecordEntity getUserTicketRecordEntity() {
		return userTicketRecordEntity;
	}

	public void setUserTicketRecordEntity(UserTicketRecordEntity userTicketRecordEntity) {
		this.userTicketRecordEntity = userTicketRecordEntity;
	}

	public UserVoteEmpResultEntity getUserVoteEmpResultEntity() {
		return userVoteEmpResultEntity;
	}

	public void setUserVoteEmpResultEntity(UserVoteEmpResultEntity userVoteEmpResultEntity) {
		this.userVoteEmpResultEntity = userVoteEmpResultEntity;
	}

}
