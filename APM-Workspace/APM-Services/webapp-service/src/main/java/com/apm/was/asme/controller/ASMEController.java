package com.apm.was.asme.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apm.was.asme.dto.ASMEInputDTO;
import com.apm.was.asme.dto.ParamResponseDTO;
import com.apm.was.asme.dto.SaveInputDTO;
import com.apm.was.asme.inf.IASMEDetails;
import com.apm.was.dto.MessageDTO;



@CrossOrigin({"http://10.177.8.119:5010","http://localhost:5010","http://localhost:5010","http://3.209.34.49:5000"})
@RequestMapping(value="/ASME")

@RestController
public class ASMEController {
	private static final Logger log = LoggerFactory.getLogger(ASMEController.class);
	
	@Autowired
	IASMEDetails iASMEDetails;
	
	/* Service to fetch asset parameters based on analytic choosen*/
	@RequestMapping(value="/fetchAssetParam",method=RequestMethod.GET)
	public ParamResponseDTO getAssetParam(@RequestParam(value="analyticId")Long analyticId){
		ParamResponseDTO response=null;
		if(Objects.isNull(analyticId)) {
			log.error("request param is null");
		}else {
			log.info("fetching count for level::"+analyticId);
			response= iASMEDetails.getAssetParam(analyticId);
		}
		return response;
	}

	
	/*save the asset input parameters data to asset*/

	@RequestMapping(value="/saveAssetInputValue",method=RequestMethod.POST)
	public MessageDTO saveAssetInputValue(@RequestBody(required= false) SaveInputDTO saveInputDTO) {
		MessageDTO response = null;
		if(Objects.isNull(saveInputDTO)) {
			log.error("Parameters not present to be saved in db");
		}else {
			
			response= iASMEDetails.saveAssetInputValue(saveInputDTO);
	}
		return response;
	}
}
