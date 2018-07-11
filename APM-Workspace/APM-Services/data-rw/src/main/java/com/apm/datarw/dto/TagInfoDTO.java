package com.apm.datarw.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagInfoDTO {
	@JsonProperty("unit")
	private String unit;
	@JsonProperty("name")
	private String name;
	@JsonProperty("value")
	private String value;
	@JsonProperty("description")
	private String description;
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
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
		return "TagInfoDTO [unit=" + unit + ", name=" + name + ", value=" + value + ", description=" + description
				+ "]";
	}


}
