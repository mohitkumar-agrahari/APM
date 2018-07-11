package com.apm.datarw.kairosdb;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddDatapointsIntoKairosDB {
	
	private static final Logger log=LoggerFactory.getLogger(AddDatapointsIntoKairosDB.class);
	
	@SuppressWarnings("unchecked")
	public CompletableFuture<JSONObject> addDatapointsIntoKairosDB(String inputData, String analyticName) {
		
		MapAndAddDataToKairosDB sstKairosDBServiceImpl = new MapAndAddDataToKairosDB();
		System.out.println("start time *******"+new Date());
		log.info("start time *******"+new Date());
		String response = null;
	    JSONObject result = new JSONObject();
		try{
			System.out.println();
			ConvertCSVToJsonImpl csvToJsonUtil = new ConvertCSVToJsonImpl(inputData);
			FutureTask<String> ftask = new FutureTask<String>(csvToJsonUtil);
			ExecutorService ser=Executors.newFixedThreadPool(1);
			ser.submit(ftask);
			response = ftask.get();
			ser.shutdown();
			if(ftask.isDone()){
			try {	
				if(response != null) {
				JSONArray jsonArray = new JSONArray(response.toString());
				try {
				String resulStr = sstKairosDBServiceImpl.mapAndAddDataToKairosDB(jsonArray, analyticName);
				result.put("response", resulStr);
				System.out.println("end time *******"+new Date());
				log.info(resulStr +" for analytic name ::"+analyticName);
				log.info("end time *******"+new Date());
				}catch (Exception e) {
					log.error(e.getMessage());
				}
				}
			}
				catch (Exception e) {
					e.printStackTrace();
					log.error("Some eror has occuerd::"+ e.getMessage());
				}
			}
			}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Some eror has occuerd::"+ e.getMessage());
		}
		return CompletableFuture.completedFuture(result);
	}
}
