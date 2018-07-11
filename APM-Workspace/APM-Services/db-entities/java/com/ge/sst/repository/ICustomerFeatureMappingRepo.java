package com.ge.sst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.sst.entity.CustomerFeatureMappingEntity;

@Repository
public interface ICustomerFeatureMappingRepo extends JpaRepository<CustomerFeatureMappingEntity, Long> {
	
	@Query("SELECT cfme from CustomerFeatureMappingEntity cfme WHERE cfme.isEnabled=true and cfme.userGroupEntity.userGroupId=?1 ORDER BY cfme.sstFeaturesEntity.navSequence")
	public List<CustomerFeatureMappingEntity> getEnabledFeatures(long userGroupId);
}
