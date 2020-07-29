package com.xsdzq.mm.model;

public class User {

	private String clientId;
	private String clientName;
	private String fundAccount;
	private String accessToken;
	private String mobile;
	private String appVersion;
	private String lastOpIP;
	private String lastLoginTime;
	private String liveUrl;
	public User() {
		super();

	}

	public User(String clientId, String clientName, String fundAccount, String accessToken, String mobile,
			String appVersion, String lastOpIP, String lastLoginTime, String liveUrl) {
		super();
		this.clientId = clientId;
		this.clientName = clientName;
		this.fundAccount = fundAccount;
		this.accessToken = accessToken;
		this.mobile = mobile;
		this.appVersion = appVersion;
		this.lastOpIP = lastOpIP;
		this.lastLoginTime = lastLoginTime;
		this.liveUrl = liveUrl;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getLiveUrl() {
		return liveUrl;
	}

	public void setLiveUrl(String liveUrl) {
		this.liveUrl = liveUrl;
	}

	@Override
	public String toString() {
		return "User [clientId=" + clientId + ", clientName=" + clientName + ", fundAccount=" + fundAccount
				+ ", accessToken=" + accessToken + ", mobile=" + mobile + ", appVersion=" + appVersion + ", lastOpIP="
				+ lastOpIP + ", lastLoginTime=" + lastLoginTime + ", liveUrl=" + liveUrl + "]";
	}

}
