package com.apm.datarw.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name="analyticSurplusConstants")
@Table(name="ANALYTIC_SURPLUS_CONSTANTS")
public class AnalyticSurplusConstantsEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2431338618641080672L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="analytic_surplus_constants_seq")
	@SequenceGenerator(
			name="analytic_surplus_constants_seq",
			sequenceName="analytic_surplus_constants_seq",
			allocationSize=1
			)
	@Column(name="TAG_ID")
	private long tagId;

	@Column(name="TAG_NAME", nullable=false, columnDefinition="text")
	private String tagName;

	@Column(name="TAG_VALUE", columnDefinition="text")
	private String tagValue;

	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "ANALYTIC_ID", referencedColumnName = "ANALYTIC_ID")
	@JsonIgnore
	private AnalyticInfoEntity analyticInfoEntity;

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public AnalyticInfoEntity getAnalyticInfoEntity() {
		return analyticInfoEntity;
	}

	public void setAnalyticInfoEntity(AnalyticInfoEntity analyticInfoEntity) {
		this.analyticInfoEntity = analyticInfoEntity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
