package com.apm.was.service;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.apm.was.dto.TubeThicknessDegradationDTO;
import com.apm.was.dto.TubeThicknessRiskDTO;
import com.apm.was.dto.VelocityCsvDTO;
import com.apm.was.inf.IBtlDetails;
import com.apm.was.reusable.ConvertCSVToJsonImpl;
import com.apm.was.util.ExternalPropertyUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class BtlDetailsImpl implements IBtlDetails{
	
	@Override
	public List<VelocityCsvDTO> getHeatMapData() {
		byte[] array = null;
		
		List<VelocityCsvDTO> velocityCsvDTOs = new ArrayList<VelocityCsvDTO>();
		String response = null;
		Gson gson;
		
		try {
		array = Files.readAllBytes(new File(ExternalPropertyUtil.getPropValue("velocity_profilecsv")).toPath());
		}catch (Exception e) {
			System.out.println("error");
		}
		String input_data = new String(array);

		try{
			System.out.println();
			ConvertCSVToJsonImpl csvToJsonUtil = new ConvertCSVToJsonImpl(input_data);
			FutureTask<String> ftask = new FutureTask<String>(csvToJsonUtil);
			ExecutorService ser=Executors.newFixedThreadPool(5);
			ser.submit(ftask);
			response = ftask.get();
			ser.shutdown();
			if(ftask.isDone()){
			try {	
				if(response != null) {
					List<JSONObject> list = new ArrayList<JSONObject>();
					
				    int i;
				    if(response!=null) {
				    	System.out.println("response is ::"+response);
				    JSONArray array1 = new JSONArray(response);
				    for (i = 0; i < array1.length(); i++)
				        list.add(array1.getJSONObject(i));
				    for (JSONObject jsonObject : list) {
				    	String jsnStr= jsonObject.toString();
				    	gson = GsonBuilder.class.newInstance().create();
						VelocityCsvDTO velocityCsvDTO = gson.fromJson(jsnStr, VelocityCsvDTO.class);
						velocityCsvDTOs.add(velocityCsvDTO);
						
				    		}
				    }
				}
			}
				catch (Exception e) {
					e.printStackTrace();
					
				}
			}
			}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		return velocityCsvDTOs;
	}
	@Override
	public List<TubeThicknessRiskDTO> getLineChartCSVData() {
		byte[] array = null;
		List<TubeThicknessRiskDTO> tubeThicknessRiskDTOs = new ArrayList<TubeThicknessRiskDTO>();
		String response = null;
		Gson gson;
		
		try {
		array = Files.readAllBytes(new File(ExternalPropertyUtil.getPropValue("line_data")).toPath());
		}catch (Exception e) {
			System.out.println("error");
		}
		String input_data = new String(array);
		try{
			
			ConvertCSVToJsonImpl csvToJsonUtil = new ConvertCSVToJsonImpl(input_data);
			FutureTask<String> ftask = new FutureTask<String>(csvToJsonUtil);
			ExecutorService ser=Executors.newFixedThreadPool(5);
			ser.submit(ftask);
			response = ftask.get();
			ser.shutdown();
			if(ftask.isDone()){
			try {	
				if(response != null) {
					System.out.println("response is ::"+response);
					List<JSONObject> list = new ArrayList<JSONObject>();
					
				    int i;
				    if(response!=null) {
				 JSONArray array1 = new JSONArray(response);
			    for (i = 0; i < array1.length(); i++)
			        list.add(array1.getJSONObject(i));
			    for (JSONObject jsonObject : list) {
			    	String jsnStr= jsonObject.toString();
			    	
			    	gson = GsonBuilder.class.newInstance().create();
			    	TubeThicknessRiskDTO tubeThicknessRiskDTO = gson.fromJson(jsnStr, TubeThicknessRiskDTO.class);
			    	tubeThicknessRiskDTOs.add(tubeThicknessRiskDTO);

			    }
				    }
				}
			}
				catch (Exception e) {
					e.printStackTrace();
					
				}
			}
			}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		return tubeThicknessRiskDTOs;
	}
	@Override
	public List<TubeThicknessDegradationDTO> getBarChartCSVData() {
		byte[] array = null;
		List<TubeThicknessDegradationDTO> degradationDTOs = new ArrayList<TubeThicknessDegradationDTO>();
		String response = null;
		Gson gson;
		
		try {
		array = Files.readAllBytes(new File(ExternalPropertyUtil.getPropValue("bar_chart")).toPath());
		}catch (Exception e) {
			System.out.println("error");
		}
		String input_data = new String(array);
		try{
			
			ConvertCSVToJsonImpl csvToJsonUtil = new ConvertCSVToJsonImpl(input_data);
			FutureTask<String> ftask = new FutureTask<String>(csvToJsonUtil);
			ExecutorService ser=Executors.newFixedThreadPool(5);
			ser.submit(ftask);
			response = ftask.get();
			ser.shutdown();
			if(ftask.isDone()){
			try {	
				if(response != null) {
					System.out.println("response is ::"+response);
					List<JSONObject> list = new ArrayList<JSONObject>();
					
				    int i;
				    if(response!=null) {
				 JSONArray array1 = new JSONArray(response);
			    for (i = 0; i < array1.length(); i++)
			        list.add(array1.getJSONObject(i));
			    for (JSONObject jsonObject : list) {
			    	String jsnStr= jsonObject.toString();
			    	
			    	gson = GsonBuilder.class.newInstance().create();
			    	TubeThicknessDegradationDTO degradationDTO = gson.fromJson(jsnStr, TubeThicknessDegradationDTO.class);
			    	degradationDTOs.add(degradationDTO);

			    }
				    }
				}
			}
				catch (Exception e) {
					e.printStackTrace();
					
				}
			}
			}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		return degradationDTOs;
	}
	
	

	

}
