package com.apm.was.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.apm.was.dto.AlarmCountDTO;
import com.apm.was.dto.AlertResponseDTO;
import com.apm.was.dto.MessageDTO;
import com.apm.was.entity.AnalyticAlarmEntity;
import com.apm.was.http.connector.HttpConnector;
import com.apm.was.inf.IAlarmDetails;
import com.apm.was.repo.IAnalyticAlarmRepo;
import com.apm.was.repo.IAnalyticInfoRepo;
import com.apm.was.repo.IAnalyticOutputEntity;
import com.apm.was.repo.IAnalyticResultReferenceTagRepo;
import com.apm.was.repo.IAnalyticRunStatus;
import com.apm.was.repo.IAnalyticTagMappingRepo;
import com.apm.was.util.ExternalPropertyUtil;




@Service
@Transactional
public class AlarmDetails implements IAlarmDetails {
	private static final Logger log= LoggerFactory.getLogger(AlarmDetails.class);

	@Autowired
	IAnalyticTagMappingRepo iAnalyticTagMappingRepo;
	
	@Autowired
	IAnalyticResultReferenceTagRepo iAnalyticResultReferenceTagRepo;
	
	@Autowired
	IAnalyticAlarmRepo  iAlarmRepo;
	
	@Autowired
	IAnalyticOutputEntity iAnalyticOutputEntity;
	
	@Autowired
	IAnalyticInfoRepo AnalyticInfoRepo;
	
	
	@Autowired
	IAnalyticRunStatus iAnalyticRunStatusRepo;
	
	HttpConnector httpConnectorObj= new HttpConnector();
	JSONObject connectorResponse;
	List<JSONObject> resultArr = null;
	
	@Override
	public List<AlertResponseDTO> getAlertData() {
		log.info("getAlertData method called");
		AlertResponseDTO alertResponseDTO= null;
		List<AlertResponseDTO> responseList= new ArrayList<AlertResponseDTO>();
	
		String dateTime="";
		Page<AnalyticAlarmEntity> alarmEntities=iAlarmRepo.getAllByOrder(new PageRequest(0, 500));
	//Page<AnalyticAlarmEntity> alarmEntities=iAlarmRepo.findAll(new PageRequest(0, 500));
		if(Objects.isNull(alarmEntities)) {
			throw new ValidationException("No Alarms are in DB :: " );
		}else {
			for (AnalyticAlarmEntity analyticAlarmEntity : alarmEntities) {
			alertResponseDTO= new AlertResponseDTO();
			alertResponseDTO.setAlertName(analyticAlarmEntity.getAlarmName());
			if(Objects.nonNull(analyticAlarmEntity.getAnalyticAssetMappingEntity())) {
			
			}else {
				alertResponseDTO.setAlertSource("NA");
			}
			alertResponseDTO.setSeverity(analyticAlarmEntity.getSeverity());
			
			SimpleDateFormat dateFormatter = new SimpleDateFormat(ExternalPropertyUtil.getPropValue("dateTimeFormate"));
			dateFormatter.setTimeZone(TimeZone.getTimeZone(ExternalPropertyUtil.getPropValue("dateTimeTimezone")));
			dateTime = dateFormatter.format(analyticAlarmEntity.getAlarmTimestamp());
			alertResponseDTO.setEvent_Time(dateTime);
			responseList.add(alertResponseDTO);
			}
		}
		log.info("responseList size :: " + responseList.size());
		return responseList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AlarmCountDTO getAssetAlertCount(String levelName) {
		AlarmCountDTO responseDto= null;
		Long TotalCount=0L;
	
		Long alarmCount=0L;
		String param="parent="+levelName;
		
		try {
			connectorResponse = httpConnectorObj.getRequests("/assets/children",param);
			System.out.println("strubgb :"+connectorResponse);
		} catch (Exception e) {
			log.error("Error while fetch data from Asset Model Service ");
			e.printStackTrace();
		}
		if(Objects.isNull(connectorResponse)) {
			log.error("Error response of http connector is null");
		}else {
			resultArr=(List<JSONObject>) connectorResponse.get("result");
			System.out.println("array is "+resultArr.size());
		if(Objects.isNull(resultArr)) {
			log.info("No asset for level ::"+levelName);
		}else {
			for (JSONObject singAssetId : resultArr) {
				alarmCount=iAlarmRepo.fetchCountByAssetName(String.valueOf(singAssetId.get("sourceKey")));
				TotalCount=TotalCount+alarmCount;
				}
			responseDto= new  AlarmCountDTO();
			responseDto.setLevelName(levelName);
			responseDto.setAlarmCount(TotalCount);
		}
		}
		return responseDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MessageDTO updateAssetHealth() {
		MessageDTO response = new MessageDTO();
//		List<AssetInfoEntity> infoEntityList = new ArrayList<AssetInfoEntity>();
//		
//		
//		List<AnalyticAlarmEntity> alarmEntities=null;
//		int highCount=0;
//		int medCount=0;
//		String assetHealthFlag="";
//		try {
//			connectorResponse = httpConnectorObj.getRequests("/assets/all", "");
//			System.out.println("strubgb :"+connectorResponse);
//		} catch (Exception e) {
//			log.error("Error while fetch data from Asset Model Service ");
//			e.printStackTrace();
//		}
//		if(Objects.isNull(connectorResponse)) {
//			log.error("Error response of http connector is null");
//		}else {
//			resultArr=(List<JSONObject>) connectorResponse.get("result");
//			System.out.println("array is "+resultArr.size());
//		if(Objects.isNull(resultArr)) {
//			log.info("No asset for level ::"+resultArr);
//		}else {
//			for (JSONObject singAssetId : resultArr) {
//				assetHealthFlag="";
//				highCount=0;
//				medCount=0;
//				
//				alarmEntities=iAlarmRepo.getAnalyticAlarmEntityByAnalyticAssetMappingEntityAssetName(String.valueOf(singAssetId.get("assetName")));
//				if(Objects.isNull(alarmEntities)) {
//					log.info("No alarms for asset ::"+singAssetId.get("sourceKey"));
//				}else {
//					for (AnalyticAlarmEntity analyticAlarmEntity : alarmEntities) {
//						if(analyticAlarmEntity.getSeverity().equalsIgnoreCase("High")) {
//							highCount=highCount+1;
//							
//						}else if(analyticAlarmEntity.getSeverity().equalsIgnoreCase("Medium")) {
//							medCount=medCount+1;
//						}
//						
//					}
//					assetHealthFlag=this.calculateAssetHealth(highCount,medCount);
//					//assetInfoEntity.setAssetHealthStatus(assetHealthFlag);
//					//infoEntityList.add(assetInfoEntity);
//					//TODO implement the store asset info 
//				}
//				
//			}
//			/*iAssetInfoEntity.save(infoEntityList);*/
//			response.setMessage("Success");
//		}}
		
		return response;
	}

	private String calculateAssetHealth(int highCount, int medCount) {
	
	String assetHealthStatus="";
     if(highCount>3||medCount>3) {
    	   assetHealthStatus="red";
       }else {
    	   assetHealthStatus="green";
       }

		return assetHealthStatus;
	}

	
	

}
