package com.apm.fas.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apm.fas.dto.NavigationDTO;
import com.apm.fas.inf.IFeatureAvailabilityService;

@CrossOrigin("http://localhost:5010")
@RestController
public class FeatureAvailabilityController {
	
	private static final Logger log = LoggerFactory.getLogger(FeatureAvailabilityController.class);
	
	@Autowired
	IFeatureAvailabilityService iFeatureAvailabilityService;
	
	@RequestMapping(name="/getFeatures", method=RequestMethod.GET)
	public  List<NavigationDTO> getEnabledFeatures(@RequestParam long userGroupId) {
		System.out.println(userGroupId);
		return iFeatureAvailabilityService.getEnabledFeatures(userGroupId);
	}
}
