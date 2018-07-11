package com.apm.datarw.kairosdb;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapToTimeSeriesFormatImpl implements Callable<JSONObject> {

	private static final Logger log=LoggerFactory.getLogger(MapToTimeSeriesFormatImpl.class);
	
	private String keyValue;
	private JSONArray inputData;
	private String analyticName;
	
	public MapToTimeSeriesFormatImpl() {};
	
	public MapToTimeSeriesFormatImpl(String keyValue, JSONArray inputData, String analyticName) {
		this.keyValue = keyValue;
		this.inputData = inputData;
		this.analyticName = analyticName;
	}
	
	@Override
	public JSONObject call() throws Exception {
		JSONObject jsonObject = mapToTimeSeriesFormat(this.keyValue,this.inputData, this.analyticName);
		return jsonObject;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject mapToTimeSeriesFormat(String keyValue, JSONArray inputData, String analyticName) throws ParseException {
		
		JSONObject metricJsonObject = new JSONObject();
		try {
		List<Double[]> datapoints = new ArrayList<Double[]>();
		for (Object object : inputData) {
			JSONObject jsonObject = (JSONObject) object;
			String strDate = (String) jsonObject.get("DateTime");
			String formattedDate = getDate(strDate);
			Timestamp timestampDate = Timestamp.valueOf(formattedDate);
			Long timestampLong = timestampDate.getTime();
			Double d1 = (double)timestampLong;
			Double d2 = 0.0;
			if((String)jsonObject.get(keyValue) != null && (((String)jsonObject.get(keyValue)).length() > 0)) {
			d2 = Double.parseDouble((String)jsonObject.get(keyValue));
			}
			else {
				d2 = 0.0;
			}
			Double[] datapoint = {d1,d2};   
			datapoints.add(datapoint);
		}
	        metricJsonObject.put("name", analyticName);
	        metricJsonObject.put("datapoints", datapoints);
	        JSONObject tagJsonObject = new JSONObject();
	        String tag = keyValue.replace("@", ""); 
	        tagJsonObject.put("host",tag);
	        metricJsonObject.put("tags",tagJsonObject );
	        metricJsonObject.put("ttl", 300);	       
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error has occured::"+e.getMessage());
		}
	        return metricJsonObject;
	}
	
	public String getDate(String date)throws ParseException {
  
		String currentFormat = "MM/dd/yyyy HH:MM:SS";
		String expectedFormat = "yyyy-MM-dd HH:MM:SS";
	    // Validating if the supplied parameters is null 
	    if (date == null || currentFormat == null || expectedFormat == null ) {
	        return null;
	    }
	    // Create SimpleDateFormat object with source string date format
	    SimpleDateFormat sourceDateFormat = new SimpleDateFormat(currentFormat);
	    // Parse the string into Date object
	    Date dateObj = sourceDateFormat.parse(date);
	    // Create SimpleDateFormat object with desired date format
	    SimpleDateFormat desiredDateFormat = new SimpleDateFormat(expectedFormat);
	    // Parse the date into another format
	    return desiredDateFormat.format(dateObj).toString();
	}
	
	/*public boolean isValidDate(String inputDate) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    try{
	    	dateFormat.applyPattern(inputDate);
	    	return true;
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
			return false;
		}
	  }*/
}
