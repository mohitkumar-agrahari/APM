package com.apm.datarw.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AnalyticOutputDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2782491370435181832L;
	@JsonProperty("alarms")
	private List<AlarmDTO> alarms;

	@JsonProperty("outputData")
	private String outputData;

	@JsonProperty("state")
	private String stateData;

	@NotNull 
	@JsonProperty("analyticId")
	private Long analyticId;
	
	@NotNull
	@JsonProperty("assetId")
	private String assetId;
	
	@NotNull
	@JsonProperty("jobId")
	private String jobId;
	
	@JsonProperty("error")
	private String error;
	
	@JsonProperty("validationRequest")
	private boolean validationRequest;
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public boolean isValidationRequest() {
		return validationRequest;
	}
	public void setValidationRequest(boolean validationRequest) {
		this.validationRequest = validationRequest;
	}
	/*public List<AlarmDTO> getAlarmTimestamps() {
		return alarm;
	}
	public void setAlarmTimestamps(List<AlarmDTO> alarmTimestamps) {
		this.alarm = alarmTimestamps;
	}*/
	public String getOutputData() {
		return outputData;
	}
	public void setOutputData(String outputData) {
		this.outputData = outputData;
	}
	public String getStateData() {
		return stateData;
	}
	public void setStateData(String stateData) {
		this.stateData = stateData;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<AlarmDTO> getAlarms() {
		return alarms;
	}
	public void setAlarms(List<AlarmDTO> alarms) {
		this.alarms = alarms;
	}
	public Long getAnalyticId() {
		return analyticId;
	}
	public void setAnalyticId(Long analyticId) {
		this.analyticId = analyticId;
	} 
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	@Override
	public String toString() {
		return "AnalyticOutputDataDTO [alarms=" + alarms + ", outputData=" + outputData + ", stateData=" + stateData
				+ ", analyticId=" + analyticId + ", assetId=" + assetId + ", jobId=" + jobId + ", validationRequest=" + validationRequest + "]";
	}
}
