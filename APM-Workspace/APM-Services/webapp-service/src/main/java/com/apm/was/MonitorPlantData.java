package com.apm.was;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan({"com.apm.was.entity.**", "com.apm.was.asme.entity.**"})
@EnableJpaRepositories({"com.apm.was.repo.**", "com.apm.was.asme.repo.**"})
@Import({com.apm.was.cors.CustomCorsFilter.class})
public class MonitorPlantData {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MonitorPlantData.class,args);
	}
}
