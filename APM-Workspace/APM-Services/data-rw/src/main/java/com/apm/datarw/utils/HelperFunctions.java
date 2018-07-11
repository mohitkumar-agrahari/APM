package com.apm.datarw.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HelperFunctions {
	
	public static Date convertToGMT( Date date ){
	    TimeZone localTimezone = TimeZone.getDefault();
	    Date ret = new Date( date.getTime() - localTimezone.getRawOffset() );

	    // if we are now in DST, back off by the delta.  Note that we are checking the GMT date, this is the KEY.
	    if ( localTimezone.inDaylightTime( ret )){
	        Date dstDate = new Date( ret.getTime() - localTimezone.getDSTSavings() );

	        // check to make sure we have not crossed back into standard time
	        // this happens when we are on the cusp of DST (7pm the day before the change for PDT)
	        if ( localTimezone.inDaylightTime( dstDate )){
	            ret = dstDate;
	        }
	     }
	     return ret;
	}
	
	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
	    Map<String, Object> map = new HashMap<String, Object>();

	    Iterator<?> keysItr = object.keys();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next().toString();
	        Object value = object.get(key);

	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        map.put(key, value);
	    }
	    return map;
	}
	
	public static List<Object> toList(JSONArray array) throws JSONException {
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.length(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = HelperFunctions.toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = HelperFunctions.toMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}
	
	public static boolean isJsonObject(String input){
		try{
			new JSONObject(input);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public static boolean isJsonArray(String input){
		try{
			new JSONArray(input);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	

	/**
	 * @param dateString
	 * @param dateFormat
	 * @return
	 */
	private static boolean converterWrapper(String dateString, String dateFormat){
		try{
			SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
			dateFormatter.parse(dateString);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	
	/**
	 * Convert Date String to Epoch.
	 * @param dateString
	 * @return
	 */
	public static Long convertDate(String dateString){
		SimpleDateFormat dateFormatter = null;
		try{
			if (converterWrapper(dateString, DataConstants.INPUT_DATE_TIME_FMT)){
				dateFormatter = new SimpleDateFormat(DataConstants.INPUT_DATE_TIME_FMT);
				dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
				return dateFormatter.parse(dateString).getTime();
			}else if (converterWrapper(dateString, DataConstants.ISO_DATE_TIME_FMT)){
				dateFormatter = new SimpleDateFormat(DataConstants.ISO_DATE_TIME_FMT);
				dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
				return dateFormatter.parse(dateString).getTime();
			}else{
				return -1L;
			}
		}catch(Exception e){
			return -1L;
		}
	}
	
	
	/**
	 * Convert an object to number. 
	 * Conditions: 
	 * 1. If it contains "." -> Decimal else Long
	 * 2. In case of Exception, send as String.	 * 
	 * @param value
	 * @return
	 */
	public static Object getFormattedValue(String value){
		//Contains Decimal
		try{
			if (value.indexOf(".") != -1){
				return Double.parseDouble(value);
			}else{
				return Long.parseLong(value);
			}
		}catch(Exception e){
			return value;
		}
		
	}
}
