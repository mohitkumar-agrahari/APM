package com.apm.was.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.validation.ValidationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apm.was.dto.AlertResponseDTO;
import com.apm.was.dto.AssetModelDTO;
import com.apm.was.dto.ImageDetailsDTO;
import com.apm.was.dto.MessageDTO;
import com.apm.was.entity.AnalyticInfoEntity;
import com.apm.was.inf.IMonitorPlantDetails;

@CrossOrigin("http://localhost:5010")
@RestController
public class MonitorPlantAppController {
	private static final Logger log = LoggerFactory.getLogger(MonitorPlantAppController.class);
	
	@Autowired
	IMonitorPlantDetails  iMonitorPlantDetails;
	
	/*Retrieves all the tags based on the assetId from KairosDB */
	@RequestMapping(value="/fetchOuttags",method=RequestMethod.GET)
	public List<JSONObject> getOuttagData(@RequestParam("assetId")String assetId) throws ParseException, IOException{
	 log.info("call made to /fetchOuttags endpoint");
    	return iMonitorPlantDetails.getOuttagData(assetId);
	}

 /*Retrieves all the analytics from KairosDB */
	@RequestMapping(value="/getAnalytics",method=RequestMethod.GET)
	public List<JSONObject> getMetricsAsAnalytics() throws ParseException, IOException{
	 log.info("call made to /getAnalytics endpoint");
    	return iMonitorPlantDetails.getMetricsAsAnalytics();
	}
 

 /*Retrieves all the tags by analytic name from KairosDB*/
	@RequestMapping(value="/getTagsByAnalyticName",method=RequestMethod.POST)
	public List<JSONObject> getTagsByAnalyticName(@RequestBody JSONObject jsonObject) throws ParseException, IOException{
	 log.info("call made to /getTagsByAnalyticName endpoint");
    	return iMonitorPlantDetails.getTagsByAnalyticName(jsonObject);
	}
	 

	
	/*Retrieves all the data-points from KairosDB as per tags */
	@RequestMapping(value="/fetchTagData",method=RequestMethod.POST,consumes="application/json",produces="application/json",headers ="Accept=application/json")
	public JSONObject getTagData(@RequestBody JSONObject jsonObject) throws IOException{
		log.info("call made to /fetchTagData endpoint");
		if(!(Objects.isNull(jsonObject))) {
			return	iMonitorPlantDetails.getTagData(jsonObject);
		}else {
			log.error("Tag Name is Empty");
		}
		return null;	
	}





/*Redirects to Grafana dashboard If dashboard already exists, if not it creates a new dashboard */
@SuppressWarnings("unchecked")
@RequestMapping(value="/goToGrafana",method=RequestMethod.GET)
public JSONObject goToGrafana(@RequestParam("analyticName") String analyticName) throws IOException{
	log.info("call made to /goToGrafana endpoint");
	JSONObject jsonObject = new JSONObject();
	String result = iMonitorPlantDetails.goToGrafana(analyticName);
	
		if(result.equalsIgnoreCase("Unauthorized")){
			jsonObject.put("error", "Unauthorized access to Grafana. Please check the Grafana admin token");
		}
		else if(!result.equalsIgnoreCase("error")) {
			jsonObject.put("grafanaURL", result);
			}
		else {
			jsonObject.put("error", "Failed to connect with Grafana Server. The Grafana Server may be temporarily down");
		}
	 return jsonObject;
}
}
