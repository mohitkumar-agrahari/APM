package com.ge.sst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ge.sst.entity.AnalyticResultState;
@Repository
public interface IAnalyticResultStateRepo extends JpaRepository<AnalyticResultState,Long>{

	/*AnalyticResultState getAnalyticResultStateByEquipmentAnalyticMappingEntityEquipmentAnalyticMappingId(
			long equipmentAnalyticMappingId);

	AnalyticResultState getAnalyticResultStateByEquipmentAnalyticMappingEntityAnalyticInfoEntityAnalyticIdAndEquipmentAnalyticMappingEntityEquipmentInfoEntityEquipmentInfoIdAndEquipmentAnalyticMappingEntityAnalyticInfoEntityIsStateMaintained(
			Long analyticId, String equipmentId, short s);
	*/
	/*@Query("select resultState from analyticResultState resultState where resultState."
			+ "equipmentAnalyticMappingEntity.equipmentInfoEntity.tsn=?1 and "
			+ "resultState.equipmentAnalyticMappingEntity.analyticInfoEntity.analyticId=?2")
	public AnalyticResultState getEquipmentEntity(String tsn, Long analyticId);
*/
	@Query("SELECT resultState FROM analyticResultState resultState WHERE resultState.analyticAssetMappingEntity.analyticInfoEntity.analyticId=?1 AND resultState.analyticAssetMappingEntity.assetId=?2")
	AnalyticResultState getResultStateEntityValue(long analyticId, String assetId);

}
