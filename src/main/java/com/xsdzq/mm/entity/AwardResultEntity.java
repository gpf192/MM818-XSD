package com.xsdzq.mm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "lcj_award_result")
public class AwardResultEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "award_result_sequence")
	@SequenceGenerator(name = "award_result_sequence", sequenceName = "sequence_award_result", allocationSize = 1)
	@Column(name = "id")
	private long id;

	@Column(name = "award_result_num") // 对奖个数
	private Integer awardNumber;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "client_id", referencedColumnName = "client_id")
	private UserEntity userEntity;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "award_id", referencedColumnName = "id")
	private AwardEntity awardEntity;

	@Column(name = "record_time", nullable = false)
	private Date recordTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getAwardNumber() {
		return awardNumber;
	}

	public void setAwardNumber(Integer awardNumber) {
		this.awardNumber = awardNumber;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public AwardEntity getAwardEntity() {
		return awardEntity;
	}

	public void setAwardEntity(AwardEntity awardEntity) {
		this.awardEntity = awardEntity;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

}
