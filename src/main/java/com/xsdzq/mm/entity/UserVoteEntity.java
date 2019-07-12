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

//用户投票数
@Entity
@Table(name = "lcj_user_vote_record")
@EntityListeners(AuditingEntityListener.class)
public class UserVoteEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_vote_sequence")
	@SequenceGenerator(name = "user_vote_sequence", sequenceName = "sequence_user_vote", allocationSize = 1)
	@Column(name = "id")
	private long id;

	@Column(name = "username")
	private String username;

	@Column(name = "account")
	private String account;

	@Column(name = "client_id")
	private String clientId;// 客户号

	@Column(name = "total_votes")
	private String totalVotes;// 所得总票数

	@Column(name = "votes_source")
	private String votesSource;// 得票来源

	@Column(name = "sourceId")
	private String sourceId;// 得票来源id

	@Column(name = "gain_time")
	private String gainTime;// 得票时间

	@Column(name = "vote_for")
	private String voteFor;// 投票对象

	@Column(name = "vote_for_amount") // 使用票数
	private String voteForAmount;

	@Column(name = "sales_department")
	private String salesDepartment;// 隶属营业部

	@Column(name = "division")
	private String division;// 隶属赛区

	// 创建时间
	@Column(name = "createtime")
	@CreatedDate
	private Date createtime;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(String totalVotes) {
		this.totalVotes = totalVotes;
	}

	public String getVotesSource() {
		return votesSource;
	}

	public void setVotesSource(String votesSource) {
		this.votesSource = votesSource;
	}

	public String getGainTime() {
		return gainTime;
	}

	public void setGainTime(String gainTime) {
		this.gainTime = gainTime;
	}

	public String getVoteFor() {
		return voteFor;
	}

	public void setVoteFor(String voteFor) {
		this.voteFor = voteFor;
	}

	public String getVoteForAmount() {
		return voteForAmount;
	}

	public void setVoteForAmount(String voteForAmount) {
		this.voteForAmount = voteForAmount;
	}

	public String getSalesDepartment() {
		return salesDepartment;
	}

	public void setSalesDepartment(String salesDepartment) {
		this.salesDepartment = salesDepartment;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "UserVoteEntity [id=" + id + ", username=" + username + ", account=" + account + ", totalVotes="
				+ totalVotes + ", votesSource=" + votesSource + ", sourceId=" + sourceId + ", gainTime=" + gainTime
				+ ", voteFor=" + voteFor + ", voteForAmount=" + voteForAmount + ", salesDepartment=" + salesDepartment
				+ ", division=" + division + "]";
	}

}
