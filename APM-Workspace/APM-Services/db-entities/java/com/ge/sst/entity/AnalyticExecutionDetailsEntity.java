package com.ge.sst.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * Analytic Execution Details Entity
 * Responsible for storing data related to analytic execution.
 * 
 * @author Ankita Srivastava
 *
 */
//TODO Delete this table
@Entity
@Table(name="ANALYTIC_EXECUTION_DETAILS")
public class AnalyticExecutionDetailsEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="analytic_execution_details_id_seq")
	@SequenceGenerator(
			name="analytic_execution_details_id_seq",
			sequenceName="analytic_execution_details_id_seq",
			allocationSize=1
			)
	@Column(name="ANALYTIC_EXECUTION_DETAILS_ID")
	private long analyticExecutionDetailsId;


	@OneToOne
	@JoinColumn(name = "EQUIPMENT_ANALYTIC_MAPPING_ID", referencedColumnName = "EQUIPMENT_ANALYTIC_MAPPING_ID")
	@JsonIgnore
	private EquipmentAnalyticMappingEntity equipmentAnalyticMappingEntity;

	

	@Column(name="EXECUTION_FOR_UNITS")
	private long executionForUnits;

	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="IS_REAL_TIME_EXECUTION")
	private short isRealTimeExecution;

	public long getAnalyticExecutionDetailsId() {
		return analyticExecutionDetailsId;
	}

	public void setAnalyticExecutionDetailsId(long analyticExecutionDetailsId) {
		this.analyticExecutionDetailsId = analyticExecutionDetailsId;
	}

	public EquipmentAnalyticMappingEntity getEquipmentAnalyticMappingEntity() {
		return equipmentAnalyticMappingEntity;
	}

	public void setEquipmentAnalyticMappingEntity(
			EquipmentAnalyticMappingEntity equipmentAnalyticMappingEntity) {
		this.equipmentAnalyticMappingEntity = equipmentAnalyticMappingEntity;
	}

	public long getExecutionForUnits() {
		return executionForUnits;
	}

	public void setExecutionForUnits(long executionForUnits) {
		this.executionForUnits = executionForUnits;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public short getIsRealTimeExecution() {
		return isRealTimeExecution;
	}

	public void setIsRealTimeExecution(short isRealTimeExecution) {
		this.isRealTimeExecution = isRealTimeExecution;
	}




}
