package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.configuration.URLS;
import com.service.RestTemplateService;

@RestController
@RequestMapping("/api/dispatcher/")
public class MailDispacherController {


	@Autowired
	RestTemplateService restTemplateService;

	// MAIL SERVICE

	@PostMapping("mail-service/sendCustom/")
	ResponseEntity<?> sendCustom(HttpEntity<?> request) {
		
		String url = URLS.SEND_CUSTOM_MAIL;
		return restTemplateService.sampleRestTemplate(HttpMethod.POST, url, request);
		
	}

	@PostMapping("mail-service/sendDefault/")
	ResponseEntity<?> sendDefault(HttpEntity<?> request) {
		
		String url = URLS.SEND_DEFAULT_MAIL;
		return restTemplateService.sampleRestTemplate(HttpMethod.POST, url, request);
		
	}

	

	
	


	
}
