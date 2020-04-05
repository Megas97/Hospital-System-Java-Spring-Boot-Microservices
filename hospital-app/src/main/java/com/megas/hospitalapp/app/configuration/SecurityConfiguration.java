package com.megas.hospitalapp.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	// Disable default login form: https://stackoverflow.com/questions/48902706/spring-cloud-eureka-with-spring-security
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
	}
	
	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}
}