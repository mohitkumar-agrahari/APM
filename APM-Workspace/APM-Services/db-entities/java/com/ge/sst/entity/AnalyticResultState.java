package com.ge.sst.entity;

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
 * @author Ankita Srivastava
 *
 */
@Entity(name="analyticResultState")
@Table(name="ANALYTIC_RESULT_STATE")
public class AnalyticResultState {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="analytic_state_id_seq")
	@SequenceGenerator(
			name="analytic_state_id_seq",
			sequenceName="analytic_state_id_seq",
			allocationSize=1
			)
	@Column(name="ANALYTIC_RESULT_STATE_ID")
	private long analyticResultStateId;

	/*@OneToOne
	@JoinColumn(name = "EQUIPMENT_ANALYTIC_MAPPING_ID", referencedColumnName = "EQUIPMENT_ANALYTIC_MAPPING_ID")
	@JsonIgnore
	private EquipmentAnalyticMappingEntity equipmentAnalyticMappingEntity;
*/
	@OneToOne
	@JoinColumn(name = "analytic_asset_mapping_id", referencedColumnName = "analytic_asset_mapping_id")
	@JsonIgnore
	private AnalyticAssetMappingEntity analyticAssetMappingEntity;

	
	@Column(name="VALUE", columnDefinition="text")
	private String value;

	public AnalyticAssetMappingEntity getAnalyticAssetMappingEntity() {
		return analyticAssetMappingEntity;
	}
	public void setAnalyticAssetMappingEntity(AnalyticAssetMappingEntity analyticAssetMappingEntity) {
		this.analyticAssetMappingEntity = analyticAssetMappingEntity;
	}

	public long getAnalyticResultStateId() {
		return analyticResultStateId;
	}


	public void setAnalyticResultStateId(long analyticResultStateId) {
		this.analyticResultStateId = analyticResultStateId;
	}

/*
	public EquipmentAnalyticMappingEntity getEquipmentAnalyticMappingEntity() {
		return equipmentAnalyticMappingEntity;
	}


	public void setEquipmentAnalyticMappingEntity(EquipmentAnalyticMappingEntity equipmentAnalyticMappingEntity) {
		this.equipmentAnalyticMappingEntity = equipmentAnalyticMappingEntity;
	}
*/

	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "AnalyticResultState [analyticResultStateId=" + analyticResultStateId + ", analyticAssetMappingEntity="
				+ analyticAssetMappingEntity + ", value=" + value + "]";
	}
}
