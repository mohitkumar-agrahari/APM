package com.apm.was.dto;

public class AlarmCountDTO {

	private String levelName;
	private Long alarmCount;
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Long getAlarmCount() {
		return alarmCount;
	}
	public void setAlarmCount(Long alarmCount) {
		this.alarmCount = alarmCount;
	}
	
}
