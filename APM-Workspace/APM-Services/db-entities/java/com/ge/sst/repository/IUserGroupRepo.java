package com.ge.sst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ge.sst.entity.UserGroupEntity;

@Repository
public interface IUserGroupRepo extends JpaRepository<UserGroupEntity, Long>{

}
