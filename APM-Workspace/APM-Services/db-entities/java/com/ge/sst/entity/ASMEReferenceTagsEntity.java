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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="asmeReferenceTags")
@Table(name="ASME_REFERENCE_TAGS")
public class ASMEReferenceTagsEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="asme_ref_tags_id_seq")
	@SequenceGenerator(
			name="asme_ref_tags_id_seq",
			sequenceName="asme_ref_tags_id_seq",
			allocationSize=1
			)
	@Column(name="ASME_REF_TAGS_ID")
	private Long asmeRefTagsId;
	
	@Column(name="ASME_TAG_NAME")
	private String asmeTagName;
	
	@Column(name="ASME_TAG_CAPTION")
	private String asmeTagCaption;
	
	@Column(name="ASME_CALCULATION_HEADER")
	private String asmeCalculationHeader;
	
	@Column(name="UOM")
	private String uom;
	
	@Column(name="TAG_TYPE")
	private String tagType;
	
	@Column(name="DATA_TYPE")
	private String dataType;
	
	@ManyToOne
	@JoinColumn(name = "ANALYTIC_ID", referencedColumnName = "ANALYTIC_ID")
	@JsonIgnore
	private AnalyticInfoEntity analyticInfoEntity;
	
	@OneToMany(mappedBy = "asmeReferenceTags", cascade = CascadeType.ALL)
	private List<ASMECoalCompInputValuesEntity> asmeCoalCompInputValues;
	
	@OneToMany(mappedBy = "asmeReferenceTags", cascade = CascadeType.ALL)
	private List<ASMEHRResultEntity> asmeHrResult;
	

	

	public String getAsmeTagName() {
		return asmeTagName;
	}

	public void setAsmeTagName(String asmeTagName) {
		this.asmeTagName = asmeTagName;
	}

	public String getAsmeTagCaption() {
		return asmeTagCaption;
	}

	public void setAsmeTagCaption(String asmeTagCaption) {
		this.asmeTagCaption = asmeTagCaption;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public AnalyticInfoEntity getAnalyticInfoEntity() {
		return analyticInfoEntity;
	}

	public void setAnalyticInfoEntity(AnalyticInfoEntity analyticInfoEntity) {
		this.analyticInfoEntity = analyticInfoEntity;
	}

	public String getAsmeCalculationHeader() {
		return asmeCalculationHeader;
	}

	public void setAsmeCalculationHeader(String asmeCalculationHeader) {
		this.asmeCalculationHeader = asmeCalculationHeader;
	}

	public Long getAsmeRefTagsId() {
		return asmeRefTagsId;
	}

	public void setAsmeRefTagsId(Long asmeRefTagsId) {
		this.asmeRefTagsId = asmeRefTagsId;
	}

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public List<ASMECoalCompInputValuesEntity> getAsmeCoalCompInputValues() {
		return asmeCoalCompInputValues;
	}

	public void setAsmeCoalCompInputValues(List<ASMECoalCompInputValuesEntity> asmeCoalCompInputValues) {
		this.asmeCoalCompInputValues = asmeCoalCompInputValues;
	}

	public List<ASMEHRResultEntity> getAsmeHrResult() {
		return asmeHrResult;
	}

	public void setAsmeHrResult(List<ASMEHRResultEntity> asmeHrResult) {
		this.asmeHrResult = asmeHrResult;
	}

}
