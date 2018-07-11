package com.ge.sst.auth.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.sst.auth.dto.LoginCredentialsDTO;
import com.ge.sst.auth.dto.UserDetailsDTO;
import com.ge.sst.auth.entity.UserDetailsEntity;
import com.ge.sst.auth.inf.IAuthenticationService;
import com.ge.sst.auth.repository.IUserDetailsRepo;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService{
	
	private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	IUserDetailsRepo userDetailsRepo;
	
	@Override
	public int isExistingUser(String userName) {
		int count = 0;
		try {
			count = userDetailsRepo.isExistingUser(userName);
			if(count != 0) {
				log.info(String.format("user %s is existing user", userName));
			}
			else {
				log.error(String.format("user %s does not exist", userName));
			}
			
		}catch (Exception e) {
			count = 0;
			log.error(String.format("user %s does not exist", userName));
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public UserDetailsDTO validateUser(LoginCredentialsDTO loginCredentialsDTO) {
		UserDetailsDTO userDetailsDTO = null;
		try {
			int count = userDetailsRepo.validateUser(loginCredentialsDTO.getUserName(), loginCredentialsDTO.getPassword());
				if(count >=1 ) {
					UserDetailsEntity userDetailsEntity = userDetailsRepo.getUserByUserName(loginCredentialsDTO.getUserName());
						if(!Objects.isNull(userDetailsEntity)) {
							userDetailsDTO = new UserDetailsDTO();
							userDetailsDTO.setUserId(userDetailsEntity.getUserId());
							userDetailsDTO.setUserName(userDetailsEntity.getUserName());
							userDetailsDTO.setUserFirstName(userDetailsEntity.getUserFirstName());
							userDetailsDTO.setUserLastName(userDetailsEntity.getUserLastName());
							userDetailsDTO.setUserEmailId(userDetailsEntity.getUserEmailId());
							userDetailsDTO.setUserGroupId(userDetailsEntity.getUserGroupId().getUserGroupId());
						}
				}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return userDetailsDTO;
	}

}