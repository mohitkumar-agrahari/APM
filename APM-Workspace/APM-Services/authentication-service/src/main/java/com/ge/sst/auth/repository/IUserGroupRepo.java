package com.ge.sst.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ge.sst.auth.entity.UserGroupEntity;

@Repository
public interface IUserGroupRepo extends JpaRepository<UserGroupEntity, Long>{

}
