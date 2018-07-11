package com.apm.was.entity;

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

@Entity
@Table(name="ANALYTIC_RESULT_REFERENCE_TAG")
public class AnalyticResultReferenceTagEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="analytic_result_ref_tag_id_seq")
	@SequenceGenerator(
			name="analytic_result_ref_tag_id_seq",
			sequenceName="analytic_result_ref_tag_id_seq",
			allocationSize=1
			)
	@Column(name="ANALYTIC_RESULT_REFERNCE_TAG_ID")
	private long analyticResultReferenceTagId;

	@OneToOne
	@JoinColumn(name = "ANALYTIC_TAG_MAPPING_ID", referencedColumnName = "ANALYTIC_TAG_MAPPING_ID")
	@JsonIgnore
	private AnalyticTagMappingEntity analyticTagMappingEntity;

	@Column(name="TAG_VALUE", columnDefinition="text")
	private String tagValue;

	@Column(name="TAG_DESCRIPTION", columnDefinition="text")
	private String tagDescription;

	public long getAnalyticResultReferenceTagId() {
		return analyticResultReferenceTagId;
	}

	public void setAnalyticResultReferenceTagId(long analyticResultReferenceTagId) {
		this.analyticResultReferenceTagId = analyticResultReferenceTagId;
	}

	public AnalyticTagMappingEntity getAnalyticTagMappingEntity() {
		return analyticTagMappingEntity;
	}

	public void setAnalyticTagMappingEntity(
			AnalyticTagMappingEntity analyticTagMappingEntity) {
		this.analyticTagMappingEntity = analyticTagMappingEntity;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public String getTagDescription() {
		return tagDescription;
	}

	public void setTagDescription(String tagDescription) {
		this.tagDescription = tagDescription;
	}
}
