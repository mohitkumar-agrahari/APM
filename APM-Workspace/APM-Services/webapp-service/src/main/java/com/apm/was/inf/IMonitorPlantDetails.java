package com.apm.was.inf;

import java.io.IOException;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;

import com.apm.was.dto.AlertResponseDTO;
import com.apm.was.dto.AssetModelDTO;
import com.apm.was.dto.ImageDetailsDTO;
import com.apm.was.dto.MessageDTO;
import com.apm.was.dto.OuttagResDTO;
import com.apm.was.entity.AnalyticInfoEntity;


public interface IMonitorPlantDetails {


	List<JSONObject> getOuttagData(String assetId) throws ParseException, IOException;
	JSONObject getTagData(JSONObject jsonObject) throws IOException;
	
	String goToGrafana(String analyticName) throws IOException;
	List<JSONObject> getMetricsAsAnalytics() throws IOException;
	List<JSONObject> getTagsByAnalyticName(JSONObject jsonObject) throws IOException;

}
