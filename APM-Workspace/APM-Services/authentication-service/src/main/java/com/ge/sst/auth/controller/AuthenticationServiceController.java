package com.ge.sst.auth.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ge.sst.auth.dto.LoginCredentialsDTO;
import com.ge.sst.auth.dto.ResponseDTO;
import com.ge.sst.auth.dto.UserDetailsDTO;
import com.ge.sst.auth.inf.IAuthenticationService;

@CrossOrigin("http://localhost:5010")
@RestController
public class AuthenticationServiceController {

	
	@Autowired
	IAuthenticationService authenticationService;
	/*@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "Hello World !!!";
		
	}*/
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseDTO login(@RequestBody LoginCredentialsDTO loginCredentialsDTO) {
		UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
		ResponseDTO responseDTO = new ResponseDTO();
		if(!Objects.isNull(loginCredentialsDTO)) {
			System.out.println("user name :: " + loginCredentialsDTO.getUserName());
			if((!loginCredentialsDTO.getUserName().trim().equals(""))) {
				int count = authenticationService.isExistingUser(loginCredentialsDTO.getUserName());
					if(count >= 1) {
						 userDetailsDTO = authenticationService.validateUser(loginCredentialsDTO);
						 if(!Objects.isNull(userDetailsDTO)) {
							 responseDTO.setResponse(userDetailsDTO);
							 return responseDTO;
						 }
						 else {
							 responseDTO.setResponse("Invalid Password");
							 return responseDTO;
						 }
					}
					else {
						 responseDTO.setResponse("User does not exist");
						return responseDTO;
					}
			}
			else {
				 responseDTO.setResponse("Please provide valid username");
				return responseDTO;
			}
		}
		return null;		
	}
}
