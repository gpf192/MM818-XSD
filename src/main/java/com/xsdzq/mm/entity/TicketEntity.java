package com.xsdzq.mm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "lcj_ticket")
@EntityListeners(AuditingEntityListener.class)
public class TicketEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_sequence")
	@SequenceGenerator(name = "ticket_sequence", sequenceName = "sequence_ticket", allocationSize = 1)
	@Column(name = "id")
	private long id;

	@Column(name = "type")
	private String type;// 0-user,1-员工

	@Column(name = "num")
	private int number;// 票数

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "TicketEntity [id=" + id + ", type=" + type + ", number=" + number + "]";
	}

}
