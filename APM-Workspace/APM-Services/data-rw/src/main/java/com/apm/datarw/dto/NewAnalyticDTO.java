package com.apm.datarw.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class NewAnalyticDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5359345230704507310L;

	@JsonProperty("analytic_name")
	@NotNull(message="'Analytic Name' cannot be null.")
	private String analyticName;

	@JsonProperty("analytic_version")
	private String analyticVersion="DEFAULT_VER";

	@JsonProperty("analytic_description")
	private String analyticDescription;

	@JsonProperty("is_alarming")
	private Boolean isAlarming;

	@JsonProperty("state_enabled")
	private Boolean isStateEnabled;

	@JsonProperty("analytic_scope_level")
	private String analyticScopeLevel;

	@JsonProperty("start_date")
	private String startDate;

	@JsonProperty("end_date")
	private String endDate;

	@JsonProperty("execution_frequency")
	private String executionFrequency;

	@JsonProperty("analytic_platform")
	private String analyticPlatform;

	@JsonProperty("is_state_maintained")
	private boolean isStateMaintained;

	@JsonProperty("data_interval")
	private long dataInterval;

	@JsonProperty("start_offset")
	private long startOffset;

	@JsonProperty("end_offset")
	private long endOffset;

	@JsonProperty("asset_id")
	private List<String> assetId;

	@JsonProperty("config")
	@NotNull(message="'Analytic Configuration' cannot be null.")
	private ConfigDTO config;

	public String getAnalyticName() {
		return analyticName;
	}
	public void setAnalyticName(String analyticName) {
		this.analyticName = analyticName;
	}
	public ConfigDTO getConfig() {
		return config;
	}
	public void setConfig(ConfigDTO config) {
		this.config = config;
	}
	public String getAnalyticVersion() {
		return analyticVersion;
	}
	public void setAnalyticVersion(String analyticVersion) {
		this.analyticVersion = analyticVersion;
	}
	public String getAnalyticDescription() {
		return analyticDescription;
	}
	public void setAnalyticDescription(String analyticDescription) {
		this.analyticDescription = analyticDescription;
	}
	public String getAnalyticScopeLevel() {
		return analyticScopeLevel;
	}
	public void setAnalyticScopeLevel(String analyticScopeLevel) {
		this.analyticScopeLevel = analyticScopeLevel;
	}
	public String getExecutionFrequency() {
		return executionFrequency;
	}
	public void setExecutionFrequency(String executionFrequency) {
		this.executionFrequency = executionFrequency;
	}
	public String getAnalyticPlatform() {
		return analyticPlatform;
	}
	public void setAnalyticPlatform(String analyticPlatform) {
		this.analyticPlatform = analyticPlatform;
	}
	public boolean isStateMaintained() {
		return isStateMaintained;
	}
	public void setStateMaintained(boolean isStateMaintained) {
		this.isStateMaintained = isStateMaintained;
	}
	public long getDataInterval() {
		return dataInterval;
	}
	public void setDataInterval(long dataInterval) {
		this.dataInterval = dataInterval;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Boolean getIsAlarming() {
		return isAlarming;
	}
	public void setIsAlarming(Boolean isAlarming) {
		this.isAlarming = isAlarming;
	}
	public Boolean getIsStateEnabled() {
		return isStateEnabled;
	}
	public void setIsStateEnabled(Boolean isStateEnabled) {
		this.isStateEnabled = isStateEnabled;
	}

	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public long getStartOffset() {
		return startOffset;
	}
	public void setStartOffset(long startOffset) {
		this.startOffset = startOffset;
	}
	public long getEndOffset() {
		return endOffset;
	}
	public void setEndOffset(long endOffset) {
		this.endOffset = endOffset;
	}
	public List<String> getAssetId() {
		return assetId;
	}
	public void setAssetId(List<String> assetIds) {
		this.assetId = assetIds;
	}
	@Override
	public String toString() {
		return "NewAnalyticDTO [analyticName=" + analyticName + ", analyticVersion=" + analyticVersion
				+ ", analyticDescription=" + analyticDescription + ", isAlarming=" + isAlarming + ", isStateEnabled="
				+ isStateEnabled + ", analyticScopeLevel=" + analyticScopeLevel + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", executionFrequency=" + executionFrequency + ", analyticPlatform="
				+ analyticPlatform + ", isStateMaintained=" + isStateMaintained + ", dataInterval=" + dataInterval
				+ ", startOffset=" + startOffset + ", endOffset=" + endOffset + ", assetIds=" + assetId + ", config="
				+ config + "]";
	}
}
