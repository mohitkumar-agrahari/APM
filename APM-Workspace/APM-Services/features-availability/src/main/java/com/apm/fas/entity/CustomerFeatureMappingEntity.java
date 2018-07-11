package com.apm.fas.entity;

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


@Entity
@Table(name="CUSTOMER_FEATURE_MAPPING")
public class CustomerFeatureMappingEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="customer_feature_mapping_id_seq")
    @SequenceGenerator(
                  name="customer_feature_mapping_id_seq",
                  sequenceName="customer_feature_mapping_id_seq",
                  allocationSize=1
                  )
	@Column(name="customer_feature_mapping_id")
	private long customerFeatureMappingId;
	
	@OneToOne
	@JoinColumn(name="feature_id", referencedColumnName="feature_id")
	private APMFeaturesEntity sstFeaturesEntity;
	
	@Column(name="is_enabled")
	private boolean isEnabled;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_group_id", referencedColumnName="user_group_id")
	private UserGroupEntity userGroupEntity;
	
	public long getCustomerFeatureMappingId() {
		return customerFeatureMappingId;
	}
	public void setCustomerFeatureMappingId(long customerFeatureMappingId) {
		this.customerFeatureMappingId = customerFeatureMappingId;
	}
	
	public APMFeaturesEntity getSstFeaturesEntity() {
		return sstFeaturesEntity;
	}
	public void setSstFeaturesEntity(APMFeaturesEntity sstFeaturesEntity) {
		this.sstFeaturesEntity = sstFeaturesEntity;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public UserGroupEntity getUserGroupEntity() {
		return userGroupEntity;
	}
	public void setUserGroupEntity(UserGroupEntity userGroupEntity) {
		this.userGroupEntity = userGroupEntity;
	}
}
