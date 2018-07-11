package com.apm.datarw.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Objects;

import java.util.TimeZone;

import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.apache.commons.lang.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apm.datarw.config.SSTConfigurations;
import com.apm.datarw.dto.AnalyticParameterDTO;
import com.apm.datarw.dto.ConfigDTO;
import com.apm.datarw.dto.PythonRequestDTO;
import com.apm.datarw.dto.TagInfoDTO;
import com.apm.datarw.entity.AnalyticAssetMappingEntity;
import com.apm.datarw.entity.AnalyticInfoEntity;
import com.apm.datarw.entity.AnalyticResultReferenceTagEntity;
import com.apm.datarw.entity.AnalyticRunEntity;
import com.apm.datarw.entity.AnalyticSurplusConstantsEntity;
import com.apm.datarw.entity.AnalyticTagMappingEntity;
import com.apm.datarw.entity.TagInfoEntity;
import com.apm.datarw.inf.IAnalyticDetailsImpl;
import com.apm.datarw.repo.IAnalyticInfoRepo;
import com.apm.datarw.repo.IAnalyticResultReferenceTagRepo;
import com.apm.datarw.repo.IAnalyticTagMappingRepo;
import com.apm.datarw.repo.ITagInfoRepo;
import com.apm.datarw.utils.DataConstants;
import com.apm.datarw.utils.ExternalPropertyUtil;
import com.apm.datarw.utils.HelperFunctions;
import com.apm.datarw.utils.HistorianWrapper;

@Service
@Transactional
public class AnalyticDetailsImpl implements IAnalyticDetailsImpl{
	private static final Logger log=LoggerFactory.getLogger(AnalyticDetailsImpl.class);
	
	@Autowired
	IAnalyticInfoRepo iAnalyticInfoRepo;

	/*@Autowired
	IAnalyticExecutionDetailsRepo iAnalyticExecutionDetailsRepo;
*/
	@Autowired
	IAnalyticTagMappingRepo iAnalyticTagMappingRepo;

	@Autowired
	ITagInfoRepo iTagInfoRepo;

	@Autowired 
	IAnalyticResultReferenceTagRepo iAnalyticResultReferenceTagRepo;
	
	/*@Autowired
	IEquipmentInfoRepo iEquipmentInfoRepo;
	*/
	@Autowired
	SSTConfigurations sstConfigurations;

	List<String> tagTypeList=null;

