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

import com.apm.was.dto.AlarmCountDTO;
import com.apm.was.dto.AlertResponseDTO;
import com.apm.was.dto.MessageDTO;
import com.apm.was.inf.IAlarmDetails;

@CrossOrigin("http://localhost:5010")
@RestController
public class AlarmDetailController {
	private static final Logger log = LoggerFactory.getLogger(AlarmDetailController.class);
	
	
	@Autowired
	IAlarmDetails iAlarmDetails;
	/*fetch alarms generated  by analytics*/
	@RequestMapping(value="/fetchAlert",method=RequestMethod.GET)
	public List<AlertResponseDTO> getAlertData(){
		log.info("call made to /fetchAlert endpoint");
		return iAlarmDetails.getAlertData();
	}

	
	@RequestMapping(value="/fetchAssetAlertCount",method=RequestMethod.GET)
	public AlarmCountDTO getAssetAlertCount(@RequestParam(value="levelName")String levelName){
		log.info("call made to /fetchAssetAlertCount end point");
		AlarmCountDTO alarmCountDTO=null;
		if(Objects.isNull(levelName)) {
			log.error("request param is null");
		}else {
			log.info("fetching count for level::"+levelName);
			alarmCountDTO= iAlarmDetails.getAssetAlertCount(levelName);
		}
		return alarmCountDTO;
	}
	
	@RequestMapping(value="/updateAssetHealth",method=RequestMethod.GET)
	public MessageDTO updateAssetHealth(){
		log.info("call made to /updateAssetHealth end point");
		return iAlarmDetails.updateAssetHealth();
	}
	

}
