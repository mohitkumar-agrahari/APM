package com.apm.datarw.dto;

public class AnalyticParameterDTO {
	private String name;
	private String unit;
	private Object value;
	private String description;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "AnalyticParameterDTO [name=" + name + ", unit=" + unit + ", value=" + value + ", description="
				+ description + "]";
	}
	
	
}
