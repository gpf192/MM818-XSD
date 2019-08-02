package com.xsdzq.mm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "lcj_user_total_ticket")
@EntityListeners(AuditingEntityListener.class)
public class UserTicketTotalViewEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "total")
	private Integer total;

	@Id
	@Column(name = "client_id")
	private String clientId;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "UserTicketTotalViewEntity [total=" + total + ", client_id=" + clientId + "]";
	}

}
