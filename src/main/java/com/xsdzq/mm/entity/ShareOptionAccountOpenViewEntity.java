package com.xsdzq.mm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/*
 * 开门红活动，  期权账户开通记录视图
 */
@Entity
@Table(name = "kmh_share_option_open_view")
@EntityListeners(AuditingEntityListener.class)
public class ShareOptionAccountOpenViewEntity implements Serializable {
	@Id
	@Column(name = "client_id")
	private String clientId;
	
	@Column(name = "date_flag")
	private int dateFlag;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public int getDateFlag() {
		return dateFlag;
	}

	public void setDateFlag(int dateFlag) {
		this.dateFlag = dateFlag;
	}


	
	
	
}