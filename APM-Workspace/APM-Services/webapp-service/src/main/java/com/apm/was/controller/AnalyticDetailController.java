package com.apm.was.controller;

import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import com.apm.was.dto.AlarmCountDTO;
import com.apm.was.dto.AlertResponseDTO;
import com.apm.was.dto.AnalyticDetailsDTO;
import com.apm.was.dto.AssetHealthRespDTO;
import com.apm.was.dto.AssetModelDTO;
import com.apm.was.dto.ImageDetailsDTO;
import com.apm.was.dto.MessageDTO;
import com.apm.was.dto.OuttagResDTO;
import com.apm.was.entity.AnalyticInfoEntity;
import com.apm.was.inf.IAlarmDetails;
import com.apm.was.inf.IAnalyticDetails;
import com.apm.was.inf.IMonitorPlantDetails;



@CrossOrigin("http://localhost:5010")
@RestController
public class AnalyticDetailController {
	private static final Logger log = LoggerFactory.getLogger(AnalyticDetailController.class);
	
	@Autowired IAnalyticDetails iAnalyticDetails;
	
/*Retrieves analytic data from database */
@RequestMapping(value="/fetchAnalyticDetailData",method=RequestMethod.GET)
public List<AnalyticDetailsDTO> getAnalyticDetailData(){
	List<AnalyticDetailsDTO>  responseObj= null;
	log.info("fetching analytic data");
	responseObj= iAnalyticDetails.getAnalyticDetailData();
		return responseObj;
	}
}
