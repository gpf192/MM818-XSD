package com.xsdzq.mm.service;

import java.util.List;

import com.xsdzq.mm.entity.OpenAccountEntity;
import com.xsdzq.mm.entity.ParamEntity;
import com.xsdzq.mm.entity.SignInvestViewEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketRecordEntity;
import com.xsdzq.mm.model.ActivityNumber;
import com.xsdzq.mm.model.User;

public interface UserService {

	ActivityNumber login(User user);

	User getUserById(Long id);
	
	UserEntity getUserByClientId(String clientId);

	void setUserInfo(UserEntity userEntity);

	User getUserById(String id);

	User findByClientId(String clientId);
	//
	void addTicketByJobWithEmpId(String clientId, String clientName, String empId, int num, String reason);
	void addTicketByJob(String clientId, String clientName, int num, String reason);
	
	
	List<OpenAccountEntity> findByOpenDate(int preDay);
	int countByOpenDateLessThanEqualAndOpenDateGreaterThanEqualAndClientId(int endDate, int beginDate, String clientId);
	
	public ParamEntity getValueByCode(String code);
	
	List<UserTicketRecordEntity> findByVotesSourceAndUserEntity_clientId(String votesSource, String clientId);
	
	List<SignInvestViewEntity>findByserviceTypeAndStatusAndEffectiveDate(int serviceType, String status, String effectiveDate);
	List<UserTicketRecordEntity> findByVotesSourceAndUserEntity_clientIdAndDateFlag(String votesSource, String clientId, String dateFlag);


}
