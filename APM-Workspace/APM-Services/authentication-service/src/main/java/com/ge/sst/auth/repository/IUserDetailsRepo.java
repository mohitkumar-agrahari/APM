package com.ge.sst.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.sst.auth.entity.UserDetailsEntity;

@Repository
public interface IUserDetailsRepo extends JpaRepository<UserDetailsEntity, Long>{

	@Query("SELECT count(user) FROM UserDetailsEntity user where user.userName=?1")
	int isExistingUser(String userName);

	@Query("SELECT count(user) FROM UserDetailsEntity user where user.userName=?1 and user.userPassword=?2")
	int validateUser(String userName, String password);

	@Query("SELECT user FROM UserDetailsEntity user where user.userName=?1")
	UserDetailsEntity getUserByUserName(String userName);

}
