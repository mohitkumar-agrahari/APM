package com.ge.sst.utils;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	private static final Logger log = LoggerFactory.getLogger(Utils.class);

public static Date getCurrentDate() {

                                /*
                                * DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); Calendar
                                * cal = Calendar.getInstance();
                                * System.out.println(dateFormat.format(cal.getTime()));
                                */

                                return new Date(System.currentTimeMillis());

                }

 public static String dateToString(Date date) {

                                String output = "";
                                if (null != date) {
                                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                                output = formatter.format(date);
                                }
                                return output;
                }

 public static java.util.Date incrementDaysToDate(java.util.Date date, int days) {
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(date);
                                cal.add(Calendar.DATE, days); // minus number would decrement the days
                                return cal.getTime();
                }

public static java.util.Date convertStringToDate(String inputDate) throws ParseException {
                                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
                                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                                java.util.Date newDate = (java.util.Date) sdf.parse(inputDate);
                                return newDate;
                }
 public static java.util.Date convertNewStringToDate(String inputDate) throws ParseException
            	{		
            		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            		java.util.Date newDate = (java.util.Date) sdf.parse(inputDate);
            		return newDate;
            	}  
 public static JSONObject convertStringToJSONObject(String jsonString) throws ParseException
	{
	 JSONObject json = new JSONObject();
			 JSONParser parser = new JSONParser();
			 try {
				 json = (JSONObject) parser.parse(jsonString);
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				log.error("Error while parsing the jsonString ::"+jsonString);
				e.printStackTrace();
			}
			 return json;
	}
}
