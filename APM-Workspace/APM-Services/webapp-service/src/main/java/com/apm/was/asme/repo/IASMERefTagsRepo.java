package com.apm.was.asme.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.apm.was.asme.entity.ASMEReferenceTagsEntity;


public interface IASMERefTagsRepo extends JpaRepository<ASMEReferenceTagsEntity,Long>{

	List<ASMEReferenceTagsEntity> getAsmeReferenceTagsByAnalyticInfoEntityAnalyticId(Long analyticId);


	List<ASMEReferenceTagsEntity> getAsmeReferenceTagsByAsmeCalculationHeader(String uniqueTagHeader);

	@Query("Select distinct(arft.tagType)FROM asmeReferenceTags arft where arft.analyticInfoEntity.analyticId=?1")
	List<String> fetchTagType(Long analyticId);
	
	@Query("SELECT distinct(arft.asmeCalculationHeader) FROM asmeReferenceTags arft where arft.analyticInfoEntity.analyticId=?1 and arft.tagType=?2")
	List<String> fetchTagHeaderByTagType(Long analyticId, String uniquetagType);

	

	

}
