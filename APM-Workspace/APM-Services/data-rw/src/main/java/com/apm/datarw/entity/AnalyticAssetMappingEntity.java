package com.apm.datarw.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ANALYTIC_ASSET_MAPPING", uniqueConstraints=@UniqueConstraint(columnNames={"analytic_id", "asset_id"}))
public class AnalyticAssetMappingEntity {
	
	 @Id
	    @GeneratedValue(strategy= GenerationType.IDENTITY, generator="analytic_asset_mapping_id_seq")
	    @SequenceGenerator(
	            name="analytic_asset_mapping_id_seq",
	            sequenceName="analytic_asset_mapping_id_seq",
	            allocationSize=1
	            )
	 @Column(name="analytic_asset_mapping_id")
	 private long analyticAssetMappingId;

	 
	 
	@ManyToOne
	@JoinColumn(name = "ANALYTIC_ID", referencedColumnName = "ANALYTIC_ID")
	@JsonIgnore
	private AnalyticInfoEntity analyticInfoEntity;
	 
	@Column(name="asset_id")
	private String assetId;
	
	@Column(name="asset_name")
	private String assetName;

	@Column(name="asset_type")
	private String assetType;

	@OneToMany(mappedBy="analyticAssetMappingEntity", cascade=CascadeType.ALL)
	private List<AnalyticAlarmEntity> analyticAlarmEntities;

	 
	public long getAnalyticAssetMappingId() {
		return analyticAssetMappingId;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public void setAnalyticAssetMappingId(long analyticAssetMappingId) {
		this.analyticAssetMappingId = analyticAssetMappingId;
	}
	public String getAssetId() {
		return assetId;
	}
	public AnalyticInfoEntity getAnalyticInfoEntity() {
		return analyticInfoEntity;
	}
	public void setAnalyticInfoEntity(AnalyticInfoEntity analyticInfoEntity) {
		this.analyticInfoEntity = analyticInfoEntity;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public List<AnalyticAlarmEntity> getAnalyticAlarmEntities() {
		return analyticAlarmEntities;
	}
	public void setAnalyticAlarmEntities(List<AnalyticAlarmEntity> analyticAlarmEntities) {
		this.analyticAlarmEntities = analyticAlarmEntities;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
}
