package com.apm.was.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.ValidationException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.apm.was.dto.AssetDTO;
import com.apm.was.dto.AssetImageDTO;
import com.apm.was.dto.AssetModelDTO;
import com.apm.was.dto.ImageDetailsDTO;
import com.apm.was.dto.MessageDTO;
import com.apm.was.dto.ServiceResponse;
import com.apm.was.entity.AnalyticAssetMappingEntity;
import com.apm.was.http.connector.HttpConnector;
import com.apm.was.inf.IAssetModel;
import com.apm.was.repo.IAnalyticAlarmRepo;
import com.apm.was.repo.IAnalyticInfoRepo;
import com.apm.was.repo.IAnalyticOutputEntity;
import com.apm.was.repo.IAnalyticResultReferenceTagRepo;
import com.apm.was.repo.IAnalyticTagMappingRepo;
import com.apm.was.util.ExternalPropertyUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
@Service
public class AssetModelImpl implements IAssetModel{
	private static final Logger log = LoggerFactory.getLogger(AssetModelImpl.class);
	HttpConnector httpConnectorObj = new HttpConnector();
	
	
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
	
	
	
	JSONObject connectorResponse =null;
	List<AssetDTO> assetDTOs = new ArrayList<AssetDTO>();

	List<JSONObject> resultArr = null;
	 
	@SuppressWarnings("unchecked")
	@Override
	public List<AssetModelDTO> getAssetData() {
		
		List<AssetModelDTO> responseList= new ArrayList<AssetModelDTO>();
		
	
		AssetModelDTO custObj=null;
		
		try {
			connectorResponse = httpConnectorObj.getRequests("/assets/all", "");
			System.out.println("strubgb :"+connectorResponse);
			log.info("getRequests data");
			log.info(connectorResponse.toJSONString());
			
		} catch (Exception e) {
			log.error(e.getMessage());
			
		}
		if(Objects.isNull(connectorResponse)) {
			log.error("Error response of http connector is null");
		}else {
			String responseStr = connectorResponse.toJSONString();
			Gson gson;
			try {
				gson = GsonBuilder.class.newInstance().create();
				ServiceResponse serviceResponse = gson.fromJson(responseStr, ServiceResponse.class);
				log.info("Service response :: " + serviceResponse.toString());
				AnalyticAssetMappingEntity analyticAssetMappingEntity = new AnalyticAssetMappingEntity();
				assetDTOs = serviceResponse.getResult();
			
				System.out.println("array is "+assetDTOs.size());
				for (AssetDTO assetDTO : assetDTOs) {
					
					custObj = new AssetModelDTO();
					custObj.setId(assetDTO.getSourceKey());
					custObj.setLabel(assetDTO.getAssetName());
					responseList.add(custObj);
					
				}
			} catch (InstantiationException | IllegalAccessException e) {
				log.error(e.getMessage());
				
			}
			
		}
		
	
		
		
		return responseList;
	}
	public File convert(MultipartFile file) throws IOException
	{    
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile(); 
		FileOutputStream fos = new FileOutputStream(convFile); 
		fos.write(file.getBytes());
		fos.close(); 
		return convFile;
	}
  public MessageDTO copy(File src, File dest) throws IOException {
	   MessageDTO returnObj = new MessageDTO();
	        InputStream is = null;
	        OutputStream os = null;
	        String destfilepath="";
	        Boolean deleteFlag=true;
	     
	       System.out.println(src);
	        
	        try {
	        	
	            is = new FileInputStream(src);
	            
	            System.out.println(dest.getAbsoluteFile());
	          // buffer size 1K
	            byte[] buf = new byte[1024];

	            int bytesRead;
	            if(dest.exists()) {
	            	log.info("file exists");
	            	returnObj.setMessage("file exists");
	            	destfilepath=dest.getAbsolutePath();
	            	returnObj.setPath(destfilepath);
	            	
	            	
	            }else {
	            	os = new FileOutputStream(dest);
	            	log.info("creating new file");
	            while ((bytesRead = is.read(buf)) > 0) {
	                os.write(buf, 0, bytesRead);
	                
	            }
	          
	            destfilepath= dest.getAbsolutePath();
	            log.info("Files copied to destination");
	            returnObj.setMessage("Files copied to destination");
	            returnObj.setPath(destfilepath);
	            os.close();
	            }
	        }catch(Exception e) {
	        	destfilepath="";
	        	log.error("Not able to copy file to destination ");
	        	 returnObj.setMessage("Not able to copy file to destination ");
	        	throw new ValidationException("Not able to copy file to destination ");
	        } finally {
	            is.close();
	            deleteFlag= src.delete();
	            if(deleteFlag) {
	            	log.info("Source file deleted Successfully");
	            	
	            }else {
	            	log.info("Failed to delete Source File");	            	
	            }
	            
	        }
			return returnObj;
	    }

