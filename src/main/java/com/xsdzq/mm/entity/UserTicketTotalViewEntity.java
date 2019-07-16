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

	@Id

	@Column(name = "total")
	private Integer total;

	@Column(name = "client_id")
	private Integer client_id;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getClient_id() {
		return client_id;
	}

	public void setClient_id(Integer client_id) {
		this.client_id = client_id;
	}

	@Override
	public String toString() {
		return "UserTicketTotalViewEntity [total=" + total + ", client_id=" + client_id + "]";
	}

}
