package com.xsdzq.mm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsdzq.mm.dao.EmpRepository;
import com.xsdzq.mm.dao.UserTicketRecordRepository;
import com.xsdzq.mm.dao.UserTicketRepository;
import com.xsdzq.mm.dao.UserTicketTotalViewRepository;
import com.xsdzq.mm.dao.UserVoteEmpResultRepository;
import com.xsdzq.mm.entity.EmpEntity;
import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketEntity;
import com.xsdzq.mm.entity.UserTicketRecordEntity;
import com.xsdzq.mm.entity.UserTicketTotalViewEntity;
import com.xsdzq.mm.entity.UserVoteEmpResultEntity;
import com.xsdzq.mm.model.UserTicketRecordAndResult;
import com.xsdzq.mm.service.EmpTicketService;
import com.xsdzq.mm.service.UserTicketService;
import com.xsdzq.mm.util.DateUtil;
import com.xsdzq.mm.util.TicketUtil;

@Service(value = "userTicketServiceImpl")
@Transactional(readOnly = true)
public class UserTicketServiceImpl implements UserTicketService {

	@Autowired
	private UserTicketRepository userTicketRepository;

	@Autowired
	private UserTicketRecordRepository userTicketRecordRepository;

	@Autowired
	private UserVoteEmpResultRepository userVoteEmpResultRepository;

	@Autowired
	private UserTicketTotalViewRepository userTicketTotalViewRepository;

	@Autowired
	private EmpRepository empRepository;

	@Autowired
	private EmpTicketService empTicketService;

	@Override
	public int getUserTicket(UserEntity userEntity) {
		// TODO Auto-generated method stub
		UserTicketEntity userTicketEntity = getUserTicketEntity(userEntity);
		return userTicketEntity.getNumber();
	}

	@Override
	public int countVoteNumber() {
		// TODO Auto-generated method stub
		return (int) userTicketTotalViewRepository.count();
	}

	@Override
	public int countUserVoteNumber(UserEntity userEntity) {
		// TODO Auto-generated method stub
		return (int) userTicketRecordRepository.countByUserEntity(userEntity);
	}

	@Override
	public UserVoteEmpResultEntity getUserVoteEmpResultEntity(UserEntity userEntity, String gainTime) {
		// TODO Auto-generated method stub
		Date recordDate = DateUtil.stringToDateLong(gainTime);
		UserVoteEmpResultEntity voteEmpResultEntity = userVoteEmpResultRepository
				.findByUserEntityAndRecordTime(userEntity, recordDate);
		return voteEmpResultEntity;
	}

	@Override
	public List<UserTicketRecordAndResult> getUserRecord(UserEntity userEntity, int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		Page<UserTicketRecordEntity> uPage = userTicketRecordRepository.findByUserEntityOrderByGainTimeDesc(userEntity,
				pageRequest);
		List<UserTicketRecordEntity> userTicketRecordEntities = uPage.getContent();
		List<UserTicketRecordAndResult> uRecordAndResultEntities = new ArrayList<UserTicketRecordAndResult>();
		for (UserTicketRecordEntity userTicketRecordEntity : userTicketRecordEntities) {
			Date gainTimeDate = userTicketRecordEntity.getGainTime();
			UserVoteEmpResultEntity userVoteEmpResultEntity = userVoteEmpResultRepository
					.findByUserEntityAndRecordTime(userEntity, gainTimeDate);
			UserTicketRecordAndResult userTicketRecordAndResult = new UserTicketRecordAndResult();
			userTicketRecordAndResult.setUserTicketRecordEntity(userTicketRecordEntity);
			if(userVoteEmpResultEntity != null) {
				userTicketRecordAndResult.setUserVoteEmpResultEntity(userVoteEmpResultEntity);
			}
			uRecordAndResultEntities.add(userTicketRecordAndResult);
		}
		return uRecordAndResultEntities;
	}

	// 提供统一的UserTicketEntity，没有的话会新增，不会得到空值
	@Override
	@Transactional
	public UserTicketEntity getUserTicketEntity(UserEntity userEntity) {
		UserTicketEntity userTicketEntity = userTicketRepository.findByUserEntity(userEntity);
		if (userTicketEntity == null) {
			UserTicketEntity myUserTicketEntity = new UserTicketEntity();
			myUserTicketEntity.setNumber(0);
			myUserTicketEntity.setUserEntity(userEntity);
			userTicketRepository.save(myUserTicketEntity);
			userTicketEntity = userTicketRepository.findByUserEntity(userEntity);
		}
		return userTicketEntity;
	}

	// 增加用户票数，同时添加记录
	@Override
	@Transactional
	public void addUserTicketNumber(UserEntity userEntity, int number, String reason, Date date) {
		UserTicketEntity userTicketEntity = getUserTicketEntity(userEntity);
		userTicketRepository.add(userTicketEntity, number);
		addUserTicketRecord(userEntity, true, number, reason, date);
	}

	// 减少用户票数，同时添加记录
	@Override
	@Transactional
	public void reduceUserTickeNumber(UserEntity userEntity, int number, String reason, Date date) {
		UserTicketEntity userTicketEntity = getUserTicketEntity(userEntity);
		userTicketRepository.reduce(userTicketEntity, number);
		addUserTicketRecord(userEntity, false, number, reason, date);
	}

	@Override
	@Transactional
	public void userVoteEmp(UserEntity userEntity, String empId, int number) {
		// TODO Auto-generated method stub
		// int empInt = Integer.parseInt(empId);
		EmpEntity empEntity = empRepository.findByEmpId(empId);
		if (empEntity == null) {
			throw new RuntimeException("员工不存在");
		}
		// 保持投票的时间一致性，作为一系列的唯一订单号 1.用户操作的时间，2.员工增加的时间，3.写入结果的时间
		Date nowDate = new Date();
		// 用户减操作
		reduceUserTickeNumber(userEntity, number, TicketUtil.USERVOTE, nowDate);
		// 员工加操作
		empTicketService.addEmpTicketNumber(empEntity, number, TicketUtil.USERVOTE, nowDate);
		// 写入结果记录
		UserVoteEmpResultEntity userVoteEmpResultEntity = new UserVoteEmpResultEntity();
		userVoteEmpResultEntity.setUserEntity(userEntity);
		userVoteEmpResultEntity.setEmpEntity(empEntity);
		userVoteEmpResultEntity.setNumber(number);
		userVoteEmpResultEntity.setRecordTime(nowDate);
		userVoteEmpResultEntity.setType(TicketUtil.USERVOTE);
		userVoteEmpResultRepository.save(userVoteEmpResultEntity);

	}

	// 添加用户票数变化的记录
	public void addUserTicketRecord(UserEntity userEntity, boolean type, int number, String reason, Date date) {
		String nowString = DateUtil.getStandardDate(new Date());
		UserTicketRecordEntity userTicketRecordEntity = new UserTicketRecordEntity();
		userTicketRecordEntity.setUserEntity(userEntity);
		userTicketRecordEntity.setType(type);
		userTicketRecordEntity.setNumber(number);
		userTicketRecordEntity.setVotesSource(reason);
		userTicketRecordEntity.setDateFlag(nowString);
		userTicketRecordEntity.setGainTime(date);
		userTicketRecordRepository.save(userTicketRecordEntity);
	}

	@Override
	public List<UserTicketTotalViewEntity> getUserTicketSort(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		Page<UserTicketTotalViewEntity> userTicketPage = userTicketTotalViewRepository.findBy(pageRequest);
		List<UserTicketTotalViewEntity> userTicketList = userTicketPage.getContent();
		return userTicketList;
	}

}
