package com.apm.fas.dto;

public class CustomerFeatureMappingDTO {
	
	private long customerFeatureMappingId;
	private long featureId;
	private boolean isEnabled;
	private String featureName;
	
	
	public long getCustomerFeatureMappingId() {
		return customerFeatureMappingId;
	}
	public void setCustomerFeatureMappingId(long customerFeatureMappingId) {
		this.customerFeatureMappingId = customerFeatureMappingId;
	}
	public long getFeatureId() {
		return featureId;
	}
	public void setFeatureId(long featureId) {
		this.featureId = featureId;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	
	@Override
	public String toString() {
		return "CustomerFeatureMappingDTO [customerFeatureMappingId=" + customerFeatureMappingId + ", featureId="
				+ featureId + ", isEnabled=" + isEnabled + ", featureName=" + featureName + "]";
	}	
}
