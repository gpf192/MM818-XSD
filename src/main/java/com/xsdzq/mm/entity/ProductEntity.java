package com.xsdzq.mm.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "lcj_product_info")
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
	@SequenceGenerator(name = "product_sequence", sequenceName = "sequence_product", allocationSize = 1)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "code")
	private String code;

	@Column(name = "type")
	private String type;

	@Column(name = "begin_date")
	private Date beginDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "coefficient")
	private String coefficient;// 转化系数

	@Column(name = "initial_amount")
	private String initialAmount;// 起始金额

	@Column(name = "flag")
	private int flag;// 0-场内 1-场外
	
	@Column(name = "scan_flag")
	private int scanFlag = 0;// 开门红 ,是否扫描场内，0-不扫场内， 1-扫场内

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(String coefficient) {
		this.coefficient = coefficient;
	}

	public String getInitialAmount() {
		return initialAmount;
	}

	public void setInitialAmount(String initialAmount) {
		this.initialAmount = initialAmount;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getScanFlag() {
		return scanFlag;
	}

	public void setScanFlag(int scanFlag) {
		this.scanFlag = scanFlag;
	}

}
