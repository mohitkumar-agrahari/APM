package com.apm.datarw.dto;

public class ValidateAnalyticRequestDTO {
	

	public ValidateAnalyticRequestDTO() {
		
	}

	private String analytic_name;
	private String analytic_id;
	private String analytic_version;
	//private String jobId;
	private ConfigDTO config;
	//private String input_data;
	//private Map<String, String> state_data = new HashMap<String, String>();
	private String asset_id;
	private boolean validationRequest;

	public ConfigDTO getConfig() {
		return config;
	}
	public void setConfig(ConfigDTO config) {
		this.config = config;
	}
	/*public String getInput_data() {
		return input_data;
	}
	public void setInput_data(String input_data) {
		this.input_data = input_data;
	}*/
	
	/*public Map<String, String> getState_data() {
		return state_data;
	}
	public void putStateInfo(String key, String value) {
		this.state_data.put(key, value);
	}*/
	
	/*public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}*/
	
	public String getAnalytic_name() {
		return analytic_name;
	}
	public void setAnalytic_name(String analytic_name) {
		this.analytic_name = analytic_name;
	}
	public String getAnalytic_id() {
		return analytic_id;

	}
	public void setAnalytic_id(String analytic_id) {
		this.analytic_id = analytic_id;
	}

	public String getAnalytic_version() {
		return analytic_version;
	}
	public void setAnalytic_version(String analytic_version) {
		this.analytic_version = analytic_version;
	}	
	public boolean isValidationRequest() {
		return validationRequest;
	}
	public void setValidationRequest(boolean validationRequest) {
		this.validationRequest = validationRequest;
	}
	public String getAsset_id() {
		return asset_id;
	}
	public void setAsset_id(String asset_id) {
		this.asset_id = asset_id;
	}
	@Override
	public String toString() {
		return "ValidateAnalyticRequestDTO [analyticName=" + analytic_name + ", analyticId=" + analytic_id
				+ ", analyticVersion=" + analytic_version + ", config=" + config + ", assetId="
				+ asset_id + ", validationRequest=" + validationRequest + "]";
	}
	
}
