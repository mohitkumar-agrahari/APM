package com.apm.was.dto;

import java.util.List;

public class ImageDetailsDTO {
	private String levelDetailsName;
	private List<AssetImageDTO> assetDetails;
	public String getLevelDetailsName() {
		return levelDetailsName;
	}
	public void setLevelDetailsName(String levelDetailsName) {
		this.levelDetailsName = levelDetailsName;
	}
	public List<AssetImageDTO> getAssetDetails() {
		return assetDetails;
	}
	public void setAssetDetails(List<AssetImageDTO> assetDetails) {
		this.assetDetails = assetDetails;
	}

}
