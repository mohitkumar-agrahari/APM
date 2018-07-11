package com.apm.datarw.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PythonRequestDTO {
	@JsonProperty("analyticName")
	private String analyticName;
	
	@JsonProperty("analyticId")
	private String analyticId;
	
	@JsonProperty("analyticVersion")
	private String analyticVersion;
	
	@JsonProperty("jobId")
	private String jobId;
	
	@JsonProperty("config")
	private ConfigDTO config;
	
	@JsonProperty("input_data")
	private String input_data;
	
	@JsonProperty("state_data")
	private Map<String, String> state_data = new HashMap<String, String>();
	
	@JsonProperty("assetId")
	private String assetId;
	
	@JsonProperty("validationRequest")
	private boolean validationRequest;

	public ConfigDTO getConfig() {
		return config;
	}
	public void setConfig(ConfigDTO config) {
		this.config = config;
	}
	public String getInput_data() {
		return input_data;
	}
	public void setInput_data(String input_data) {
		this.input_data = input_data;
	}
	
	public Map<String, String> getState_data() {
		return state_data;
	}
	public void putStateInfo(String key, String value) {
		this.state_data.put(key, value);
	}
	public String getAnalyticName() {
		return analyticName;
	}
	public void setAnalyticName(String analytic_name) {
		this.analyticName = analytic_name;
	}
	public String getAnalyticId() {
		return analyticId;
	}
	public void setAnalyticId(String analyticId) {
		this.analyticId = analyticId;
	}
	public String getAnalyticVersion() {
		return analyticVersion;
	}
	public void setAnalyticVersion(String analyticVersion) {
		this.analyticVersion = analyticVersion;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}	
	public boolean isValidationRequest() {
		return validationRequest;
	}
	public void setValidationRequest(boolean validationRequest) {
		this.validationRequest = validationRequest;
	}
	@Override
	public String toString() {
		return "PythonRequestDTO [analyticName=" + analyticName + ", analyticId=" + analyticId + ", analyticVersion="
				+ analyticVersion + ", jobId=" + jobId + ", config=" + config + ", input_data=" + input_data
				+ ", state_data=" + state_data + ", assetId=" + assetId + ", validationRequest = "+ validationRequest + "]";
	}
	
}
