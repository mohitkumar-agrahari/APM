package com.apm.was.dto;

import java.util.List;

public class AssetModelDTO {
	private String label;
	private String id;
	private List<AssetModelDTO> children;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<AssetModelDTO> getChildren() {
		return children;
	}
	public void setChildren(List<AssetModelDTO> children) {
		this.children = children;
	}
	
}
