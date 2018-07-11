package com.apm.was.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apm.was.dto.AssetModelDTO;
import com.apm.was.dto.ImageDetailsDTO;
import com.apm.was.dto.MessageDTO;
import com.apm.was.inf.IAssetModel;

@RestController
@RequestMapping(value="/am")
public class AssetModelController {
	private static final Logger log = LoggerFactory.getLogger(AssetModelController.class);
	@Autowired IAssetModel iAssetModel;
	
	/*Retrieves data of all Asset */

	@RequestMapping(value="/getAssetData",method=RequestMethod.GET,produces="application/json")
	public List<AssetModelDTO> getAssetData(){
		
			
		return iAssetModel.getAssetData();
	} 
	
	/*Retrieves hierarchy data */

	@RequestMapping(value="/getHierarchyBySourcekey",method=RequestMethod.GET,produces="application/json")
	public List<AssetModelDTO> getHierarchyBySourcekey(@RequestParam(value="sourceKey")String sourceKey){
		
			
		return iAssetModel.getHierarchyBySourcekey(sourceKey);
	} 
	/*save the imageFile data to asset*/

	@RequestMapping(value="/saveAssetImage",method=RequestMethod.POST)
	public MessageDTO saveAssetImage(@RequestParam(value = "imgFile") MultipartFile imageFile,@RequestParam(value="analyticName")String analyticName,@RequestParam(value="assetType")String assetType) throws IOException{
		log.info("call made to /saveAssetImage endpoint");
		return iAssetModel.saveAssetImage(imageFile,analyticName,assetType);
	} 

	/*Retrieves the imageFile data of an asset based on levelDisplay Name*/
	@RequestMapping(value="/getImageData",method=RequestMethod.GET,produces="application/json")
	public ImageDetailsDTO getImageData(@RequestParam(value="levelDisplayName")String levelDisplayName){
		log.info("call made to /getImageData endpoint");
		if(levelDisplayName.isEmpty()) {
			log.error("levelDisplay name is Empty");
			throw new ValidationException("levelDisplay name is Empty");
		}else {
			return iAssetModel.getImageData(levelDisplayName);
		}	
	} 

}
