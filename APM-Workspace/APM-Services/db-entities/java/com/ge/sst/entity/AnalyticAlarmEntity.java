package com.ge.sst.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ge.sst.helper.entities.AuditEntity;

/**
 * Analytic Alarm Entity
 * Responsible for storing the alarms.
 * 
 * @author Ankita Srivastava
 *
 */
@Entity(name="analyticAlarmEntity")
@Table(name="ANALYTIC_ALARMS")
public class AnalyticAlarmEntity  implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="analytic_alarm_id_seq")
	@SequenceGenerator(
			name="analytic_alarm_id_seq",
			sequenceName="analytic_alarm_id_seq",
			allocationSize=1
			)
	@Column(name="ALARM_ID")
	private Long alarmId;

	@Column(name="ALARM_TIMESTAMP")
	private Date alarmTimestamp;

	@Column(name="ALARM_NAME")
	private String alarmName;
	
	@Column(name="SEVERITY")
	private String severity;
	
	@ManyToOne
	@JoinColumn(name = "analytic_asset_mapping_id", referencedColumnName = "analytic_asset_mapping_id")
	@JsonIgnore
	private AnalyticAssetMappingEntity analyticAssetMappingEntity;
	
	public Date getAlarmTimestamp() {
		return alarmTimestamp;
	}
	public void setAlarmTimestamp(Date alarmTimestamp) {
		this.alarmTimestamp = alarmTimestamp;
	}
	public String getAlarmName() {
		return alarmName;
	}
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public Long getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(Long alarmId) {
		this.alarmId = alarmId;
	}
	public AnalyticAssetMappingEntity getAnalyticAssetMappingEntity() {
		return analyticAssetMappingEntity;
	}
	public void setAnalyticAssetMappingEntity(AnalyticAssetMappingEntity analyticAssetMappingEntity) {
		this.analyticAssetMappingEntity = analyticAssetMappingEntity;
	}

	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
	@Override
	public String toString() {
		return "AnalyticAlarmEntity [alarmId=" + alarmId + ", alarmTimestamp=" + alarmTimestamp + ", alarmName="
				+ alarmName + "]";
	}
	
}
