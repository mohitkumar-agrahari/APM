package com.apm.datarw.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ExternalPropertyUtil {
	public static String getPropValueFromExternalConfig(String key) {

        Properties prop = new Properties();
        String propFileName = "externalConfig.properties";

        InputStream inputStream;
            try {
                inputStream = ExternalPropertyUtil.class.getClassLoader().getResourceAsStream(propFileName);
                prop.load(inputStream);
            } catch (FileNotFoundException e) {
                String ex =e.getMessage();
            
            } catch (IOException e) {
                String ex = e.getMessage();       
            }
      
       
        return prop.getProperty(key);
    }
	public static String getPropValueFromApplicationConfig(String key) {

        Properties prop = new Properties();
        String propFileName = "application.properties";

        InputStream inputStream;
            try {
                inputStream = ExternalPropertyUtil.class.getClassLoader().getResourceAsStream(propFileName);
                prop.load(inputStream);
            } catch (FileNotFoundException e) {
                String ex =e.getMessage();
            
            } catch (IOException e) {
                String ex = e.getMessage();       
            }
      
       
        return prop.getProperty(key);
    }
}
