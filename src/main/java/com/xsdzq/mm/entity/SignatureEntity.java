package com.xsdzq.mm.entity;

public class SignatureEntity {
	private String Signature;
	private String timestamp;
	private String noncestr;

	public String getSignature() {
		return Signature;
	}

	public void setSignature(String signature) {
		Signature = signature;
	}

	public SignatureEntity(String signature) {
		super();
		Signature = signature;
	}

	public SignatureEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	
}
