package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dispacher")
public class DispacherController {

//	@Autowired
//	RestTemplate restTemplate;

	@GetMapping("/test")
	String test() {
		return "Ciao";
	}

//	@CrossOrigin("Ciccio bello ha la bua e vuole te!")
//	@PostMapping("/redirect/ticket")
//	HttpResponse<?> ticket(HttpEntity<?> request) {
//
//		request.getHeaders().setOrigin("Ciccio bello ha la bua e vuole te!");
//
//		restTemplate.exchange("cicciobello.it", HttpMethod.POST, request, Object.class);
//
//		return null;
//	}
}
