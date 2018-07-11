package com.ge.sst.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * @author Ankita Srivastava
 *
 */
//TODO delete this entity
@Entity
@Table(name="EQUIPMENT_ANAlYTIC_MAPPING")
@NamedNativeQuery(name="EquipmentAnalyticMappingEntity.searchByTsn",
	query="select equipment_analytic_mapping.* from (equipment_analytic_mapping inner join equipment_info eq_info on "
			+ "equipment_analytic_mapping.equipment_info_id = eq_info.equipment_info_id) "
			+ "inner join analytic_info analytic_table on equipment_analytic_mapping.analytic_id=analytic_table.analytic_id where eq_info.tsn like "
			+ "(:assetId) and analytic_table.analytic_id=:analyticId", resultClass=EquipmentAnalyticMappingEntity.class)
public class EquipmentAnalyticMappingEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="equipment_analytic_mapping_id_seq")
	@SequenceGenerator(
			name="equipment_analytic_mapping_id_seq",
			sequenceName="equipment_analytic_mapping_id_seq",
			allocationSize=1
			)
	@Column(name="EQUIPMENT_ANALYTIC_MAPPING_ID")
	private long equipmentAnalyticMappingId;



	@ManyToOne
	@JoinColumn(name = "ANALYTIC_ID", referencedColumnName = "ANALYTIC_ID")
	@JsonIgnore
	private AnalyticInfoEntity analyticInfoEntity;

	@OneToOne(mappedBy="equipmentAnalyticMappingEntity", cascade=CascadeType.ALL)
	private AnalyticExecutionDetailsEntity analyticExecutionDetailsEntity;
	
	

	public AnalyticExecutionDetailsEntity getAnalyticExecutionDetailsEntity() {
		return analyticExecutionDetailsEntity;
	}

	public void setAnalyticExecutionDetailsEntity(
			AnalyticExecutionDetailsEntity analyticExecutionDetailsEntity) {
		this.analyticExecutionDetailsEntity = analyticExecutionDetailsEntity;
	}

	public long getEquipmentAnalyticMappingId() {
		return equipmentAnalyticMappingId;
	}

	public void setEquipmentAnalyticMappingId(long equipmentAnalyticMappingId) {
		this.equipmentAnalyticMappingId = equipmentAnalyticMappingId;
	}

	

	public AnalyticInfoEntity getAnalyticInfoEntity() {
		return analyticInfoEntity;
	}

	public void setAnalyticInfoEntity(AnalyticInfoEntity analyticInfoEntity) {
		this.analyticInfoEntity = analyticInfoEntity;
	}

	

	

}
