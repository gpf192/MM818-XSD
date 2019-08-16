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
@Table(name = "lcj_user_ticket_record")
@EntityListeners(AuditingEntityListener.class)
public class UserTicketRecordEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_ticket_record_sequence")
	@SequenceGenerator(name = "user_ticket_record_sequence", sequenceName = "sequence_user_ticket_record", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "type", nullable = false)
	private boolean type; // type 为0 表示减少 reason 以0开头 0X， 为1表示增加 reason 以1开头 1X

	@Column(name = "votes_source", nullable = false) // 得票来源 ， 11.登陆 12.分享 13.理财产品 14.签约投顾 01 投票
	private String votesSource;

	@Column(name = "num", nullable = false)
	private Integer number = 0; // 增加或者减少的数量,默认为0

	@Column(name = "data_flag", nullable = false)
	private String dateFlag; // 每日的判断标准

	@Column(name = "gain_time")
	private Date gainTime;// 得票时间

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "client_id", referencedColumnName = "client_id")
	private UserEntity userEntity;
	
	@Column(name = "serial_num")
	private String serialNum; // 交易流水号

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public String getVotesSource() {
		return votesSource;
	}

	public void setVotesSource(String votesSource) {
		this.votesSource = votesSource;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getDateFlag() {
		return dateFlag;
	}

	public void setDateFlag(String dateFlag) {
		this.dateFlag = dateFlag;
	}

	public Date getGainTime() {
		return gainTime;
	}

	public void setGainTime(Date gainTime) {
		this.gainTime = gainTime;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
	public String toString() {
		return "UserTicketRecordEntity [id=" + id + ", type=" + type + ", votesSource=" + votesSource + ", number="
				+ number + ", dateFlag=" + dateFlag + ", gainTime=" + gainTime + ", userEntity=" + userEntity + "]";
	}

}
