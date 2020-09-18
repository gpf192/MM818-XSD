package com.xsdzq.mm.entity;

import javax.validation.constraints.Min;

import org.springframework.lang.NonNull;

public class People {

	@NonNull
	private String name;

	@Min(value = 5)
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "People [name=" + name + ", age=" + age + "]";
	}

}
