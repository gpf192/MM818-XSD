package com.xsdzq.mm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "lcj_award")
public class AwardEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "award_sequence")
	@SequenceGenerator(name = "award_sequence", sequenceName = "sequence_award", allocationSize = 1)
	@Column(name = "id")
	private long id;

	@Column(name = "award_name")
	private String awardName;// 奖品名称 牛气冲天奖

	@Column(name = "award_name_alias")
	private String awardNameAlias;// 奖品别名 京东E卡，价值50元

	@Column(name = "image_name") // 关联图片名称
	private String imageName;

	@Column(name = "image_number")
	private Integer imageNumber;

	@Column(name = "award_value") // 奖品价值
	private Integer awardValue;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public String getAwardNameAlias() {
		return awardNameAlias;
	}

	public void setAwardNameAlias(String awardNameAlias) {
		this.awardNameAlias = awardNameAlias;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Integer getImageNumber() {
		return imageNumber;
	}

	public void setImageNumber(Integer imageNumber) {
		this.imageNumber = imageNumber;
	}

	public Integer getAwardValue() {
		return awardValue;
	}

	public void setAwardValue(Integer awardValue) {
		this.awardValue = awardValue;
	}

	@Override
	public String toString() {
		return "AwardEntity [id=" + id + ", awardName=" + awardName + ", awardNameAlias=" + awardNameAlias
				+ ", imageName=" + imageName + ", imageNumber=" + imageNumber + ", awardValue=" + awardValue + "]";
	}
}
