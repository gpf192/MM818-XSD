package com.xsdzq.mm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

//参赛者得票数
@Entity
@Table(name = "lcj_emp_vote_record")
@EntityListeners(AuditingEntityListener.class)
public class EmpVoteEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_vote_sequence")
	@SequenceGenerator(name = "emp_vote_sequence", sequenceName = "sequence_emp_vote", allocationSize = 1)
	@Column(name = "id")
	private long id;

	@Column(name = "emp_id", unique = true, nullable = false)
	private int empId;

	@Column(name = "emp_name")
	private String empName;

	@Column(name = "emp_code")
	private String empCode;

	@Column(name = "sales_department")
	private String salesDepartment;// 隶属营业部

	@Column(name = "division")
	private String division;// 隶属赛区

	@Column(name = "get_vote_amount")
	private String getVoteAmount;// 获得票数

	@Column(name = "get_vote_time")
	private String getVoteTime;// 获得票时间

	@Column(name = "vote_from_user")
	private String voteFromUser;// 来源用户

	@Column(name = "client_id")
	private String clientId;// 来源用户

	@Column(name = "weight", columnDefinition = "int default 0")
	private int weight = 0;// 设置信息权重，初始为0

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getSalesDepartment() {
		return salesDepartment;
	}

	public void setSalesDepartment(String salesDepartment) {
		this.salesDepartment = salesDepartment;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getGetVoteAmount() {
		return getVoteAmount;
	}

	public void setGetVoteAmount(String getVoteAmount) {
		this.getVoteAmount = getVoteAmount;
	}

	public String getGetVoteTime() {
		return getVoteTime;
	}

	public void setGetVoteTime(String getVoteTime) {
		this.getVoteTime = getVoteTime;
	}

	public String getVoteFromUser() {
		return voteFromUser;
	}

	public void setVoteFromUser(String voteFromUser) {
		this.voteFromUser = voteFromUser;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