	@Override
	@SuppressWarnings("unchecked")
	public PythonRequestDTO getAnalyticDetails(JSONObject jsonObject) {
		PythonRequestDTO response= new PythonRequestDTO();
		try{

			JSONObject tagAliases= new JSONObject();
			ConfigDTO configDTO =new ConfigDTO();
			AnalyticInfoEntity  analyticInfoEntity=null;
			List<TagInfoEntity>  tagEntityList= null;
			AnalyticResultReferenceTagEntity analyticResultReferenceTagEntity=null;
			TagInfoDTO tagInfoDTO = null;
			List<TagInfoDTO> infoDTOsList=null;
			List<AnalyticTagMappingEntity> tagList=null;
			List<String> tagTypeList=null;
			String key="";
			String tagSource="";
			Long startTime=0l;
			Long endTime=0l;
			Long analyticId=0l;
			String equipmentId="";

			if(!(jsonObject.isEmpty())){
				for (Iterator<?> iterator = jsonObject.keySet().iterator(); iterator
						.hasNext();) { 
					key = (String) iterator.next();
					if((key.equalsIgnoreCase("analyticId"))){
						analyticId=Long.parseLong( (String) jsonObject.get(key));
					}
					if((key.equalsIgnoreCase("equipmentInfoId"))){
						equipmentId= jsonObject.get(key).toString();
					}
					if((key.equalsIgnoreCase("startTime"))){
						startTime=Long.parseLong( (String) jsonObject.get(key));
					}
					if((key.equalsIgnoreCase("endTime"))){
						endTime=Long.parseLong( (String) jsonObject.get(key));
					}
				}

				analyticInfoEntity=iAnalyticInfoRepo.findOne(analyticId);
				Map<String, Object> parametersMap = null;
				if(analyticInfoEntity!=null){
					//Get Parameters and Constants
					if(null != analyticInfoEntity.getAnalyticSurplusConstants()){
						//Any Extra Parameters [Just in case]
						parametersMap = analyticInfoEntity.getAnalyticSurplusConstants().stream().collect(Collectors.toMap(AnalyticSurplusConstantsEntity::getTagName, obj ->{
							Object tagValue = null;
							try{
								if (HelperFunctions.isJsonArray(obj.getTagValue())){
									List<Object> listConstants = new ArrayList<Object>();
									JSONArray tempArray = new JSONArray(obj.getTagValue());
									Integer idx = 0;
									for(idx=0; idx<tempArray.length(); idx++){
										try{
											listConstants.add(HelperFunctions.toMap(tempArray.getJSONObject(idx)));
										}catch(Exception jsonObjEx){
											try{
												listConstants.add(HelperFunctions.toList(tempArray.getJSONArray(idx)));
											}catch(Exception jsonArrEx){
												listConstants.add(tempArray.get(idx));
											}
										}
									}
									tagValue = listConstants;
								}else if (HelperFunctions.isJsonObject(obj.getTagValue())){
									tagValue = HelperFunctions.toMap(new org.json.JSONObject(obj.getTagValue()));
								}else{
									tagValue = obj.getTagValue();
								}
							}catch(JSONException | NullPointerException e){
								tagValue = obj.getTagValue();
							}
							return tagValue;
						}));
					}
					if( null == parametersMap){
						parametersMap= new HashMap<String, Object>();
					}
					List<AnalyticParameterDTO> analyticParameters = new ArrayList<>();
					if(null != analyticInfoEntity.getAnalyticParameterList()){
						analyticInfoEntity.getAnalyticParameterList().forEach(parameter -> {
							AnalyticParameterDTO analyticParameter = new AnalyticParameterDTO();
							analyticParameter.setDescription(parameter.getTagDescription());
							analyticParameter.setName(parameter.getTagName());
							//TODO: Integrate unit column in database. 
							analyticParameter.setUnit(null);
							analyticParameter.setValue(HelperFunctions.getFormattedValue(parameter.getTagValue()));
							analyticParameters.add(analyticParameter);
						});
					}
					//Set Analytic Parameters.
					parametersMap.put("analytic_parameters", analyticParameters);
					configDTO.setParameters(parametersMap);
					//Get Parameters and Constants :: END
					tagEntityList=iTagInfoRepo.getByAnalyticId(analyticId);
					if(tagEntityList.size()>0 &&tagEntityList!=null){
						for (TagInfoEntity infoEntity : tagEntityList) {
							tagAliases.put(infoEntity.getTagName(), infoEntity.getTagAliases());
							configDTO.setTag_aliases(tagAliases);
						}

					}
					//fetchHistorianData(analyticInfoEntity);
					fetchCentralHistorianData(analyticInfoEntity);
					//historian
					tagTypeList=iTagInfoRepo.fetchTagType();	
					if(tagTypeList!=null){
						for (String singleTagType : tagTypeList) {
							infoDTOsList= new ArrayList<TagInfoDTO>();
							tagList=iAnalyticTagMappingRepo.fetchByIdandType(analyticId,singleTagType);
							if(tagList.size()>0){
								for (AnalyticTagMappingEntity singleEntity : tagList) {
									tagInfoDTO = new  TagInfoDTO();
									tagInfoDTO.setName(singleEntity.getTagInfoEntity().getTagName());
									tagSource=singleEntity.getTagInfoEntity().getTagSource();
									tagInfoDTO.setUnit(singleEntity.getTagInfoEntity().getUnit());
									tagInfoDTO.setDescription(singleEntity.getTagInfoEntity().getTagDescription());
									if((tagSource.equalsIgnoreCase("SQL")&&singleEntity.getTagInfoEntity().getTagType().equalsIgnoreCase("reference"))&&!(tagSource.isEmpty())){

										analyticResultReferenceTagEntity=iAnalyticResultReferenceTagRepo.fetchById(singleEntity.getAnalyticTagMappingId());
										if(analyticResultReferenceTagEntity!=null){
											tagInfoDTO.setValue(analyticResultReferenceTagEntity.getTagValue());
										}


									}
									infoDTOsList.add(tagInfoDTO);

									if(singleEntity.getTagInfoEntity().getTagType().equalsIgnoreCase("inputTS")){
										configDTO.setInput_tags(infoDTOsList);
									}else if(singleEntity.getTagInfoEntity().getTagType().equalsIgnoreCase("outputTS")){
										configDTO.setOutput_tags(infoDTOsList);
									}
									response.setAnalyticName(analyticInfoEntity.getAnalyticName());
									response.setConfig(configDTO);
								}
							}
						}
					}
				}

			}
		}catch(Exception e){
			log.error("error in analytic implementation", e);
		}


		return response;
	}


	


