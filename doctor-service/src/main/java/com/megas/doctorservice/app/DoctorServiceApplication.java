package com.megas.doctorservice.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class DoctorServiceApplication {
	// Disable default login form: https://stackoverflow.com/questions/48902706/spring-cloud-eureka-with-spring-security
	@EnableWebSecurity
	static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
	    protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
	    }
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DoctorServiceApplication.class, args);
	}
}