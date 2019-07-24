package com.xsdzq.mm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "lcj_employee_info")
@EntityListeners(AuditingEntityListener.class)
public class ProductSellViewEntity implements Serializable {
	// 客户号
	@Column(name = "client_id")
	private String clientId;

	@Column(name = "client_name")
	private String clientName;
	
	@Column(name = "product_code")
	private String productCode;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "finance_account")
	private String financeAccount;//金融账号
	
	@Column(name = "deal_time")
	private String dealTime;//成交日期
	
	@Column(name = "deal_amount")
	private String dealAmount;//成交金额

	@Override
	public String toString() {
		return "ProductSellViewEntity [clientId=" + clientId + ", clientName=" + clientName + ", productCode="
				+ productCode + ", productName=" + productName + ", financeAccount=" + financeAccount + ", dealTime="
				+ dealTime + ", dealAmount=" + dealAmount + "]";
	}
	
}
