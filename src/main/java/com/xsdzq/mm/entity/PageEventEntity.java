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
@Table(name = "lcj_page_event")
public class PageEventEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "page_event_sequence")
	@SequenceGenerator(name = "page_event_sequence", sequenceName = "sequence_page_event", allocationSize = 1)
	@Column(name = "id")
	private long id;

	@Column(name = "page_event_id")
	private String pageEventId;

	@Column(name = "action_event")
	private String actionEvent;

	@Column(name = "record_time", nullable = false)
	private Date recordTime;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "client_id", referencedColumnName = "client_id")
	private UserEntity userEntity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPageEventId() {
		return pageEventId;
	}

	public void setPageEventId(String pageEventId) {
		this.pageEventId = pageEventId;
	}

	public String getActionEvent() {
		return actionEvent;
	}

	public void setActionEvent(String actionEvent) {
		this.actionEvent = actionEvent;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

}
