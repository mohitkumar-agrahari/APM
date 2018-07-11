package com.apm.datarw.kairosdb;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apm.datarw.utils.ExternalPropertyUtil;

public class AddDatapointsIntoKairosDBHelper implements Callable<String>{
	private static final Logger log=LoggerFactory.getLogger(AddDatapointsIntoKairosDBHelper.class);
	
	private JSONArray inputData;
	
	public AddDatapointsIntoKairosDBHelper() {};
	public AddDatapointsIntoKairosDBHelper(JSONArray inputData) {
		this.inputData = inputData;
	}
	
	@Override
	public String call() throws Exception {
		String response = addDatapointsIntoKairosDB(this.inputData);
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public String addDatapointsIntoKairosDB(JSONArray inputData) throws UnsupportedEncodingException {
		String responseStr = null;
		//System.out.println("KairosDB URL :: "+sstConfig.getKairosDBUrl());
		//log.info("KairosDB URL :: "+sstConfig.getKairosDBUrl());
		String url = ExternalPropertyUtil.getPropValueFromExternalConfig("addDatapointsIntoKairosDBUrl");
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		// add header
		post.setHeader("Content-Type", "application/json");
		StringEntity stringEntity = new StringEntity(inputData.toString(), "UTF-8");
		post.setEntity(stringEntity);
		try {
		HttpResponse response = client.execute(post);
		log.info("Response Code :: " + response.getStatusLine().getStatusCode());

		if(response.getStatusLine().getStatusCode() == 204) {
			responseStr = "Datapoints has been successfully added";
		}
		else {
			responseStr = "Some error has been occured";
		}
		}	
		catch (Exception e) {
			responseStr = "Some error has been occured";
			log.error(responseStr);
			e.printStackTrace();
		}
		return responseStr;
	}
	
}
