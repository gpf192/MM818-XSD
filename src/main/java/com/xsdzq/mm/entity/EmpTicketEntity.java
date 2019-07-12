package com.xsdzq.mm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "lcj_emp_number")
@EntityListeners(AuditingEntityListener.class)
public class EmpTicketEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_ticket_sequence")
	@SequenceGenerator(name = "emp_ticket_sequence", sequenceName = "emp_sequence_ticket", allocationSize = 1)
	@Column(name = "id")
	private long id;

	@Column(name = "number")
	private Integer number = 0;// 票数 默认是0

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
	private EmpEntity empEntity;

	// 创建时间
	@Column(name = "createtime")
	@CreatedDate
	private Date createtime;

	// 修改时间
	@Column(name = "modifytime", nullable = true)
	@LastModifiedDate
	private Date modifytime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public EmpEntity getEmpEntity() {
		return empEntity;
	}

	public void setEmpEntity(EmpEntity empEntity) {
		this.empEntity = empEntity;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	@Override
	public String toString() {
		return "EmpTicketEntity [id=" + id + ", number=" + number + ", empEntity=" + empEntity.toString()
				+ ", createtime=" + createtime + ", modifytime=" + modifytime + "]";
	}

}
