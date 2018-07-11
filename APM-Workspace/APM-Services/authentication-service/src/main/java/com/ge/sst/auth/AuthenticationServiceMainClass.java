package com.ge.sst.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan("com.ge.sst.auth.entity.**")
@EnableJpaRepositories("com.ge.sst.auth.repository.**")
@Import(com.ge.sst.auth.cors.CustomCorsFilter.class)
public class AuthenticationServiceMainClass {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AuthenticationServiceMainClass.class,args);
	}
}
