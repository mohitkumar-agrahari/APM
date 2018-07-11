package com.apm.datarw.dto;

import org.springframework.web.multipart.MultipartFile;

public class PythodAddAnalyticDTO {
	private String analyticName;
	private String analyticVersion;
	private MultipartFile analyticPackage;
	public String getAnalyticName() {
		return analyticName;
	}
	public void setAnalyticName(String analyticName) {
		this.analyticName = analyticName;
	}
	public String getAnalyticVersion() {
		return analyticVersion;
	}
	public void setAnalyticVersion(String analyticVersion) {
		this.analyticVersion = analyticVersion;
	}
	public MultipartFile getAnalyticPackage() {
		return analyticPackage;
	}
	public void setAnalyticPackage(MultipartFile analyticPackage) {
		this.analyticPackage = analyticPackage;
	}
	@Override
	public String toString() {
		return "PythodAddAnalyticDTO [analyticName=" + analyticName + ", analyticVersion=" + analyticVersion
				+ ", analyticPackage=" + analyticPackage + "]";
	}

}
