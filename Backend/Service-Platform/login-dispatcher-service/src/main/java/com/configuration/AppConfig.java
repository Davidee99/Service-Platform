package com.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.service.RestTemplateService;
import com.service.RestTemplateServiceImpl;

@Configuration
public class AppConfig {

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	RestTemplateService restTemplateService() {
		return new RestTemplateServiceImpl();
	}
}
