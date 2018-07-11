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
/**
 * @author Ankita Srivastava
 *
 */
@Entity
@Table(name="TAG_INFO")
public class TagInfoEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tag_info_id_seq")
    @SequenceGenerator(
                  name="tag_info_id_seq",
                  sequenceName="tag_info_id_seq",
                  allocationSize=1
                  )
	@Column(name="TAG_ID")
	private long tagId;
	
	@Column(name="TAG_NAME")
	private String tagName;
	
	@Column(name="TAG_TYPE")
	private String tagType;
	
	@Column(name="TAG_SOURCE")
	private String tagSource;
	
	@Column(name="TAG_DESCRIPTION")
	private String tagDescription;
	
	@Column(name="TAG_ALIASES")
	private String tagAliases;
	
	@Column(name="UNIT")
	private String unit;
	
	//@OneToOne(cascade=CascadeType.ALL)
	@OneToOne(mappedBy="tagInfoEntity")
	//@JoinColumn(name = "ANALYTIC_TAG_MAPPING_ID", referencedColumnName = "ANALYTIC_TAG_MAPPING_ID")
	private AnalyticTagMappingEntity analyticTagMappingEntity;

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public String getTagSource() {
		return tagSource;
	}

	public void setTagSource(String tagSource) {
		this.tagSource = tagSource;
	}

	public AnalyticTagMappingEntity getAnalyticTagMappingEntity() {
		return analyticTagMappingEntity;
	}

	public void setAnalyticTagMappingEntity(
			AnalyticTagMappingEntity analyticTagMappingEntity) {
		this.analyticTagMappingEntity = analyticTagMappingEntity;
	}

	public String getTagDescription() {
		return tagDescription;
	}

	public void setTagDescription(String tagDescription) {
		this.tagDescription = tagDescription;
	}

	public String getTagAliases() {
		return tagAliases;
	}

	public void setTagAliases(String tagAliases) {
		this.tagAliases = tagAliases;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
	
	
}
