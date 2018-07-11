package com.ge.sst.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Analytic Output Entity
 * This entity is responsible for storing data related to analytic output.
 * @author Ankita Srivastava
 *
 */
@Entity(name="analyticOutputEntity")
@Table(name="ANALYTIC_OUTPUT_RESULTS")
public class AnalyticOutputEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="analytic_output_id_seq")
	@SequenceGenerator(
			name="analytic_output_id_seq",
			sequenceName="analytic_output_id_seq",
			allocationSize=1
			)
	@Column(name="ANALYTIC_RESULT_ID")
	private long analyticResultId;

	@ManyToOne
	@JoinColumn(name = "ANALYTIC_ID", referencedColumnName = "ANALYTIC_ID")
	private AnalyticInfoEntity analyticInfoEntity;

	@Column(name="TAG_NAME")
	private String tagName;

	@Column(name="DATETIME")
	private Long dateTime;

	@Column(name="TAG_VALUE")
	private String tagValue;

	@Column(name="ASSET_ID")
	private String assetId;
	
	@Column(name="IS_TIMESERIES")
	private boolean isTimeseries;

	public AnalyticInfoEntity getAnalyticInfoEntity() {
		return analyticInfoEntity;
	}

	public void setAnalyticInfoEntity(AnalyticInfoEntity analyticInfoEntity) {
		this.analyticInfoEntity = analyticInfoEntity;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Long getDateTime() {
		return dateTime;
	}

	public void setDateTime(Long dateTime) {
		this.dateTime = dateTime;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public boolean isTimeseries() {
		return isTimeseries;
	}

	public void setTimeseries(boolean isTimeseries) {
		this.isTimeseries = isTimeseries;
	}

	public long getAnalyticResultId() {
		return analyticResultId;
	}

	public void setAnalyticResultId(long analyticResultId) {
		this.analyticResultId = analyticResultId;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	@Override
	public String toString() {
		return "AnalyticOutputEntity [analyticResultId=" + analyticResultId + ", analyticInfoEntity="
				+ analyticInfoEntity + ", tagName=" + tagName + ", dateTime=" + dateTime + ", tagValue=" + tagValue
				+ ", assetId=" + assetId + ", isTimeseries=" + isTimeseries + "]";
	}



}
