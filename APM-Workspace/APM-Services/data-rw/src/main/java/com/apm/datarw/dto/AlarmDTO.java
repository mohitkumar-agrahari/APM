package com.apm.datarw.dto;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class AlarmDTO {
	@JsonProperty("asset_id")
	private String assetId;
	
	@JsonProperty("serverity")
	private String severity;
	
	@JsonProperty("scan_group")
	private Map<String, String> scanGroup;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("datetime")
	private String datetime;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public Map<String, String> getScanGroup() {
		return scanGroup;
	}

	public void setScanGroup(Map<String, String> scanGroup) {
		this.scanGroup = scanGroup;
	}

	public String getAlarmName() {
		return name;
	}

	public void setAlarmName(String alarmName) {
		this.name = alarmName;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	@Override
	public String toString() {
		return "AlarmDTO [assetId=" + assetId + ", severity=" + severity + ", scanGroup=" + scanGroup + ", alarmName="
				+ name + ", datetime=" + datetime + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
