package com.xsdzq.mm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "lcj_user_vote_emp_result")
@EntityListeners(AuditingEntityListener.class)
public class UserVoteEmpResultEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_emp_ticket_sequence")
	@SequenceGenerator(name = "user_emp_ticket_sequence", sequenceName = "user_emp_sequence_ticket", allocationSize = 1)
	@Column(name = "id")
	private long id;
	@Column(name = "num", nullable = false)
	private Integer number; // 增加或者减少的数量,默认为0

	@Column(name = "type", nullable = false)
	private String type; // 0 投票 1 理财自动 2投顾自动

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "client_id", referencedColumnName = "client_id")
	private UserEntity userEntity;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
	private EmpEntity empEntity;

	@Column(name = "record_time", nullable = false)
	private Date recordTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public EmpEntity getEmpEntity() {
		return empEntity;
	}

	public void setEmpEntity(EmpEntity empEntity) {
		this.empEntity = empEntity;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

}
