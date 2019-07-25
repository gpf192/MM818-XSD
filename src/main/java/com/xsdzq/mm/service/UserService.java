package com.xsdzq.mm.service;

import com.xsdzq.mm.entity.UserEntity;
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
	void addTicketByJobWithEmpId(String clientId, String clientName, String empId, int num);
	void addTicketByJob(String clientId, String clientName, int num);
	
	void addTicketByJobForReduceEmp(String clientId, String empId, int num, String reason);
}
