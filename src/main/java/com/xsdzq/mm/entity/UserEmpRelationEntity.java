package com.xsdzq.mm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "lcj_user_emp_relation_view")
@EntityListeners(AuditingEntityListener.class)
public class UserEmpRelationEntity implements Serializable {
	// 客户号
	@Id
	@Column(name = "client_id")
	private String clientId;

	@Column(name = "client_name")
	private String clientName;
	
	@Column(name = "tougu_id")
	private String touguId;
	
	@Column(name = "tougu_name")
	private String touguName;//投顾姓名
	
	@Column(name = "broker_id")
	private String brokerId;
	
	@Column(name = "broker_name")
	private String brokerName;//经纪人
	
	@Column(name = "depart_name")
	private String departName;//经纪人

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getTouguId() {
		return touguId;
	}

	public void setTouguId(String touguId) {
		this.touguId = touguId;
	}

	public String getTouguName() {
		return touguName;
	}

	public void setTouguName(String touguName) {
		this.touguName = touguName;
	}

	public String getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	
	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Override
	public String toString() {
		return "UserEmpRelationEntity [clientId=" + clientId + ", clientName=" + clientName + ", touguId=" + touguId
				+ ", touguName=" + touguName + ", brokerId=" + brokerId + ", brokerName=" + brokerName + "]";
	}
	
	
}
