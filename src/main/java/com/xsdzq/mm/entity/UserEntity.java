package com.xsdzq.mm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "customer")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, length = 20)
	private Long id;

	// 登录名称
	@Column(name = "username", unique = true, nullable = false, length = 100)
	private String username;

	// 别名
	@Column(name = "alias", nullable = true, length = 100)
	private String alias;

	@Column(name = "password", nullable = true, length = 500)
	private String password;

	@Column(name = "phone", unique = true, nullable = true, length = 12)
	private String phone;

	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserEntity(Long id, String username, String alias, String password, String phone) {
		super();
		this.id = id;
		this.username = username;
		this.alias = alias;
		this.password = password;
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
