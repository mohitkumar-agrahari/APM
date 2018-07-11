package com.apm.was.asme.dto;

import java.util.List;

public class SaveInputDTO {
	
	private Boolean isrealtime;
	private String startDate;
	private String endDate;
	private List<ASMEInputDTO> parameters;
	public Boolean getIsrealtime() {
		return isrealtime;
	}
	public void setIsrealtime(Boolean isrealtime) {
		this.isrealtime = isrealtime;
	}
	public List<ASMEInputDTO> getParameters() {
		return parameters;
	}
	public void setParameters(List<ASMEInputDTO> parameters) {
		this.parameters = parameters;
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
	

}
