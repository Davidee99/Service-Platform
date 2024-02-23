package com.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface RestTemplateService {

	public ResponseEntity<?> sampleRestTemplate(HttpMethod httpMethod, String url, HttpEntity<?> request);
}
