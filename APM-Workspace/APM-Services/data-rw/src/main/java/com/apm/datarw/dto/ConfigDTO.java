package com.apm.datarw.dto;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.json.simple.JSONObject;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;


public class ConfigDTO {
	
	public ConfigDTO() {
		
	}
	
	//New tag as per condenser analytics begins here
	private String analytic_name;
	private String asset_id;
	private Long num_tag_alarm;
	private Long mode_tag_threshold;
	private Long persistent_time;
	private Map<String, Object> model;
	private String mode_tag;
	
	// ends here
	
	@NotNull(message="'Tag Aliases' cannot be null.")
	private JSONObject tag_aliases;
	private Map<String, Object> parameters;
	@NotNull(message="'Input Tags' cannot be null.")
	private List<TagInfoDTO> input_tags;
	@NotNull(message="'Output Tags' cannot be null.")
	private List<TagInfoDTO> output_tags;
	
	public JSONObject getTag_aliases() {
		return tag_aliases;
	}
	public void setTag_aliases(JSONObject tag_aliases) {
		this.tag_aliases = tag_aliases;
	}
	public Map<String, Object> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	public List<TagInfoDTO> getInput_tags() {
		return input_tags;
	}
	public void setInput_tags(List<TagInfoDTO> input_tags) {
		this.input_tags = input_tags;
	}
	public List<TagInfoDTO> getOutput_tags() {
		return output_tags;
	}
	public void setOutput_tags(List<TagInfoDTO> output_tags) {
		this.output_tags = output_tags;
	}
	public Long getNum_tag_alarm() {
		return num_tag_alarm;
	}
	public void setNum_tag_alarm(Long num_tag_alarm) {
		this.num_tag_alarm = num_tag_alarm;
	}
	public Long getMode_tag_threshold() {
		return mode_tag_threshold;
	}
	public void setMode_tag_threshold(Long mode_tag_threshold) {
		this.mode_tag_threshold = mode_tag_threshold;
	}
	public Long getPersistent_time() {
		return persistent_time;
	}
	public void setPersistent_time(Long persistent_time) {
		this.persistent_time = persistent_time;
	}
	public Map<String, Object> getModel() {
		return model;
	}
	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
	public String getMode_tag() {
		return mode_tag;
	}
	public void setMode_tag(String mode_tag) {
		this.mode_tag = mode_tag;
	}
	public String getAnalytic_name() {
		return analytic_name;
	}
	public void setAnalytic_name(String analytic_name) {
		this.analytic_name = analytic_name;
	}
	public String getAsset_id() {
		return asset_id;
	}
	public void setAsset_id(String asset_id) {
		this.asset_id = asset_id;
	}

}
