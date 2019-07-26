package com.xsdzq.mm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Entity
@Table(name = "lcj_sign_invest_view")
@EntityListeners(AuditingEntityListener.class)
public class SignInvestViewEntity implements Serializable {
	@Column(name = "client_id")
	private String clientId;
	
	@Column(name = "Invest_id")
	private String InvestId;
	
	@Column(name = "service_type")
	private int serviceType;  //0-投顾  1-产品
	
	@Column(name = "status")
	private String status;//0-未审核  1-审核通过   2-驳回
	
	@Column(name = "effective_date")
	private String effectiveDate;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}



	
	public String getInvestId() {
		return InvestId;
	}

	public void setInvestId(String investId) {
		InvestId = investId;
	}

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Override
	public String toString() {
		return "SignInvestViewEntity [clientId=" + clientId + ", serviceType=" + serviceType + ", status=" + status
				+ ", effectiveDate=" + effectiveDate + "]";
	}


	
	

}
