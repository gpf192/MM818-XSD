package com.xsdzq.mm.service.impl;

import java.util.Calendar;
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
	public List<UserTicketRecordEntity> getUserRecord(UserEntity userEntity) {
		// TODO Auto-generated method stub
		return userTicketRecordRepository.findByUserEntityOrderByGainTimeDesc(userEntity);
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
	public void addUserTicketNumber(UserEntity userEntity, int number, String reason) {
		UserTicketEntity userTicketEntity = getUserTicketEntity(userEntity);
		userTicketRepository.add(userTicketEntity, number);
		addUserTicketRecord(userEntity, true, number, reason);
	}

	// 减少用户票数，同时添加记录
	@Override
	@Transactional
	public void reduceUserTickeNumber(UserEntity userEntity, int number, String reason) {
		UserTicketEntity userTicketEntity = getUserTicketEntity(userEntity);
		userTicketRepository.reduce(userTicketEntity, number);
		addUserTicketRecord(userEntity, false, number, reason);
	}
	
	// JOB 增加用户票数，同时添加记录
	@Override
	@Transactional
	public void addUserTicketNumberByJob(UserEntity userEntity, int number, String reason) {
		UserTicketEntity userTicketEntity = getUserTicketEntity(userEntity);
		userTicketRepository.add(userTicketEntity, number);
		addUserTicketRecordByJob(userEntity, true, number, reason);
		
	}

	// JOB 减少用户票数，同时添加记录
	@Override
	@Transactional
	public void reduceUserTickeNumberByJob(UserEntity userEntity, int number, String reason) {
		UserTicketEntity userTicketEntity = getUserTicketEntity(userEntity);
		userTicketRepository.reduce(userTicketEntity, number);
		addUserTicketRecordByJob(userEntity, false, number, reason);
	}

	

	@Override
	@Transactional
	public void userVoteEmp(UserEntity userEntity, String empId, int number) {
		// TODO Auto-generated method stub
		//int empInt = Integer.parseInt(empId);
		EmpEntity empEntity = empRepository.findByEmpId(empId);
		if (empEntity == null) {
			throw new RuntimeException("员工不存在");
		}
		// 用户减操作
		reduceUserTickeNumber(userEntity, number, TicketUtil.USERVOTE);
		// 员工加操作
		empTicketService.addEmpTicketNumber(empEntity, number, TicketUtil.USERVOTE);
		// 写入结果记录
		UserVoteEmpResultEntity userVoteEmpResultEntity = new UserVoteEmpResultEntity();
		userVoteEmpResultEntity.setUserEntity(userEntity);
		userVoteEmpResultEntity.setEmpEntity(empEntity);
		userVoteEmpResultEntity.setNumber(number);
		userVoteEmpResultEntity.setRecordTime(new Date());
		userVoteEmpResultEntity.setType(TicketUtil.USERVOTE);
		userVoteEmpResultRepository.save(userVoteEmpResultEntity);

	}
	//JOB
	//登录\分享\抽奖 用户定时任务  不再重复添加用户得票记录 前端已经添加
	@Override
	@Transactional
	public void userVoteEmpByJobForReduceEmp(UserEntity userEntity, String empId, int number, String reason) {
		// TODO Auto-generated method stub
		//int empInt = Integer.parseInt(empId);
		EmpEntity empEntity = empRepository.findByEmpId(empId);
		if (empEntity == null) {
			throw new RuntimeException("员工不存在");
		}
		// 用户减操作
		//得票时间标志是前一天  dataflag
		reduceUserTickeNumberByJob(userEntity, number, reason);
		// 员工加操作
		empTicketService.addEmpTicketNumberByJOB(empEntity, number, reason);
		// 写入结果记录
		UserVoteEmpResultEntity userVoteEmpResultEntity = new UserVoteEmpResultEntity();
		userVoteEmpResultEntity.setUserEntity(userEntity);
		userVoteEmpResultEntity.setEmpEntity(empEntity);
		userVoteEmpResultEntity.setNumber(number);
		userVoteEmpResultEntity.setRecordTime(new Date());
		userVoteEmpResultEntity.setType(reason);
		userVoteEmpResultRepository.save(userVoteEmpResultEntity);

	}
	
	
	//购买产品用户投票
	@Override
	@Transactional
	public void userVoteEmpByJob(UserEntity userEntity, String empId, int number, String reason) {
		// TODO Auto-generated method stub
		//int empInt = Integer.parseInt(empId);
		EmpEntity empEntity = empRepository.findByEmpId(empId);
		if (empEntity == null) {
			throw new RuntimeException("员工不存在");
		}
		// 用户减操作
		//得票时间标志是前一天  dataflag
		reduceUserTickeNumberByJob(userEntity, number, reason);
		// 员工加操作
		empTicketService.addEmpTicketNumberByJOB(empEntity, number, reason);
		// 写入结果记录
		UserVoteEmpResultEntity userVoteEmpResultEntity = new UserVoteEmpResultEntity();
		userVoteEmpResultEntity.setUserEntity(userEntity);
		userVoteEmpResultEntity.setEmpEntity(empEntity);
		userVoteEmpResultEntity.setNumber(number);
		userVoteEmpResultEntity.setRecordTime(new Date());
		userVoteEmpResultEntity.setType(reason);
		userVoteEmpResultRepository.save(userVoteEmpResultEntity);

	}
	
	//定时任务添加票数变化记录
	public void addUserTicketRecordByJob(UserEntity userEntity, boolean type, int number, String reason) {
	      
		String nowString = DateUtil.getPreDay();
		UserTicketRecordEntity userTicketRecordEntity = new UserTicketRecordEntity();
		userTicketRecordEntity.setUserEntity(userEntity);
		userTicketRecordEntity.setType(type);
		userTicketRecordEntity.setNumber(number);
		userTicketRecordEntity.setVotesSource(reason);
		userTicketRecordEntity.setDateFlag(nowString);
		userTicketRecordEntity.setGainTime(new Date());
		userTicketRecordRepository.save(userTicketRecordEntity);
	}

	// 添加用户票数变化的记录
	public void addUserTicketRecord(UserEntity userEntity, boolean type, int number, String reason) {
		String nowString = DateUtil.getStandardDate(new Date());
		UserTicketRecordEntity userTicketRecordEntity = new UserTicketRecordEntity();
		userTicketRecordEntity.setUserEntity(userEntity);
		userTicketRecordEntity.setType(type);
		userTicketRecordEntity.setNumber(number);
		userTicketRecordEntity.setVotesSource(reason);
		userTicketRecordEntity.setDateFlag(nowString);
		userTicketRecordEntity.setGainTime(new Date());
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

	//JOB
	@Override
	public List<UserTicketRecordEntity>  getByVotesSourceAndDateFlag(String votesSource, String dateFlag){
		return userTicketRecordRepository.findByVotesSourceAndDateFlag(votesSource, dateFlag);
	}
}
