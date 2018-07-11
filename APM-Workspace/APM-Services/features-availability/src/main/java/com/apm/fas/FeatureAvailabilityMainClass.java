package com.apm.fas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan("com.apm.fas.entity.**")
@EnableJpaRepositories("com.apm.fas.repo.**")
@Import(com.apm.fas.cors.CustomCorsFilter.class)
public class FeatureAvailabilityMainClass {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(FeatureAvailabilityMainClass.class,args);
	}
}
