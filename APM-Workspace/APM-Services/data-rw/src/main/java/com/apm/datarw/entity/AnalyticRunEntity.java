package com.apm.datarw.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="ANALYTIC_RUN_STATUS")
public class AnalyticRunEntity {
	@Id
	@Column(name="JOB_ID", columnDefinition="text")
	private String jobId=UUID.randomUUID().toString();
	@Column(name="START_DATE")
	private String startDate;
	@Column(name="END_DATE")
	private String endDate;
	@Column(name="ASSET_ID")
	private String assetId;
	@Column(name="IS_RUNNING")
	private Boolean isRunning;
	@Column(name="IS_SCHEDULED")
	private Boolean isScheduled;
	@Column(name="JOB_STATUS")
	private String jobStatus;
	@ManyToOne
	@JoinColumn(name = "ANALYTIC_ID", referencedColumnName = "ANALYTIC_ID")
	private AnalyticInfoEntity analyticInfoEntity;
	
	@Column(name="is_real_time_execution")
	private boolean isRealTimeExecution;
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public Boolean getIsRunning() {
		return isRunning;
	}
	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}
	public AnalyticInfoEntity getAnalyticInfoEntity() {
		return analyticInfoEntity;
	}
	public void setAnalyticInfoEntity(AnalyticInfoEntity analyticInfoEntity) {
		this.analyticInfoEntity = analyticInfoEntity;
	}
	public Boolean getIsScheduled() {
		return isScheduled;
	}
	public void setIsScheduled(Boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	public boolean isRealTimeExecution() {
		return isRealTimeExecution;
	}
	public void setRealTimeExecution(boolean isRealTimeExecution) {
		this.isRealTimeExecution = isRealTimeExecution;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	
	
}
