package com.apm.fas.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.apm.fas.entity.APMFeaturesEntity;

@Repository
public interface IAPMFeaturesRepo extends JpaRepository<APMFeaturesEntity, Long>{
}
