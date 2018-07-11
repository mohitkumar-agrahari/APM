package com.apm.was.dto;

import java.util.List;

import org.json.simple.JSONObject;

public class OuttagResDTO {

	private String tag;
	private List<JSONObject> value;
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public List<JSONObject> getValue() {
		return value;
	}
	public void setValue(List<JSONObject> value) {
		this.value = value;
	}
	
	
	
}
