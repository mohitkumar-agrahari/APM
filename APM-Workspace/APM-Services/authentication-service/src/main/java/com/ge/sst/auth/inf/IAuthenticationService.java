package com.ge.sst.auth.inf;

import com.ge.sst.auth.dto.LoginCredentialsDTO;
import com.ge.sst.auth.dto.UserDetailsDTO;

public interface IAuthenticationService {

	int isExistingUser(String userName);

	UserDetailsDTO validateUser(LoginCredentialsDTO loginCredentialsDTO);

}
