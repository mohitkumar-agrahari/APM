package com.ge.sst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ge.sst.entity.ASMECoalCompInputValuesEntity;

public interface IASMECoalCompInputValueRepo extends JpaRepository<ASMECoalCompInputValuesEntity,Long>{

	
	@Query("select max(aip.startDate) from asmeCoalCompInputValues aip where aip.asmeReferenceTags.asmeRefTagsId=?1 and aip.isRealTime=?2")
	String getLatestEntry(Long asmeRefTagsId,Boolean isRealFlag);

	ASMECoalCompInputValuesEntity getAsmeCoalCompInputValuesByStartDate(String startDate);



	ASMECoalCompInputValuesEntity getAsmeCoalCompInputValuesByStartDateAndAsmeReferenceTagsAsmeRefTagsIdAndIsRealTime(
			String startDate, Long id, Boolean isRealTimeFlag);

	ASMECoalCompInputValuesEntity getAsmeCoalCompInputValuesByStartDateAndAsmeReferenceTagsAsmeRefTagsId(
			String latestDateTime, Long asmeRefTagsId);

	

	

}
