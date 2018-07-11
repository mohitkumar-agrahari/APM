package com.ge.sst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.sst.entity.SSTFeaturesEntity;

@Repository
public interface ISSTFeaturesRepo extends JpaRepository<SSTFeaturesEntity, Long>{
	
	

}
