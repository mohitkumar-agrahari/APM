package com.ge.sst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.sst.entity.AnalyticTagMappingEntity;
import com.ge.sst.entity.TagInfoEntity;





@Repository
public interface IAnalyticTagMappingRepo extends JpaRepository<AnalyticTagMappingEntity,Long>{

	@Query("SELECT tagMapping FROM AnalyticTagMappingEntity tagMapping WHERE tagMapping.analyticInfoEntity.analyticId=?1")
	List<AnalyticTagMappingEntity> fetchByAnalyticId(Long analyticId);
	
	@Query("SELECT tagMapping FROM AnalyticTagMappingEntity tagMapping WHERE tagMapping.analyticInfoEntity.analyticId=?1 and tagMapping.tagInfoEntity.tagType=?2")
	List<AnalyticTagMappingEntity> fetchByIdandType(Long analyticId, String singleTagType);

	

}
