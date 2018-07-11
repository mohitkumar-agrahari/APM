package com.apm.was.dto;

import java.util.Date;

public class AnalyticDetailsDTO {
	
			private Long id;
	  private String title;
	  private String subtitle;
	  
	  private String severity;
	  
	  private String date;
	  private Long analytic_id;
	  private String analytic_name;
	  private String analytic_version;
	  private String added_on;
	  private String analytic_platform;
	  private String execution_frequency;
	  private String is_execution_passed;
	  private Boolean analyticExecutionStatus;
	  private String analyticStartDate;
	  private String analyticEndDate;
	  private String assetType;
	  private String assetName;
	  private String lastExecutedDate;
	  private String formattedDate;
	  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
	public Long getAnalytic_id() {
		return analytic_id;
	}
	public void setAnalytic_id(Long analytic_id) {
		this.analytic_id = analytic_id;
	}
	public String getAnalytic_name() {
		return analytic_name;
	}
	public void setAnalytic_name(String analytic_name) {
		this.analytic_name = analytic_name;
	}
	public String getAnalytic_version() {
		return analytic_version;
	}
	public void setAnalytic_version(String analytic_version) {
		this.analytic_version = analytic_version;
	}
	
	public String getAnalytic_platform() {
		return analytic_platform;
	}
	public void setAnalytic_platform(String analytic_platform) {
		this.analytic_platform = analytic_platform;
	}
	
	
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getFormattedDate() {
		return formattedDate;
	}
	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}
	public String getExecution_frequency() {
		return execution_frequency;
	}
	public void setExecution_frequency(String execution_frequency) {
		this.execution_frequency = execution_frequency;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAdded_on() {
		return added_on;
	}
	public void setAdded_on(String added_on) {
		this.added_on = added_on;
	}

	public String getAnalyticStartDate() {
		return analyticStartDate;
	}
	public void setAnalyticStartDate(String analyticStartDate) {
		this.analyticStartDate = analyticStartDate;
	}
	public String getAnalyticEndDate() {
		return analyticEndDate;
	}
	public void setAnalyticEndDate(String analyticEndDate) {
		this.analyticEndDate = analyticEndDate;
	}
	public String getLastExecutedDate() {
		return lastExecutedDate;
	}
	public void setLastExecutedDate(String lastExecutedDate) {
		this.lastExecutedDate = lastExecutedDate;
	}
	public Boolean getAnalyticExecutionStatus() {
		return analyticExecutionStatus;
	}
	public void setAnalyticExecutionStatus(Boolean analyticExecutionStatus) {
		this.analyticExecutionStatus = analyticExecutionStatus;
	}
	public String getIs_execution_passed() {
		return is_execution_passed;
	}
	public void setIs_execution_passed(String is_execution_passed) {
		this.is_execution_passed = is_execution_passed;
	}
	
	

}
