package com.apm.was.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.apm.was.inf.IMonitorPlantDetails;
import com.apm.was.repo.IAnalyticAlarmRepo;
import com.apm.was.repo.IAnalyticInfoRepo;
import com.apm.was.repo.IAnalyticOutputEntity;
import com.apm.was.repo.IAnalyticResultReferenceTagRepo;
import com.apm.was.repo.IAnalyticTagMappingRepo;
import com.apm.was.util.ExternalPropertyUtil;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Service
@Transactional
public class MonitorPlantDetails implements IMonitorPlantDetails {
	private static final Logger log= LoggerFactory.getLogger(MonitorPlantDetails.class);

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
	
	
	 
		
	
	/*
	 * method to fetch Page request Object
	 */
	 @SuppressWarnings("unused")
	private PageRequest gotoPage(int page)
	    {
	        PageRequest request = new PageRequest(page,1);
	        return request;
	    }

	 
	 /*
	  * get all outtag data from KiarosDB based on assetId
	  * */
	@SuppressWarnings("unchecked")
	@Override
	public List<JSONObject> getOuttagData(String assetId) throws ParseException, IOException {
		log.info("getOuttagData method called for asset Id :: " + assetId);
	 JSONObject jsonObject = null;
	 List<JSONObject> list = new ArrayList<JSONObject>();
	 OkHttpClient client = new OkHttpClient();
	 Request request = new Request.Builder()
	                      .url(ExternalPropertyUtil.getPropValue("getOuttagDataUrl"))
	                      .build();
	
				 Response response = null;
				 try {
					 response = client.newCall(request).execute();
					 System.out.println(""+ response.isSuccessful());
					 log.info("response status code :: " + response.code());
					 String result = response.body().string();
					 jsonObject = new JSONObject((JSONObject) new JSONParser().parse(result.toString()));
					 org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray)jsonObject.get("results");
						 for (Object object : jsonArray) {
							String strTag = (String) object;
							JSONObject jsonObject2 = new JSONObject();
							jsonObject2.put("tag", strTag);
							list.add(jsonObject2);
						 }
				 }
				 catch (Exception e) {
					 List<JSONObject> catchList = new ArrayList<JSONObject>();
					 JSONObject catchJsonObject = new JSONObject();
					 catchJsonObject.put("error", "Failed to retrieve data from KairosDB. The server May be temporarily down");
					 catchList.add(catchJsonObject);
					 log.error("Failed to retrieve data from KairosDB :: " + e.getMessage());
					 return catchList;
				}
				 finally {
					 if(response != null) {
						response.body().close();
					}
				 }
			log.info("list size :: " + list.size());
			return list;
	}

	/*
	 * method to fetch data based on tag and type of chart
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getTagData(JSONObject jsonObject) throws IOException {
		log.info("getTagData method called");
		RequestBody reqBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
		JSONObject responseJsonObject = null;
		OkHttpClient client = new OkHttpClient();
	    Request request = new Request.Builder()
	            .url(ExternalPropertyUtil.getPropValue("getTagDataUrl"))
	            .post(reqBody)
	            .build();
	    Response response = null;
	    try {
	    response = client.newCall(request).execute();
	    log.info("response code :: " + response.code());
	    String result = response.body().string();
	    responseJsonObject = new JSONObject((JSONObject) new JSONParser().parse(result.toString()));
	    }
	    catch (Exception e) {
			 JSONObject catchJsonObject = new JSONObject();
			 catchJsonObject.put("error", "Failed to retrieve data from KairosDB. The server May be temporarily down");
			 log.error("Failed to retrieve data from KairosDB :: " + e.getMessage());
			 return catchJsonObject;
		}
		 finally {
			 if(response != null) {
				response.body().close();
			}
		 }
	   return responseJsonObject;
	}

@Override
	public String goToGrafana(String analyticName) throws IOException {
		log.info("goToGrafana method called");
		String result = null;
		if(analyticName == null || analyticName.isEmpty() || analyticName.length()==0) {
			result = ExternalPropertyUtil.getPropValue("grafanaHomeUrl");
		}
		else {
		if(analyticName.contains(" ")) {
			analyticName = analyticName.replace(" ", "-");
		}
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
	            .url(ExternalPropertyUtil.getPropValue("getAndCreateGrafanaDashboardUrl")+ "/"+analyticName)
	            .header(ExternalPropertyUtil.getPropValue("authorizationHeader"),ExternalPropertyUtil.getPropValue("bearer")+" "+ ExternalPropertyUtil.getPropValue("bearerTokenValue"))
	            .build();
	    Response response = null;
	    try {
	    response = client.newCall(request).execute();
	    log.info("response code :: " + response.code());
	    if(response != null) {
	    if(response.code() == 200) {
	    	result = ExternalPropertyUtil.getPropValue("grafanDashboardUrl") +analyticName+ExternalPropertyUtil.getPropValue("grafanaOrgId");
	    	log.info("Grafana Dashboard URL for already existing analytic ::" + result);
	    } 
	    else if(response.code() == 404) {
	    	String slug = this.createGrafanaDashboard(analyticName);
	    	log.info("in else slug value ::" + slug);
	    	if(!slug.equalsIgnoreCase("error")) {
	    	result =  ExternalPropertyUtil.getPropValue("grafanDashboardUrl") +slug+ExternalPropertyUtil.getPropValue("grafanaOrgId");
	    	}
	    	else {
	    		result = "error";
	    	}
	    	log.info("Grafana Dashboard URL for newly created analytic ::" + result);
	    }
	    else {
	    	result = "Unauthorized";
	    }
	    }
	    }
	    catch (Exception e) {
			 String error = "error";
			 log.error("Failed to connect with Grafana Server :: " + e.getMessage());
			 return error;
		}
		 finally {
			 if(response != null) {
				response.body().close();
			}
		 }
		}
		return result;
	}
		@SuppressWarnings("unchecked")
	public String createGrafanaDashboard(String analyticName) throws IOException{
		String result = null;
		JSONObject jsonObject = new JSONObject();
		JSONObject dashboardJsonObject = new JSONObject();
		dashboardJsonObject.put("id", null);
		dashboardJsonObject.put("title", analyticName);
		dashboardJsonObject.put("timezone", "browser");
		dashboardJsonObject.put("schemaVersion", 6);
		dashboardJsonObject.put("version", 0);
		jsonObject.put("dashboard", dashboardJsonObject);
		jsonObject.put("overwrite", false);
		System.out.println(jsonObject.toString());
		RequestBody reqBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
		OkHttpClient client = new OkHttpClient();
	    Request request = new Request.Builder()
	            .url(ExternalPropertyUtil.getPropValue("getAndCreateGrafanaDashboardUrl"))
	            .header(ExternalPropertyUtil.getPropValue("authorizationHeader"),ExternalPropertyUtil.getPropValue("bearer")+" "+ ExternalPropertyUtil.getPropValue("bearerTokenValue"))
	            .post(reqBody)
	            .build();
	    Response response = null;
	    try {
	    response = client.newCall(request).execute();
	    if(response != null) {
	    String innerResult = response.body().string();
	    JSONObject responseJsonObject = new JSONObject((JSONObject) new JSONParser().parse(innerResult.toString()));
	    System.out.println(innerResult.toString());
	    System.out.println(responseJsonObject.toJSONString());
	    result = (String) responseJsonObject.get("slug");
	    }
	    else {
	    	result = "error";
	    }
	    }
	    catch (Exception e) {
			 String error = "error";
			 log.error("Failed to connect with Grafana Server :: " + e.getMessage());
			 return error;
		}
		 finally {
			 if(response != null) {
				response.body().close();
			}
		 }
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<JSONObject> getMetricsAsAnalytics() throws IOException {
		List<JSONObject> list = new ArrayList<JSONObject>();
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(ExternalPropertyUtil.getPropValue("getMetricsAsAnalyticsUrl")).build();
	    Response response = null;
		    try {
		    response = client.newCall(request).execute();
			    if(response != null) {
					    if(response.code() == 200) {
					    	String responseString = response.body().string();
					    	JSONObject jsonObject = new JSONObject((JSONObject) new JSONParser().parse(responseString));
					    	org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) jsonObject.get("results");
					    	for (Object object : jsonArray) {
								String stringObject = (String) object;
								if(!(stringObject.contains("kairosdb"))) {
									JSONObject jsonObject2 = new JSONObject();
									jsonObject2.put("analyticName", stringObject);
									log.info("metric name :: " + stringObject);
									list.add(jsonObject2);
								}
								
							}					    	
					    } 
			    }
		    }
		    catch (Exception e) {
		    	 List<JSONObject> catchList = new ArrayList<JSONObject>();
				 JSONObject catchJsonObject = new JSONObject();
				 catchJsonObject.put("error", "Failed to retrieve data from KairosDB. The server May be temporarily down");
				 catchList.add(catchJsonObject);
				 log.error("Failed to retrieve data from KairosDB :: " + e.getMessage());
				 return catchList;
			}
			 finally {
				 if(response != null) {
					response.body().close();
				}
			 }
		    log.info("metric list size :: "+list.size());
		return list;
	}
@SuppressWarnings("unchecked")
	@Override
	public List<JSONObject> getTagsByAnalyticName(JSONObject jsonObject) throws IOException {
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		RequestBody reqBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
		OkHttpClient client = new OkHttpClient();
	    Request request = new Request.Builder()
	            .url(ExternalPropertyUtil.getPropValue("getTagsByAnalyticNameUrl"))
	            .post(reqBody)
	            .build();
	    Response response = null;
	    try {
	    response = client.newCall(request).execute();
	    String result = response.body().string();
	    JSONObject responseJsonObject = new JSONObject((JSONObject) new JSONParser().parse(result.toString()));
	    org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) responseJsonObject.get("queries");
	    for (Object object : jsonArray) {
	    	JSONObject analyticJsonObject = new JSONObject();
			JSONObject jsonObject2 = (JSONObject) object;
			org.json.simple.JSONArray jsonArray2 = (org.json.simple.JSONArray) jsonObject2.get("results");
			List<JSONObject> tagList = new ArrayList<JSONObject>();
			for (Object object2 : jsonArray2) {
				JSONObject jsonObjectResult = (JSONObject) object2;
				analyticJsonObject.put("analyticName", (String)jsonObjectResult.get("name"));
				JSONObject jsonObject3 = (JSONObject) jsonObjectResult.get("tags");
				org.json.simple.JSONArray jsonArray3 = null;
				try {
				jsonArray3 = (org.json.simple.JSONArray) jsonObject3.get("host");
				}catch (Exception e) {
					e.printStackTrace();
					log.error("Error occured :: " + e.getMessage());
				}
				if(!Objects.isNull(jsonArray3)) {
				for (Object object3 : jsonArray3) {
					
					String tags = (String) object3;
					JSONObject jsonObject4 = new JSONObject();
					jsonObject4.put("tag", tags);
					tagList.add(jsonObject4);
				}
				}
				analyticJsonObject.put("tags", tagList);
			}
			list.add(analyticJsonObject);
		}
	    }
	    catch (Exception e) {
	    	 List<JSONObject> catchList = new ArrayList<JSONObject>();
			 JSONObject catchJsonObject = new JSONObject();
			 catchJsonObject.put("error", "Failed to retrieve data from KairosDB. The server May be temporarily down");
			 catchList.add(catchJsonObject);
			 log.error("Failed to retrieve data from KairosDB :: " + e.getMessage());
			 return catchList;
		}
		 finally {
			 if(response != null) {
				response.body().close();
			}
		 }
		return list;
	}


	
}
