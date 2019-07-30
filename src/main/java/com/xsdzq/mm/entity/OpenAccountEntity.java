package com.xsdzq.mm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Entity
@Table(name = "lcj_open_account_view")
@EntityListeners(AuditingEntityListener.class)
public class OpenAccountEntity implements Serializable {
	@Id
	
	@Column(name = "client_id")
	private String clientId;
	
	@Column(name = "org_name")
	private String orgName;
	
	@Column(name = "org_account")
	private String orgAccount;
	
	@Column(name = "open_date")
	private int openDate;
	
	@Column(name = "close_date")
	private int closeDate;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgAccount() {
		return orgAccount;
	}

	public void setOrgAccount(String orgAccount) {
		this.orgAccount = orgAccount;
	}

	public int getOpenDate() {
		return openDate;
	}

	public void setOpenDate(int openDate) {
		this.openDate = openDate;
	}

	public int getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(int closeDate) {
		this.closeDate = closeDate;
	}

	@Override
	public String toString() {
		return "OpenAccountEntity [clientId=" + clientId + ", orgName=" + orgName + ", orgAccount=" + orgAccount
				+ ", openDate=" + openDate + ", closeDate=" + closeDate + "]";
	}
	
	
}
