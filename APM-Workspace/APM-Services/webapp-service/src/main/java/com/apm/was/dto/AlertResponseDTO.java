package com.apm.was.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlertResponseDTO {
	
	@JsonProperty("alert_name")
	private String alertName;
	
	@JsonProperty("severity")
	private String severity;
	
	@JsonProperty("alert_source")
	private String alertSource;
    
	@JsonProperty("event_time")
	private String event_Time;

	public String getAlertName() {
		return alertName;
	}

	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getAlertSource() {
		return alertSource;
	}

	public void setAlertSource(String alertSource) {
		this.alertSource = alertSource;
	}

	public String getEvent_Time() {
		return event_Time;
	}

	public void setEvent_Time(String event_Time) {
		this.event_Time = event_Time;
	}


	


	

}
