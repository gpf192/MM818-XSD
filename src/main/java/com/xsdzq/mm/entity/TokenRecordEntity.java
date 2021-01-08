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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "lcj_token_record")
@EntityListeners(AuditingEntityListener.class)
public class TokenRecordEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * default entity id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_token_record")
	@SequenceGenerator(name = "sequence_token_record", sequenceName = "sequence_token_record", allocationSize = 1)
	@Column(name = "id")
	private long id;

	// 客户号
	@Column(name = "client_id", length = 100)
	private String clientId;

	// 登录ClientId
	@Column(name = "login_client_id", length = 100)
	private String loginClientId;

	@Column(name = "access_token", length = 100)
	private String accessToken;

	// 登录ClientId
	@Column(name = "response", length = 1200)
	private String response;

	// 创建时间
	@Column(name = "createtime")
	@CreatedDate
	private Date createtime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getLoginClientId() {
		return loginClientId;
	}

	public void setLoginClientId(String loginClientId) {
		this.loginClientId = loginClientId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "TokenRecordEntity [id=" + id + ", clientId=" + clientId + ", loginClientId=" + loginClientId
				+ ", accessToken=" + accessToken + ", response=" + response + ", createtime=" + createtime + "]";
	}

}
