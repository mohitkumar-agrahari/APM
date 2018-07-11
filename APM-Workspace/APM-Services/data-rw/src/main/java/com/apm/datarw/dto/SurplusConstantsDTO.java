package com.apm.datarw.dto;

import java.util.HashMap;
import java.util.Map;

public class SurplusConstantsDTO {
	private Map<String, Object> keyValuePairs;
	public SurplusConstantsDTO(){
		this.keyValuePairs = new HashMap<String, Object>();
	}
	public void setValue(String key, Object value){
		this.keyValuePairs.put(key, value);
	}
	public Map<String, Object> getSurplusConstants(){
		return this.keyValuePairs;
	}
	@Override
	public String toString() {
		return "SurplusConstantsDTO [keyValuePairs=" + keyValuePairs + "]";
	}
	
}