	/**
	 * @param jsonObject
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PythonRequestDTO facadeForScheduler(AnalyticRunEntity jobObject){
		PythonRequestDTO response= new PythonRequestDTO();
		try{
			//org.json.JSONObject tagAliases= new org.json.JSONObject();
  
			JSONObject tagAliases = new JSONObject();
			ConfigDTO configDTO =new ConfigDTO();
			List<TagInfoEntity> tagEntityList = new ArrayList<TagInfoEntity>();
			AnalyticResultReferenceTagEntity analyticResultReferenceTagEntity=null;
			TagInfoDTO tagInfoDTO = null;
			List<TagInfoDTO> infoDTOsList=null;
			List<AnalyticTagMappingEntity> tagList=null;
			List<String> tagTypeList=null;
			String tagSource="";
			AnalyticInfoEntity analyticInfoEntity = null;
			analyticInfoEntity=iAnalyticInfoRepo.findOne(jobObject.getAnalyticInfoEntity().getAnalyticId());
			Map<String, Object> parametersMap = null;
			if(analyticInfoEntity!=null){
				//Get Parameters and Constants
				if(null != analyticInfoEntity.getAnalyticSurplusConstants()){
					//Any Extra Parameters [Just in case]
					parametersMap = analyticInfoEntity.getAnalyticSurplusConstants().stream().collect(Collectors.toMap(AnalyticSurplusConstantsEntity::getTagName, obj ->{
						Object tagValue = null;
						try{
							if (HelperFunctions.isJsonArray(obj.getTagValue())){
								List<Object> listConstants = new ArrayList<Object>();
								JSONArray tempArray = new JSONArray(obj.getTagValue());
								Integer idx = 0;
								for(idx=0; idx<tempArray.length(); idx++){
									try{
										listConstants.add(HelperFunctions.toMap(tempArray.getJSONObject(idx)));
									}catch(Exception jsonObjEx){
										try{
											listConstants.add(HelperFunctions.toList(tempArray.getJSONArray(idx)));
										}catch(Exception jsonArrEx){
											listConstants.add(tempArray.get(idx));;
										}
									}
								}
								tagValue = listConstants;
							}else if (HelperFunctions.isJsonObject(obj.getTagValue())){
								tagValue = HelperFunctions.toMap(new org.json.JSONObject(obj.getTagValue()));
							}else{
								tagValue = obj.getTagValue();
							}
						}catch(JSONException | NullPointerException e){
							tagValue = obj.getTagValue();
						}
						return tagValue;
					}));
				}
				if( null == parametersMap){
					parametersMap= new HashMap<String, Object>();
				}
				List<AnalyticParameterDTO> analyticParameters = new ArrayList<>();
				if(null != analyticInfoEntity.getAnalyticParameterList()){
					analyticInfoEntity.getAnalyticParameterList().forEach(parameter -> {
						AnalyticParameterDTO analyticParameter = new AnalyticParameterDTO();
						analyticParameter.setDescription(parameter.getTagDescription());
						analyticParameter.setName(parameter.getTagName());
						//TODO: Integrate unit column in database. 
						analyticParameter.setUnit(null);
						analyticParameter.setValue(HelperFunctions.getFormattedValue(parameter.getTagValue()));
						analyticParameters.add(analyticParameter);
					});
				}
				//Set Analytic Parameters.
				parametersMap.put("analytic_parameters", analyticParameters);
				configDTO.setParameters(parametersMap);
				//Get Parameters and Constants :: END

				//tagEntityList=iTagInfoRepo.findById(analyticInfoEntity.getAnalyticId());
				List<AnalyticTagMappingEntity> analyticTagMappingEntities=iAnalyticTagMappingRepo.fetchByAnalyticId(analyticInfoEntity.getAnalyticId());
				if(Objects.isNull(analyticTagMappingEntities)) {
					log.info("No tag mapped against analytic id::"+analyticInfoEntity.getAnalyticId());
				}else {
				
				
				for (AnalyticTagMappingEntity analyticTagMappingEntity : analyticTagMappingEntities) {
					TagInfoEntity infoEntity =analyticTagMappingEntity.getTagInfoEntity();
				
				
				//System.out.println("********"+analyticInfoEntity.getAnalyticId());
				//tagEntityList=iTagInfoRepo.getTagInfoEntityByAnalyticTagMappingEntityAnalyticInfoEntityAnalyticId(analyticInfoEntity.getAnalyticId());
				//System.out.println("tags list::"+tagEntityList.size());
				if(infoEntity!=null){
					//for (TagInfoEntity infoEntity : analyticTagMappingEntities) {
						tagAliases.put(infoEntity.getTagName(), infoEntity.getTagAliases());
						configDTO.setTag_aliases(tagAliases);
					//}

				}
				}
				}

				tagEntityList=iTagInfoRepo.findById(analyticInfoEntity.getAnalyticId());
				if(tagEntityList.size()>0 &&tagEntityList!=null){
					String tagName = "";
					String tagAliasValue = "";
					for (TagInfoEntity infoEntity : tagEntityList) {
						//changes for comma separated tags starts here
						
						if(tagName.equals(infoEntity.getTagName())) {
							tagAliasValue = tagAliasValue + (String) tagAliases.get(tagName);
							tagAliasValue = tagAliasValue + "," + infoEntity.getTagAliases();
							configDTO.getTag_aliases().remove(tagName);
							tagAliases.put(tagName, tagAliasValue);
							configDTO.setTag_aliases(tagAliases);
							tagAliasValue = "";					
						}
						else {
						//changes for comma separated tags ends here
						tagAliases.put(infoEntity.getTagName(), infoEntity.getTagAliases());
						configDTO.setTag_aliases(tagAliases);
						tagName = infoEntity.getTagName();
						}
					}

				}
				
				JSONObject jsonObject = configDTO.getTag_aliases();

				tagTypeList=iTagInfoRepo.fetchTagType();	
				if(tagTypeList!=null){
					for (String singleTagType : tagTypeList) {
						infoDTOsList= new ArrayList<TagInfoDTO>();
						tagList=iAnalyticTagMappingRepo.fetchByIdandType(analyticInfoEntity.getAnalyticId(),singleTagType);
						if(tagList.size()>0){
							for (AnalyticTagMappingEntity singleEntity : tagList) {
								tagInfoDTO = new  TagInfoDTO();
								tagInfoDTO.setName(singleEntity.getTagInfoEntity().getTagName());
								tagSource=singleEntity.getTagInfoEntity().getTagSource();
								tagInfoDTO.setUnit(singleEntity.getTagInfoEntity().getUnit());
								tagInfoDTO.setDescription(singleEntity.getTagInfoEntity().getTagDescription());
								if((tagSource.equalsIgnoreCase("SQL")&&singleEntity.getTagInfoEntity().getTagType().equalsIgnoreCase("reference"))&&!(tagSource.isEmpty())){

									analyticResultReferenceTagEntity=iAnalyticResultReferenceTagRepo.fetchById(singleEntity.getAnalyticTagMappingId());
									if(analyticResultReferenceTagEntity!=null){
										tagInfoDTO.setValue(analyticResultReferenceTagEntity.getTagValue());
									}
								}
								infoDTOsList.add(tagInfoDTO);
								if(singleEntity.getTagInfoEntity().getTagType().equalsIgnoreCase("inputTS")){
									configDTO.setInput_tags(infoDTOsList);
								}else if(singleEntity.getTagInfoEntity().getTagType().equalsIgnoreCase("outputTS")){
									configDTO.setOutput_tags(infoDTOsList);
								}
								response.setAnalyticName(analyticInfoEntity.getAnalyticName());
								response.setConfig(configDTO);
								//Set State Data here.
							}
						}
					}
				}
			}
		}catch(Exception e){
			log.error("Error in fetching Analytic configuration.", e);
		}
		return response;
	}
	
	@Override
	public List<Map<String, String>> fetchHistorianData(AnalyticInfoEntity analyticInfoEntity) {
		List<Map<String, String>> responseList = new ArrayList<>();
		
		if(analyticInfoEntity!=null){
			long startDate=analyticInfoEntity.getStartTime().getTime();
			long endDate=(analyticInfoEntity.getStartTime().getTime())+((analyticInfoEntity.getDataInterval())*1000);
			//String assetId="TEST";
			List<AnalyticAssetMappingEntity> analyticAssetMappingEntities =  analyticInfoEntity.getAnalyticAssetMappingEntities();
			for (AnalyticAssetMappingEntity analyticAssetMappingEntity : analyticAssetMappingEntities) {
				Map<String,String> responseMap= new HashMap<>();
				String assetId = analyticAssetMappingEntity.getAssetId();
				tagTypeList=iTagInfoRepo.findAliasesByType(analyticInfoEntity.getAnalyticId(),"inputTs");		
				if(tagTypeList.size()>0&&tagTypeList!=null){
					try {
						responseMap=HistorianWrapper.getDataMap(tagTypeList,assetId,startDate,endDate,analyticInfoEntity.getDataInterval(),"localhost","","");
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
				responseList.add(responseMap);
			}			
		}
		return responseList;
	}
	/*
	 * Get data from Central Historian
	 * */
	@Override
	public List<Map<String,String>> fetchCentralHistorianData(AnalyticInfoEntity analyticInfo) throws ParseException {
		List<Map<String, String>> responseList = new ArrayList<>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(DataConstants.INPUT_DATE_TIME_FMT);
		dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		if(analyticInfo!=null){
			long startDate = (DateUtils.addDays(analyticInfo.getStartTime(), Integer.parseInt(ExternalPropertyUtil.getPropValueFromApplicationConfig("startTimeSubValue")))).getTime();
			long endDate = ((DateUtils.addDays(analyticInfo.getEndTime(), Integer.parseInt(ExternalPropertyUtil.getPropValueFromApplicationConfig("endTimeSubValue"))).getTime()) + ((analyticInfo.getDataInterval())*1000));
			log.info("startDate :: "+""+startDate);
			log.info("endDate :: "+""+endDate);
			log.info("historian server ip address :: \t" + sstConfigurations.getServerAddress());
			
			//analyticInfo.getEquipmentAnalyticMappingEntity().stream().forEach(equipmentAnalyticMappingEntity -> {
			analyticInfo.getAnalyticAssetMappingEntities().stream().forEach(analyticAssetMappingEntity -> {
					String assetId = analyticAssetMappingEntity.getAssetId();
					Map<String,String> responseMap= new HashMap<>();
		
					List<String> tagTypeList=iTagInfoRepo.findAliasesByType(analyticInfo.getAnalyticId(),"inputTs");		
					log.info("**tagTypeList*******" + tagTypeList.size());
						if(tagTypeList.size()>0&&tagTypeList!=null){
							try {
								responseMap=HistorianWrapper.getDataMap(tagTypeList,assetId,startDate,endDate,analyticInfo.getDataInterval(),
										sstConfigurations.getServerAddress(),
										sstConfigurations.getServerUsername(),
										sstConfigurations.getServerPassword());
							} catch (Exception e) {
								log.error(e.getMessage());
							}
						}
						responseList.add(responseMap);
					});
		}
				
		return responseList;
	}
}
