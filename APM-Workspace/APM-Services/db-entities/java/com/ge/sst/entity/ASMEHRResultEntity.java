package com.ge.sst.entity;

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

@Entity(name="asmeHrResult")
@Table(name="ASME_HR_RESULT")
public class ASMEHRResultEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="asme_result_id_seq")
	@SequenceGenerator(
			name="asme_result_id_seq",
			sequenceName="asme_result_id_seq",
			allocationSize=1
			)
	@Column(name="ASME_RESULT_ID")
	private Long asmeResultId;
	
	@ManyToOne
	@JoinColumn(name = "ASME_REF_TAGS_ID", referencedColumnName = "ASME_REF_TAGS_ID")
	@JsonIgnore
	private ASMEReferenceTagsEntity asmeReferenceTags;
	
	@Column(name="ASME_TAG_VALUE")
	private String asme_tag_value;
	
	@Column(name="START_DATE")
	private String  startDate;
	
	@Column(name="END_DATE")
	private String  endDate;
	
	@Column(name="IS_REAL_TIME")
	private Boolean isRealTime;

	public Long getAsmeResultId() {
		return asmeResultId;
	}

	public void setAsmeResultId(Long asmeResultId) {
		this.asmeResultId = asmeResultId;
	}

	public ASMEReferenceTagsEntity getAsmeReferenceTags() {
		return asmeReferenceTags;
	}

	public void setAsmeReferenceTags(ASMEReferenceTagsEntity asmeReferenceTags) {
		this.asmeReferenceTags = asmeReferenceTags;
	}

	public String getAsme_tag_value() {
		return asme_tag_value;
	}

	public void setAsme_tag_value(String asme_tag_value) {
		this.asme_tag_value = asme_tag_value;
	}

	

	public Boolean getIsRealTime() {
		return isRealTime;
	}

	public void setIsRealTime(Boolean isRealTime) {
		this.isRealTime = isRealTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
