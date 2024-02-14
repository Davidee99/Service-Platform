package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.CustomRequestObject;
import com.model.RequestObject;
import com.service.EmailService;

@RestController
@RequestMapping("/mailsender")
public class MailsenderController {
	@Autowired
	EmailService svc;
	
	private final String ACCESS_KEY = "qwerty";

	@PostMapping("/sendDefault")
	ResponseEntity<String> sendDefault(@RequestBody RequestObject reqObj, @RequestHeader HttpHeaders requestHeadres) {
		
		//Controlliamo che chi fa la request a questo endPoint abbia all'interno degli Headers la chiave access_key = "qwerty"
		if(requestHeadres.get("access_key") == null || !ACCESS_KEY.equals(requestHeadres.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		
		String success = svc.sendDefaultMessage(reqObj.getTo(), reqObj.getAccessCode(), reqObj.getLink());
		return ResponseEntity.status(HttpStatus.OK).body(success);

	}

	@PostMapping("/sendCustom")
	ResponseEntity<?> sendCustom(@RequestBody CustomRequestObject reqObj, @RequestHeader HttpHeaders requestHeadres) {
			
		if(requestHeadres.get("access_key") == null || !ACCESS_KEY.equals(requestHeadres.get("access_key").get(0))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso Negato"); //401
		}
		
		String success = svc.sendCustomMessage(reqObj.getTo(), reqObj.getSubj(), reqObj.getText());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(success);
	}
	
}
