package com.apm.datarw.dto;

import java.util.Vector;

public class HistorianDataDTO {
	private String tagName;
	private Vector<Long> dateTime;
	private Vector<String> dataValues;
	private Vector<String> qualityFlags;
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public Vector<Long> getDateTime() {
		return dateTime;
	}
	public void setDateTime(Vector<Long> dateTime) {
		this.dateTime = dateTime;
	}
	public Vector<String> getDataValues() {
		return dataValues;
	}
	public void setDataValues(Vector<String> dataValues) {
		this.dataValues = dataValues;
	}
	public Vector<String> getQualityFlags() {
		return qualityFlags;
	}
	public void setQualityFlags(Vector<String> qualityFlags) {
		this.qualityFlags = qualityFlags;
	}
	@Override
	public String toString() {
		return "HistorianDataDTO [tagName=" + tagName + ", dateTime=" + dateTime + ", dataValues=" + dataValues
				+ ", qualityFlags=" + qualityFlags + "]";
	}
	
}
