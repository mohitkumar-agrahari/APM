package com.apm.was.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apm.was.dto.AnalyticDetailsDTO;
import com.apm.was.entity.AnalyticAssetMappingEntity;
import com.apm.was.entity.AnalyticInfoEntity;
import com.apm.was.entity.AnalyticRunEntity;
import com.apm.was.inf.IAnalyticDetails;
import com.apm.was.repo.IAnalyticAlarmRepo;
import com.apm.was.repo.IAnalyticInfoRepo;
import com.apm.was.repo.IAnalyticOutputEntity;
import com.apm.was.repo.IAnalyticResultReferenceTagRepo;
import com.apm.was.repo.IAnalyticRunStatus;
import com.apm.was.repo.IAnalyticTagMappingRepo;
import com.apm.was.util.Utils;

@Service
@Transactional
public class AnalyticDetailsImpl implements IAnalyticDetails {
	private static final Logger log= LoggerFactory.getLogger(AnalyticDetailsImpl.class);

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
	
	
	
	@Override
	public List<AnalyticDetailsDTO> getAnalyticDetailData() {
		List<AnalyticDetailsDTO> responseList = new ArrayList<AnalyticDetailsDTO>();
		List<AnalyticAssetMappingEntity> analyticAssetMappingEntities= new ArrayList<AnalyticAssetMappingEntity>();
		AnalyticDetailsDTO resposneObj= null;
		List<AnalyticInfoEntity> analyticInfoEntities;
		AnalyticRunEntity analyticRunEntity= null;
		
		String latestDate="";
		
		Date endDate = null;
		analyticInfoEntities = AnalyticInfoRepo.findAll();
		if(Objects.isNull(analyticInfoEntities)) {
			log.error("No analytic is deployed");
			throw new ValidationException("No analytic is deployed");
		}else {
			for (AnalyticInfoEntity analyticInfoEntity : analyticInfoEntities) {
				resposneObj= new AnalyticDetailsDTO();
				latestDate=iAnalyticRunStatusRepo.fetchLatestJobTimeByAnalyticId(analyticInfoEntity.getAnalyticId());
				if(latestDate != null) {
					//last executed job of an anlytic by latest end date
				analyticRunEntity=iAnalyticRunStatusRepo.getAnalyticRunEntityByEndDate(latestDate);
				if(Objects.isNull(analyticRunEntity)) {
					log.info("no job with this end date ::"+latestDate);
					resposneObj.setIs_execution_passed("UnScheduled");
					//resposneObj.setLastExecutedDate('N/A');
				}else {
					if(analyticRunEntity.getJobStatus()!= null) {
						resposneObj.setIs_execution_passed(analyticRunEntity.getJobStatus());
					}else {
						resposneObj.setIs_execution_passed("Failed");
					}
					resposneObj.setLastExecutedDate(analyticRunEntity.getStartDate());
				}
				}
				resposneObj.setAnalytic_id(analyticInfoEntity.getAnalyticId());
			resposneObj.setId(analyticInfoEntity.getAnalyticId());
                resposneObj.setAnalytic_name(analyticInfoEntity.getAnalyticName());
                resposneObj.setTitle(analyticInfoEntity.getAnalyticName());
                resposneObj.setAnalytic_version(analyticInfoEntity.getAnalyticVersion());
                resposneObj.setSubtitle(analyticInfoEntity.getAnalyticVersion());
                resposneObj.setAnalytic_platform(analyticInfoEntity.getAnalyticPlatform());
                resposneObj.setExecution_frequency(analyticInfoEntity.getExecutionFrequency());
                
                resposneObj.setAnalyticStartDate(analyticInfoEntity.getStartTime().toString());
                resposneObj.setAnalyticEndDate(analyticInfoEntity.getEndTime().toString());
               
                 String endDataStr=resposneObj.getAnalyticEndDate();
               
                 try {
					 Date end= Utils.convertNewStringToDate(endDataStr);
					 endDate = end;
				} catch (ParseException e) {
					log.error("error in parsing the end date ::"+endDataStr);
					e.printStackTrace();
				}  
                 Date currentDate= new Date();
                 System.out.println("currdate"+currentDate);System.out.println("endDataStr"+endDataStr);
                 System.out.println("date"+endDate);
                
                 if(currentDate.before(endDate)) {
                	 resposneObj.setAnalyticExecutionStatus(true);
                 }else {
                	 resposneObj.setAnalyticExecutionStatus(false);
              }
                 analyticAssetMappingEntities= analyticInfoEntity.getAnalyticAssetMappingEntities();
                 
                if(Objects.isNull(analyticAssetMappingEntities)) {
                	log.error("No asset is mapped with analytic id  :"+analyticInfoEntity.getAnalyticId());
                	throw new ValidationException("No asset is mapped with analytic id  :"+analyticInfoEntity.getAnalyticId());
                	
                }else {
				log.info("Mappind Exists for analytic id ::"+analyticInfoEntity.getAnalyticId());
				for (AnalyticAssetMappingEntity analyticAssetMappingEntity : analyticAssetMappingEntities) {
					
				
					resposneObj.setAssetName(analyticAssetMappingEntity.getAssetName());
					resposneObj.setAssetType(analyticAssetMappingEntity.getAssetType());
					}
                }
			
				resposneObj.setSeverity("");
				
				resposneObj.setAdded_on(analyticInfoEntity.getAddedOn().toString());
				resposneObj.setDate(analyticInfoEntity.getAddedOn().toString());
			
				
				
				responseList.add(resposneObj);
				
				
				
				
			}
			
			
			
		}
		return responseList;
	
	}
	

}
