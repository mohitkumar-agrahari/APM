package com.apm.was.reusable;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.apm.was.util.CSV;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class ConvertCSVToJsonImpl implements Callable<String>{
	
	private static final Logger log=LoggerFactory.getLogger(ConvertCSVToJsonImpl.class);
	
	 private String inputData;
	 public ConvertCSVToJsonImpl() {};
	       
	 public ConvertCSVToJsonImpl(String inputData) {
	        this.inputData = inputData;
	        
	    }
	 
	@Override
		public String call() {
		String response = null;
		try {
		response = convertCSVToJson(this.inputData);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return response;
		}
	
	
public String convertCSVToJson(String inputData) {
		log.info("convertCSVToJson method called");
		String fileContent = null;
		try {
			
			InputStream in = IOUtils.toInputStream(inputData, "UTF-8");
		    CSV csv = new CSV(false, ',', in);
		    List < String > fieldNames = null;
		    if (csv.hasNext()) fieldNames = new ArrayList < String> (csv.next());
		    List < Map < String, String >> list = new ArrayList < Map < String, String >> ();
		    while (csv.hasNext()) {
		        List < String > x = csv.next();
		        Map< String, String > obj = new LinkedHashMap< String, String > ();
		        for (int i = 0; i < fieldNames.size(); i++) {
		        	if(x.get(i).trim().equalsIgnoreCase("")||x.get(i).trim().equalsIgnoreCase(null)) {
		        		obj.put(fieldNames.get(i), null);
		        	}else {
		        		obj.put(fieldNames.get(i), x.get(i));
		        	}
		            
		        }
		        list.add(obj);
		    }
		    ObjectMapper mapper = new ObjectMapper();
		    mapper.enable(SerializationFeature.INDENT_OUTPUT);
		    mapper.writeValue(new File("data.json"), list);
		    
		    byte[] array = Files.readAllBytes(new File("data.json").toPath());
			String str = new String(array);
		    fileContent = str.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Error has occured::" + e.getMessage());
		}
		return fileContent;
	}

	public File convert(MultipartFile file) throws IOException
	{    
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile(); 
		FileOutputStream fos = new FileOutputStream(convFile); 
		fos.write(file.getBytes());
		fos.close(); 
		return convFile;
	}	
}
