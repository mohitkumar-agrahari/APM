package com.apm.was.http.connector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apm.was.util.ExternalPropertyUtil;
import com.apm.was.util.Utils;
/*This class contains method that can be used to communicate to others requests using Http calls*/
public class HttpConnector {
	private static final Logger logger = LoggerFactory.getLogger(HttpConnector.class);
	
	 /*Method to get the concerned request using HttpGet*/
	
	public  JSONObject getRequests(String mapping,String params) throws Exception {
		JSONObject responseObj= new JSONObject();
		String resposebdy_str="";
		String url=ExternalPropertyUtil.getPropValue("amServiceURL");
		String requestUrl ="";
		String parametres="";
		if(Objects.isNull(mapping)) {
			logger.error("No Mapping provided");
		}else if(Objects.nonNull(params)&&!(params.equalsIgnoreCase("")))
		{
			logger.info(" Param for the request::"+params);
			parametres="?"+params;
		}
		requestUrl=url+mapping+parametres;
         InputStreamReader isr = null;
         
         HttpGet get = new HttpGet(requestUrl);
         get.setHeader("Content-Type",ExternalPropertyUtil.getPropValue("amServiceContentType") );
		 HttpClient client1 = HttpClientBuilder.create().build();
		 HttpResponse response = client1.execute(get);
		    logger.info( "response  ---   "+response);
		    logger.info( "Sending 'GET' request to URL : ", requestUrl);
		    logger.info( "Post parameters : ", params);
		    logger.info( "Response Code : ", response.getStatusLine().getStatusCode());

		    	isr = new InputStreamReader(response.getEntity().getContent());
                logger.info( "isr  -- ", isr);
                BufferedReader rd = new BufferedReader(isr);

                StringBuilder result = new StringBuilder();
                String line = "";
                	while ((line = rd.readLine()) != null) {
                      result.append(line);
                		}
                		logger.info( "result  -- ", result);
                		resposebdy_str = result.toString();
                		logger.info( "token  -- ", resposebdy_str);
                			if (isr != null) {
                					isr.close();
                				}
                			rd.close();
                System.out.println(resposebdy_str);
                			responseObj=Utils.convertStringToJSONObject(resposebdy_str);
                return responseObj;
        
  }
/*	 public static void main(String[] args) throws Exception {
			String response =getRequests("/assets/all","");
			System.out.println("response ::" +response);
		}*/

}
