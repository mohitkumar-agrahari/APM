package com.apm.was.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ANALYTIC_TAG_MAPPING")
public class AnalyticTagMappingEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="analytic_tag_mapping_id_seq")
	@SequenceGenerator(
			name="analytic_tag_mapping_id_seq",
			sequenceName="analytic_tag_mappiANALYTIC_TAG_MAPPING_IDng_id_seq",
			allocationSize=1
			)
	@Column(name="ANALYTIC_TAG_MAPPING_ID")
	private long analyticTagMappingId;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "ANALYTIC_ID", referencedColumnName = "ANALYTIC_ID")
	@JsonIgnore
	private AnalyticInfoEntity analyticInfoEntity;

	@OneToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name = "TAG_ID", referencedColumnName = "TAG_ID")
	@JoinColumn(name = "TAG_ID")
	@JsonIgnore
	private TagInfoEntity tagInfoEntity;

	@OneToOne(mappedBy="analyticTagMappingEntity", cascade=CascadeType.ALL)
	private AnalyticResultReferenceTagEntity analyticResultReferenceTagEntity;

	/*@OneToOne(mappedBy="analyticTagMappingEntity", cascade=CascadeType.ALL)
	private AnalyticResultTimeseriesTagEntity analyticResultTimeseriesTagEntity;

*/
	public long getAnalyticTagMappingId() {
		return analyticTagMappingId;
	}

	public void setAnalyticTagMappingId(long analyticTagMappingId) {
		this.analyticTagMappingId = analyticTagMappingId;
	}

	public AnalyticInfoEntity getAnalyticInfoEntity() {
		return analyticInfoEntity;
	}

	public void setAnalyticInfoEntity(AnalyticInfoEntity analyticInfoEntity) {
		this.analyticInfoEntity = analyticInfoEntity;
	}

	public TagInfoEntity getTagInfoEntity() {
		return tagInfoEntity;
	}

	public void setTagInfoEntity(TagInfoEntity tagInfoEntity) {
		this.tagInfoEntity = tagInfoEntity;
	}

	public AnalyticResultReferenceTagEntity getAnalyticResultReferenceTagEntity() {
		return analyticResultReferenceTagEntity;
	}

	public void setAnalyticResultReferenceTagEntity(
			AnalyticResultReferenceTagEntity analyticResultReferenceTagEntity) {
		this.analyticResultReferenceTagEntity = analyticResultReferenceTagEntity;
	}
/*
	public AnalyticResultTimeseriesTagEntity getAnalyticResultTimeseriesTagEntity() {
		return analyticResultTimeseriesTagEntity;
	}

	public void setAnalyticResultTimeseriesTagEntity(
			AnalyticResultTimeseriesTagEntity analyticResultTimeseriesTagEntity) {
		this.analyticResultTimeseriesTagEntity = analyticResultTimeseriesTagEntity;
	}*/


}
