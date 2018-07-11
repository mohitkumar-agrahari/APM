package com.apm.was.util;

import java.io.InputStream;
import java.util.Properties;

public class ExternalPropertyUtil {
	public static String getPropValue(String key) { 
        Properties prop = new Properties();
        String propFileName = "externalConfig.properties";

        InputStream inputStream;
            try {
                inputStream = ExternalPropertyUtil.class.getClassLoader().getResourceAsStream(propFileName);
                prop.load(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }       
        return prop.getProperty(key);
    }
}