	@Override
	public MessageDTO saveAssetImage(MultipartFile imageFile, String analyticName, String assetType) throws IOException {
		MessageDTO response= new MessageDTO();
		File newfile = null;
		File dir=null;
		String desfilepath="";
		String srcpath="";
		String userDir="";
		 Path srcpaths;
		 Path destpath;
		 boolean dirFlag=false;
		String filename=imageFile.getOriginalFilename();
			//Convert MultiPart file to File.
			newfile = this.convert(imageFile);
			srcpath=newfile.getAbsolutePath();
			userDir=ExternalPropertyUtil.getPropValue("saveImageURL");

			if(userDir!=null) {
			userDir=userDir+"\\images";
			dir= new File(userDir);
			if(!(dir.exists())) {
				dirFlag= new File(dir.toString()).mkdir();
				   if(dirFlag==false) {
					   log.error("Could not create directory");
				   }
				log.info("Directory created");
			}
				desfilepath=dir.toString()+"\\"+filename;
				System.out.println(desfilepath);
				srcpaths=Paths.get(srcpath);
			    destpath=Paths.get(desfilepath);
			    response=this.copy(srcpaths.toFile(), destpath.toFile());
			    this.saveDetailsInDB(filename,analyticName,assetType);

			  }			
		return response;	
}

