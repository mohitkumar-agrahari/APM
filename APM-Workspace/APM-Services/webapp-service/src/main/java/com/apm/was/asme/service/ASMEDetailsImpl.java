package com.apm.was.asme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apm.was.asme.controller.ASMEController;
import com.apm.was.asme.dto.ASMEInputDTO;
import com.apm.was.asme.dto.ParamDTO;
import com.apm.was.asme.dto.ParamResponseDTO;
import com.apm.was.asme.dto.SaveInputDTO;
import com.apm.was.asme.entity.ASMECoalCompInputValuesEntity;
import com.apm.was.asme.entity.ASMEHRResultEntity;
import com.apm.was.asme.entity.ASMEReferenceTagsEntity;
import com.apm.was.asme.inf.IASMEDetails;
import com.apm.was.asme.repo.IASMECoalCompInputValueRepo;
import com.apm.was.asme.repo.IASMEHRResultRepo;
import com.apm.was.asme.repo.IASMERefTagsRepo;
import com.apm.was.dto.MessageDTO;
import com.apm.was.repo.IAnalyticInfoRepo;


@Service
@Transactional
public class ASMEDetailsImpl implements IASMEDetails {
	
	private static final Logger log = LoggerFactory.getLogger(ASMEDetailsImpl.class);
	
	@Autowired
	IAnalyticInfoRepo iAnalyticInfoRepo;
	
	@Autowired
	IASMERefTagsRepo iASMERefTagRepo;
	
	@Autowired
	IASMECoalCompInputValueRepo iASMEInputValueRepo;
	
	@Autowired
	IASMEHRResultRepo iASMEResultRepo;

	@Override
	public ParamResponseDTO getAssetParam(Long analyticId) {
		ParamResponseDTO finalResponse = new ParamResponseDTO();
		List<ASMEInputDTO> responseList= null;
		ASMEInputDTO asmeInputDTO=null;
		ParamDTO paramDTO=null;
		List<ParamDTO> paramDTOList = null;
		log.info("get Asset Parameter for analytic id started::"+analyticId);
		List<ASMEReferenceTagsEntity> asmeReferenceTagsEntities= null;
		ASMECoalCompInputValuesEntity inputValuesEntity=null;
		String LatestDateTime=null;
		ASMEHRResultEntity asmehrResultEntity=null;
		List<String> tagHeaders=null;
		List<String> tagTypes=null;
		tagTypes=iASMERefTagRepo.fetchTagType(analyticId);
		if(Objects.isNull(tagTypes)) {
			log.error("no tags has been stored for analytic id::"+analyticId);
			}else {
				for (String UniquetagType : tagTypes) {
					responseList= new ArrayList<ASMEInputDTO>();
					
					tagHeaders=iASMERefTagRepo.fetchTagHeaderByTagType(analyticId,UniquetagType);
					if(Objects.isNull(tagHeaders)) {
						log.error("No Asset parameters for analytic id::"+analyticId);
					}else {
						for (String UniqueTagHeader : tagHeaders) {
							asmeReferenceTagsEntities=iASMERefTagRepo.getAsmeReferenceTagsByAsmeCalculationHeader(UniqueTagHeader);
							if(Objects.isNull(asmeReferenceTagsEntities)) {
								log.error("Error in asset parameter storage for header ::"+UniqueTagHeader);
							}else {
					asmeInputDTO = new ASMEInputDTO();
					paramDTOList = new ArrayList<ParamDTO>();
					asmeInputDTO.setHeader(UniqueTagHeader);
					for (ASMEReferenceTagsEntity asmeReferenceTagsEntity : asmeReferenceTagsEntities) {
						paramDTO= new ParamDTO();
						
						paramDTO.setId(asmeReferenceTagsEntity.getAsmeRefTagsId());
						paramDTO.setName(asmeReferenceTagsEntity.getAsmeTagCaption());
						paramDTO.setUom(asmeReferenceTagsEntity.getUom());
						paramDTO.setType(asmeReferenceTagsEntity.getDataType());
						
						if(UniquetagType.equalsIgnoreCase("input")){
							
							LatestDateTime=iASMEInputValueRepo.getLatestEntry(asmeReferenceTagsEntity.getAsmeRefTagsId(),true);
							if(LatestDateTime != null) {
								//TODO verify whether on fetch we should display real time value or backfill
								inputValuesEntity=iASMEInputValueRepo.getAsmeCoalCompInputValuesByStartDateAndAsmeReferenceTagsAsmeRefTagsIdAndIsRealTime(LatestDateTime,asmeReferenceTagsEntity.getAsmeRefTagsId(),true);
								if(!(Objects.isNull(inputValuesEntity))) {
									paramDTO.setValue(inputValuesEntity.getAsme_tag_value());
								}
							}
							
						}else if(UniquetagType.equalsIgnoreCase("output")) {
							LatestDateTime=iASMEResultRepo.getLatestEntry(asmeReferenceTagsEntity.getAsmeRefTagsId(),true);
							if( LatestDateTime != null) {
								asmehrResultEntity=iASMEResultRepo.getAsmeHrResultByStartDateAndAsmeReferenceTagsAsmeRefTagsIdAndIsRealTime(LatestDateTime,asmeReferenceTagsEntity.getAsmeRefTagsId(),true);
								if(!(Objects.isNull(asmehrResultEntity))) {
									paramDTO.setValue(asmehrResultEntity.getAsme_tag_value());
								}
							}
						}
						paramDTOList.add(paramDTO);
						
					}
					asmeInputDTO.setParameter(paramDTOList);
				
				}
			
				responseList.add(asmeInputDTO);
						}
			
					}
				if(UniquetagType.equalsIgnoreCase("input")){
					finalResponse.setInputParam(responseList);
				}else if(UniquetagType.equalsIgnoreCase("output")) {
					finalResponse.setOutputParam(responseList);
				}
			}
			}
		
		return finalResponse;
	}

