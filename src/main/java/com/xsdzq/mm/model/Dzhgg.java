package com.xsdzq.mm.model;

import java.io.Serializable;

/**
 * 大智慧广告信息录入
 * @author Administrator
 *
 */
public class Dzhgg implements Serializable{
	private String name;
	private String phone;
	private String activity;
	private String recordtime;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(String recordtime) {
		this.recordtime = recordtime;
	}
	@Override
	public String toString() {
		return "Dzhgg [name=" + name + ", phone=" + phone + ", activity=" + activity + ", recordtime=" + recordtime
				+ "]";
	}
	
}
