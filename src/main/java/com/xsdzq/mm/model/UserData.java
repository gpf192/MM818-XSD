package com.xsdzq.mm.model;

public class UserData {
	private String encryptData;

	public UserData() {
		super();
	}

	public UserData(String encryptData) {
		super();
		this.encryptData = encryptData;
	}

	public String getEncryptData() {
		return encryptData;
	}

	public void setEncryptData(String encryptData) {
		this.encryptData = encryptData;
	}

	@Override
	public String toString() {
		return "UserData [encryptData=" + encryptData + "]";
	}

}
