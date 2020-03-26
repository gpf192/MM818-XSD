package com.xsdzq.mm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "live_login_record")
public class LiveRecordEntity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "live_login_sequence")
	@SequenceGenerator(name = "live_login_sequence", sequenceName = "sequence_live_login", allocationSize = 1)
	@Column(name = "id")
	private long id;
	
	@Column(name = "record_time", nullable = false)
	@CreatedDate
	private Date recordTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
	
}