	@SuppressWarnings("unchecked")
	public void saveDetailsInDB(String filename,String analyticName, String assetType) {
//		JSONObject respMessage= new JSONObject();
//		
//		List<AssetInfoEntity> assetInfoEntity2 = new ArrayList<AssetInfoEntity>();
//		AnalyticInfoEntity entity=null;
//		List<AssetInfoEntity> assetInfoList=null;
//		if(Objects.isNull(filename)&&analyticName.equalsIgnoreCase("null")&&assetType.equalsIgnoreCase("null")) {
//			log.error("Response is Empty");
//			respMessage.put("message","Response is Empty");
//		}else {
//			log.info("filename"+filename+"analyticName"+analyticName+"assetType"+assetType);
//			entity=AnalyticInfoRepo.getAnalyticInfoEntityByAnalyticName(analyticName);
//			assetInfoList=iAssetInfoEntity.fetchByAssetType(assetType);
//			//System.out.println(assetInfoList.size());
//
//			AnalyticInfoRepo.save(entity);
//		if(Objects.isNull(assetInfoList)) {
//				log.info("No data for Asset ::"+assetType);
//			}else {
//				for (AssetInfoEntity assetInfoEntity21 : assetInfoList) {
//					assetInfoEntity21.setImagePath(filename);
//					assetInfoEntity2.add(assetInfoEntity21);
//				}
//			}
//			iAssetInfoEntity.save(assetInfoEntity2);
//		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public ImageDetailsDTO getImageData(String levelDisplayName) {
		
		AssetImageDTO assetImageDTO=null;
		List<AssetImageDTO> dtosList=new ArrayList<AssetImageDTO>();
		ImageDetailsDTO imageDetailsDTO=new ImageDetailsDTO();
		
		
		
		imageDetailsDTO.setLevelDetailsName(levelDisplayName);
		String param="parent="+levelDisplayName;
		
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
			log.info("No asset for level ::"+levelDisplayName);
		}else {
			for (JSONObject singAssetId : resultArr) {
				
				assetImageDTO= new AssetImageDTO();
				assetImageDTO.setAssetType(String.valueOf(singAssetId.get("assetName")));
				dtosList.add(assetImageDTO);
				}
				imageDetailsDTO.setAssetDetails(dtosList);
			}
		}
		return imageDetailsDTO;
	}
	@Override
	public List<AssetModelDTO> getHierarchyBySourcekey(String sourceKey) {
		List<AssetModelDTO> responseList = new ArrayList<AssetModelDTO>();
		
		AssetModelDTO hasChildreturnobj = null;
		AssetModelDTO childAssets = null;
		List<AssetModelDTO> childAssetList = null;
		List<AssetDTO> childerAM = null;
		boolean isChildExistFlag= false;
		try {
			String Param= "sourcekey="+sourceKey;
			connectorResponse = httpConnectorObj.getRequests("/assets/hierarchy",Param);
			System.out.println("strubgb :"+connectorResponse);
			log.info("getRequests data");
			log.info(connectorResponse.toJSONString());
			
		} catch (Exception e) {
			log.error(e.getMessage());
			
		}
		if(Objects.isNull(connectorResponse)) {
			log.error("Error response of http connector is null");
		}else {
			String responseStr = connectorResponse.toJSONString();
			Gson gson;
			try {
				gson = GsonBuilder.class.newInstance().create();
				ServiceResponse serviceResponse = gson.fromJson(responseStr, ServiceResponse.class);
				log.info("Service response :: " + serviceResponse.toString());
				assetDTOs = serviceResponse.getResult();
				System.out.println("array is "+assetDTOs.size());
				
				for (AssetDTO assetDto : assetDTOs) {
					isChildExistFlag=isChildExists(assetDto);
					if(isChildExistFlag== true) {
						log.info("Child Exists");
						hasChildreturnobj=hasChildExist(assetDto,true);
						responseList.add(hasChildreturnobj);
						childerAM=assetDto.getChildren();
						for (AssetDTO assetDTO2 : childerAM) {
							isChildExistFlag=isChildExists(assetDTO2);
							System.out.println("child flag"+isChildExistFlag);
							hasChildreturnobj=hasChildExist(assetDTO2,isChildExistFlag);
							if(responseList.size()>0) {
								for (AssetModelDTO modelDTO : responseList) {
									List<AssetModelDTO> modelDTOs= modelDTO.getChildren();
									for (AssetModelDTO assetModelDTO : modelDTOs) {
										if(assetModelDTO.getId().equalsIgnoreCase(hasChildreturnobj.getId())) {
											assetModelDTO.setChildren(hasChildreturnobj.getChildren());
										}
									}
									
								}
								
							}else {
							responseList.add(hasChildreturnobj);
							}
							
						}
					
		
					}else {
						log.info("No Child Exists");
						hasChildExist(assetDto,false);
					}
					//responseList.add(hasChildreturnobj);
					
				}
			
				
				
			} catch (InstantiationException | IllegalAccessException e) {
				log.error(e.getMessage());
				
			}
			
		}
		
		return responseList;
	}

	private boolean isChildExists(AssetDTO assetDTO) {
		Boolean isChildExistFlag=false;
		
		if(Objects.isNull(assetDTO)) {
			log.info("Request body is empty");
		}else {
			if(Objects.isNull(assetDTO.getChildren())) {
				log.info("No child Exists");
				isChildExistFlag= false;
			}else {
				log.info("Child Exists for asset ::"+assetDTO.getAssetName());
				
				isChildExistFlag= true;
			}
			
			
		}
		return isChildExistFlag;
		
	}
	private AssetModelDTO hasChildExist(AssetDTO assetDTO,boolean ifChildExistsFlag) {
		AssetModelDTO parentAsset = null;
		AssetModelDTO childAssets = null;
		List<AssetModelDTO> childAssetList = null;
		List<AssetDTO> childerAM = null;
		if(ifChildExistsFlag == true) {
			parentAsset= new AssetModelDTO();
			childAssetList = new ArrayList<AssetModelDTO>();
			parentAsset.setId(assetDTO.getSourceKey());
			parentAsset.setLabel(assetDTO.getAssetName());
			childerAM= assetDTO.getChildren();
			for (AssetDTO singleChild : childerAM) {
				boolean tempFlag=isChildExists(singleChild);
				childAssets= new AssetModelDTO();
				childAssets.setId(singleChild.getSourceKey());
				childAssets.setLabel(singleChild.getAssetName());
				childAssetList.add(childAssets);
			}
			parentAsset.setChildren(childAssetList);
			
		}else {
			parentAsset= new AssetModelDTO();
			parentAsset.setId(assetDTO.getSourceKey());
			parentAsset.setLabel(assetDTO.getAssetName());
		
		}
		
		
		return parentAsset;
		
	}
}
