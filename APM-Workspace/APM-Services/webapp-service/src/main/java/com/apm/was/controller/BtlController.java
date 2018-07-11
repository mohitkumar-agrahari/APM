package com.apm.was.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.apm.was.dto.TubeThicknessDegradationDTO;
import com.apm.was.dto.TubeThicknessRiskDTO;
import com.apm.was.dto.VelocityCsvDTO;
import com.apm.was.inf.IBtlDetails;

@RestController
@RequestMapping(value="/btl")
public class BtlController {
	
	@Autowired
	IBtlDetails iBtlDetails;
	
	@RequestMapping(value="/getHeatMapData", method=RequestMethod.GET)
	public List<VelocityCsvDTO> getHeatMapData() {
		List<VelocityCsvDTO> responseDTO=iBtlDetails.getHeatMapData();
		return responseDTO;
		
	}
	
	@RequestMapping(value="/getLineChartCSVData", method=RequestMethod.GET)
	public List<TubeThicknessRiskDTO> getLineChartData() {
		List<TubeThicknessRiskDTO> responseDTO=iBtlDetails.getLineChartCSVData();
		return responseDTO;
		
	}
	@RequestMapping(value="/getBarChartCSVData", method=RequestMethod.GET)
	public List<TubeThicknessDegradationDTO> getBarChartCSVData() {
		List<TubeThicknessDegradationDTO> responseDTO=iBtlDetails.getBarChartCSVData();
		return responseDTO;
		
	}

}