	@Override
	public MessageDTO saveAssetInputValue(SaveInputDTO saveInputDTO) {
		List<ASMEInputDTO> parameterList = new ArrayList<ASMEInputDTO>();
		List<ParamDTO> paramDTO=null;
		ASMECoalCompInputValuesEntity asmeCoalCompInputValuesEntity=null;
		List<ASMECoalCompInputValuesEntity> asmeCoalCompInputValuesEntities= new ArrayList<ASMECoalCompInputValuesEntity>();
		ASMEReferenceTagsEntity asmeReferenceTags= null;
		log.info("Save input value in database started");
		parameterList= saveInputDTO.getParameters();
		String startDate=saveInputDTO.getStartDate();
	    String enddate=saveInputDTO.getEndDate();
		Boolean isRealTimeFlag=saveInputDTO.getIsrealtime();
		if(Objects.isNull(startDate)) {
	       log.error("Start date is null");
		}else {
			
				for (ASMEInputDTO asmeInputDTO : parameterList) {
					paramDTO=asmeInputDTO.getParameter();
					if(Objects.isNull(paramDTO)) {
						log.error("Parameter list empty for header ::"+asmeInputDTO.getHeader());
					}else {
						for (ParamDTO paramDTO2 : paramDTO) {
							asmeCoalCompInputValuesEntity=iASMEInputValueRepo.getAsmeCoalCompInputValuesByStartDateAndAsmeReferenceTagsAsmeRefTagsIdAndIsRealTime(startDate,paramDTO2.getId(),isRealTimeFlag);
							if(Objects.isNull(asmeCoalCompInputValuesEntity)) {
							asmeCoalCompInputValuesEntity= new ASMECoalCompInputValuesEntity();
							asmeCoalCompInputValuesEntity.setIsRealTime(isRealTimeFlag);
							asmeCoalCompInputValuesEntity.setAsme_tag_value(paramDTO2.getValue());
							asmeCoalCompInputValuesEntity.setStartDate(startDate);
							asmeCoalCompInputValuesEntity.setEndDate(enddate);
							asmeReferenceTags=iASMERefTagRepo.findOne(paramDTO2.getId());
							if(Objects.isNull(asmeReferenceTags)) {
								log.error("No reference tag for id :: "+ paramDTO2.getId());
								
							}else {
							asmeCoalCompInputValuesEntity.setAsmeReferenceTags(asmeReferenceTags);
							}
							}else {
								asmeCoalCompInputValuesEntity.setIsRealTime(isRealTimeFlag);
								asmeCoalCompInputValuesEntity.setAsme_tag_value(paramDTO2.getValue());
								asmeCoalCompInputValuesEntity.setStartDate(startDate);
								asmeCoalCompInputValuesEntity.setEndDate(enddate);
								asmeReferenceTags=iASMERefTagRepo.findOne(paramDTO2.getId());
								if(Objects.isNull(asmeReferenceTags)) {
									log.error("No reference tag for id :: "+ paramDTO2.getId());
									
								}else {
								asmeCoalCompInputValuesEntity.setAsmeReferenceTags(asmeReferenceTags);
								}
								
							}
							asmeCoalCompInputValuesEntities.add(asmeCoalCompInputValuesEntity);
						}
					}
					
				}
				iASMEInputValueRepo.save(asmeCoalCompInputValuesEntities);
					
				
			
			}
		
		
		
		return null;
	}

}
