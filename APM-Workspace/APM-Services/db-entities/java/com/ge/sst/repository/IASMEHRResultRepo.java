package com.ge.sst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ge.sst.entity.ASMECoalCompInputValuesEntity;
import com.ge.sst.entity.ASMEHRResultEntity;

public interface IASMEHRResultRepo extends JpaRepository<ASMEHRResultEntity,Long>{

	@Query("select max(ar.startDate) from asmeHrResult ar where ar.asmeReferenceTags.asmeRefTagsId=?1 and ar.isRealTime=?2")
	String getLatestEntry(Long asmeRefTagsId,Boolean isRealFlag);

	ASMEHRResultEntity getAsmeHrResultByStartDate(String latestDateTime);

	ASMEHRResultEntity getAsmeHrResultByStartDateAndAsmeReferenceTagsAsmeRefTagsId(String latestDateTime,
			Long asmeRefTagsId);

	ASMEHRResultEntity getAsmeHrResultByStartDateAndAsmeReferenceTagsAsmeRefTagsIdAndIsRealTime(String latestDateTime,
			Long asmeRefTagsId, boolean b);





	

}
