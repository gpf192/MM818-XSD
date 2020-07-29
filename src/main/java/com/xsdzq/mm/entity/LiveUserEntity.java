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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "live_user")
@EntityListeners(AuditingEntityListener.class)
public class LiveUserEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "live_user_sequence")
	@SequenceGenerator(name = "live_user_sequence", sequenceName = "sequence_live_user", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	// lcj_user
	// 客户号
	@Column(name = "client_id", unique = true, nullable = false, length = 100)
	private String clientId;

	@Column(name = "client_name", nullable = true, length = 300)
	private String clientName;

	// 资金账号
	@Column(name = "fund_account", nullable = true, length = 100)
	private String fundAccount;

	@Column(name = "access_token", nullable = true, length = 100)
	private String accessToken;

	@Column(name = "password", nullable = true, length = 500)
	private String password;

	@Column(name = "mobile", nullable = true, length = 12)
	private String mobile;

	@Column(name = "app_version", nullable = true, length = 100)
	private String appVersion;

	@Column(name = "last_op_ip", nullable = true, length = 200)
	private String lastOpIP;

	@Column(name = "last_login_time", nullable = true, length = 200)
	private String lastLoginTime;
	
	  @Column(name="uuid", nullable=true, length=200)
	  private String uuid;
	// 创建时间
	@Column(name = "createtime")
	@CreatedDate
	private Date createtime;

	// 修改时间
	@Column(name = "modifytime", nullable = true)
	@LastModifiedDate
	private Date modifytime;

	public LiveUserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LiveUserEntity(Long id, String clientId, String clientName, String fundAccount, String accessToken,
			String password, String mobile, String appVersion, String lastOpIP, String lastLoginTime, String uuid,
			Date createtime, Date modifytime) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.clientName = clientName;
		this.fundAccount = fundAccount;
		this.accessToken = accessToken;
		this.password = password;
		this.mobile = mobile;
		this.appVersion = appVersion;
		this.lastOpIP = lastOpIP;
		this.lastLoginTime = lastLoginTime;
		this.uuid = uuid;
		this.createtime = createtime;
		this.modifytime = modifytime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getFundAccount() {
		return fundAccount;
	}

	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getLastOpIP() {
		return lastOpIP;
	}

	public void setLastOpIP(String lastOpIP) {
		this.lastOpIP = lastOpIP;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	
}
