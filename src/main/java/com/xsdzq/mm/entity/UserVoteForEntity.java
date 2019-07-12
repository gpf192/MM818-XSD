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

@Entity
@Table(name = "lcj_user_vote_for_record")
@EntityListeners(AuditingEntityListener.class)
public class UserVoteForEntity implements Serializable{
	 private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_vote_for_sequence")
	@SequenceGenerator(name = "user_vote_for_sequence", sequenceName = "sequence_user_vote_for", allocationSize = 1)
	@Column(name = "id")
	 private Long id;
	
	 // 客户号
	 @Column(name = "client_id", unique = true, nullable = false, length = 100)
	 private String clientId;
	 
	 //用户姓名
	 @Column(name = "username")
	 private String username;
	 
	 //投票时间
	 @Column(name = "vote_time")
	 private String voteTime;
	 
	 //使用对象-员工
	 @Column(name = "emp_name")
	 private String empName;
	 
	 //员工编号
	 @Column(name = "emp_code")
	 private String empCode;
	 
	 //员工id
	 @Column(name = "emp_id")
	 private int emp_id;//c3与c4的系统有区别，以id为主键
	 
	 //使用票数
	 @Column(name = "emp_num")
	 private String voteNum;
	 
	 //隶属营业部
	 @Column(name = "sales_department")
	 private String salesDepartment;
	 
	 //隶属赛区
	 @Column(name = "division")
	 private String division;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getVoteTime() {
		return voteTime;
	}

	public void setVoteTime(String voteTime) {
		this.voteTime = voteTime;
	}

	public String getEmpName() {
		return empName;
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

	public String getVoteNum() {
		return voteNum;
	}

	public void setVoteNum(String voteNum) {
		this.voteNum = voteNum;
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

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	@Override
	public String toString() {
		return "UserVoteForEntity [id=" + id + ", clientId=" + clientId + ", username=" + username + ", voteTime="
				+ voteTime + ", empName=" + empName + ", empCode=" + empCode + ", voteNum=" + voteNum
				+ ", salesDepartment=" + salesDepartment + ", division=" + division + "]";
	}
	 
	 
}